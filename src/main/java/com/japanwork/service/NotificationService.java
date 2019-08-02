package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.JobApplication;
import com.japanwork.model.Notification;
import com.japanwork.model.PageInfo;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.User;
import com.japanwork.payload.request.MarkReadNotificationReuqest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.NotificationResponse;
import com.japanwork.repository.notification.NotificationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class NotificationService {	
	@Autowired 
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestTranslationService requestTranslationService;
	
	@Autowired
	private JobApplicationService jobApplicationService;

	public void addNotification(UUID senderId, UUID conversationId,UUID objectableId, UUID receiverId, String content, String type, UUID userId) 
			throws ForbiddenException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setObjectableId(objectableId);
		notification.setNotificationType(type);
		notification.setCreatedAt(timestamp);
		notification.setContent(content);
		notification.setReceiverId(receiverId);
		notification.setDeletedAt(null);
		Notification result = notificationRepository.save(notification);
		BaseDataResponse response = new BaseDataResponse(this.convertNotificationResponse(result, conversationId));
		rabbitTemplate.convertAndSend("notifications/"+userId, ""+userId, response);
	}
	public BaseDataMetaResponse notifications(UserPrincipal userPrincipal, int page, int paging) throws ResourceNotFoundException{
		try {
			User user = userService.getUser(userPrincipal);
			Page<Notification> pages = notificationRepository.findByReceiverIdAndDeletedAt(
			        PageRequest.of(page-1, paging, Sort.by("createdAt").descending()), user.getPropertyId(), null);
			PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
			
			List<NotificationResponse> list = new ArrayList<NotificationResponse>();
			
			if(pages.getContent().size() > 0) {
				for (Notification notification : pages.getContent()) {
					UUID conversationId = null;
					String notificationType = notification.getNotificationType();
					if(notificationType.equals(CommonConstant.NotificationType.MESSAGE_REQUEST)) {
						RequestTranslation requestTranslation = requestTranslationService.requestTranslation(
								notification.getObjectableId(), userPrincipal);
						conversationId = requestTranslation.getConversation().getId();
					} else if(notificationType.equals(CommonConstant.NotificationType.MESSAGE_JOB_APPLICATION_COMPANY_SUPPORT)){
						JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(notification.getObjectableId());
						conversationId = jobApplication.getCandidateSupportConversaion().getId();
					} else if(notificationType.equals(CommonConstant.NotificationType.MESSAGE_JOB_APPLICATION_ALL)) {
						JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(notification.getObjectableId());
						conversationId = jobApplication.getAllConversation().getId();
					} else if(notificationType.equals(CommonConstant.NotificationType.MESSAGE_JOB_APPLICATION_CANDIDATE_SUPPORT)) {
						JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(notification.getObjectableId());
						conversationId = jobApplication.getCandidateSupportConversaion().getId();
					} 
					list.add(this.convertNotificationResponse(notification, conversationId));
				}
			}
			
			return new BaseDataMetaResponse(list, pageInfo);
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public int unreadNumber(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		try {
			User user = userService.getUser(userPrincipal);
			int num = notificationRepository.countByReceiverIdAndIsReadAndDeletedAt(user.getPropertyId(), false, null);
			return num;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	@Transactional
	public void markReads(UserPrincipal userPrincipal, MarkReadNotificationReuqest markReadNotificationReuqest) {
		notificationRepository.updateIsReadByIdInAndDeletedAt(true, markReadNotificationReuqest.getNotificationIds());
	}
	
	@Transactional
	public void markAllReads(UserPrincipal userPrincipal) {
		notificationRepository.updateIsReadByReceiverIdAndDeletedAt(true, userService.getUser(userPrincipal).getPropertyId());
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
