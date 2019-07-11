package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Notification;
import com.japanwork.payload.request.NotificationRequest;
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
	public Notification addNotification(UserPrincipal userPrincipal, UUID id, 
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
		
		return result;
	}
	
	public Page<Notification> listNotification(UUID id, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Notification> pages = notificationRepository.findByConversationIdAndIsDelete(
					PageRequest.of(page-1, paging, Sort.by("createAt").descending()), id, false);
			return pages;
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
