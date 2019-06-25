package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Notification;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.NotificationRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.NotificationResponse;
import com.japanwork.repository.notification.NotificationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private ConversationService conversationService;
	
	@Transactional
	public BaseDataResponse addNotification(UserPrincipal userPrincipal, UUID id, 
			NotificationRequest notificationRequest) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Notification notification = new Notification();
		notification.setSenderId(userPrincipal.getId());
		notification.setConversation(conversationService.findByIdAndIsDelete(id, false));
		notification.setCreateAt(timestamp);
		notification.setContent(notificationRequest.getContent());
		notification.setTitle("Message");
		notification.setNotificationType(1);
		notification.setDelete(false);
		Notification result = notificationRepository.save(notification);
		
		return new BaseDataResponse(converNotificationResponse(result));
	}
	
	public BaseDataMetaResponse listNotification(UUID id, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Notification> pages = notificationRepository.findByConversationIdAndIsDelete(
					PageRequest.of(page-1, paging), id, false);
			PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
			
			List<NotificationResponse> list = new ArrayList<NotificationResponse>();
			
			if(pages.getContent().size() > 0) {
				for (Notification notification : pages.getContent()) {
					list.add(converNotificationResponse(notification));
				}
			}
			
			BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
			return response;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public NotificationResponse converNotificationResponse(Notification notification) {
		NotificationResponse notificationResponse = new NotificationResponse();
		notificationResponse.setId(notification.getId());
		notificationResponse.setConversationId(notification.getConversation().getId());
		notificationResponse.setSenderId(notification.getSenderId());
		notificationResponse.setTitle(notification.getTitle());
		notificationResponse.setNotificationType(notification.getNotificationType());
		notificationResponse.setContent(notification.getContent());
		notificationResponse.setCreateAt(notification.getCreateAt());
		
		return notificationResponse;
	}
}
