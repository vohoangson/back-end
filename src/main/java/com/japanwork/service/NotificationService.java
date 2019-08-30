package com.japanwork.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Notification;
import com.japanwork.model.PageInfo;
import com.japanwork.model.User;
import com.japanwork.payload.request.MarkReadNotificationRequest;
import com.japanwork.payload.response.NotificationResponse;
import com.japanwork.repository.notification.NotificationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class NotificationService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private NotificationRepository notificationRepository;

	public void addNotification(UUID senderId, UUID conversationId, UUID objectableId, UUID receiverId, String content,
			String type) throws ForbiddenException{
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setObjectableId(objectableId);
		notification.setNotificationType(type);
		notification.setContent(content);
        notification.setReceiverId(receiverId);
        notification.setCreatedAt(CommonFunction.getCurrentDateTime());
        notification.setUpdatedAt(CommonFunction.getCurrentDateTime());
		Notification result = notificationRepository.save(notification);
		ResponseDataAPI responseDataAPI = new ResponseDataAPI(
													CommonConstant.ResponseDataAPIStatus.SUCCESS,
													this.convertNotificationResponse(result, conversationId),
													""
        );
		rabbitTemplate.convertAndSend("notifications/"+receiverId, ""+receiverId, responseDataAPI);
	}
	
	public ResponseDataAPI index(User user, int page, int paging)
			throws ResourceNotFoundException{
		try {
			Page<Notification> pages = notificationRepository.findByReceiverIdAndDeletedAt(
			        PageRequest.of(page-1, paging, Sort.by("createdAt").descending()), user.getId(), null);
			PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());

			List<NotificationResponse> list = new ArrayList<NotificationResponse>();

			if(pages.getContent().size() > 0) {
				for (Notification notification : pages.getContent()) {
					list.add(this.convertNotificationResponse(notification, null));
				}
			}

			return new ResponseDataAPI(
			        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                    list,
                    pageInfo
            );
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
	}

	public int countNotificationUnread(User user) throws ResourceNotFoundException{
		try {
			int num = notificationRepository.countByReceiverIdAndIsReadAndDeletedAt(user.getId(), false, null);
			return num;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
	}

	@Transactional
	public void update(UserPrincipal userPrincipal, MarkReadNotificationRequest markReadNotificationRequest) {
		notificationRepository.updateIsReadByIdInAndDeletedAt(true, markReadNotificationRequest.getNotificationIds());
	}

	@Transactional
	public void updateAllRead(User user) {
		notificationRepository.updateIsReadByReceiverIdAndDeletedAt(true, user.getPropertyId());
	}
	public NotificationResponse convertNotificationResponse(Notification notification, UUID conversationId) {
		NotificationResponse notificationResponse = new NotificationResponse();
		notificationResponse.setId(notification.getId());
		notificationResponse.setObjectableId(notification.getObjectableId());
		notificationResponse.setNotificationType(notification.getNotificationType());
		notificationResponse.setReceiverId(notification.getReceiverId());
		notificationResponse.setSenderId(notification.getSenderId());
		notificationResponse.setConversationId(conversationId);
		notificationResponse.setContent(notification.getContent());
		notificationResponse.setCreateAt(notification.getCreatedAt());
		notificationResponse.setRead(notification.isRead());
		return notificationResponse;
	}
}
