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

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Conversation;
import com.japanwork.model.Notification;
import com.japanwork.model.User;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Transactional
	public Notification addNotification(UserPrincipal userPrincipal, UUID id, 
			NotificationRequest notificationRequest) throws ForbiddenException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		UUID senderId = null;
		User user = userService.getUser(userPrincipal);
		
		Conversation conversation = conversationService.findByIdAndIsDelete(id, null);
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			senderId = candidateService.myCandidate(userPrincipal).getUid();
			if(!conversation.getCandidate().getUid().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
		}
		
		if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			senderId = companyService.myCompany(userPrincipal).getUid();
			if(!conversation.getCompany().getUid().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
			
		}
		
		if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			senderId = translatorService.myTranslator(userPrincipal).getUid();
			if(!conversation.getTranslator().getUid().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
		}
		
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setConversation(conversationService.findByIdAndIsDelete(id, null));
		notification.setCreatedAt(timestamp);
		notification.setContent(notificationRequest.getContent());
		notification.setTitle("Message");
		notification.setNotificationType(1);
		notification.setDeletedAt(null);
		Notification result = notificationRepository.save(notification);
		
		return result;
	}
	
	public Page<Notification> listNotification(UUID id, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Notification> pages = notificationRepository.findByConversationUidAndDeletedAt(
					PageRequest.of(page-1, paging, Sort.by("createAt").descending()), id, null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public NotificationResponse converNotificationResponse(Notification notification) {
		NotificationResponse notificationResponse = new NotificationResponse();
		notificationResponse.setId(notification.getId());
		notificationResponse.setConversationId(notification.getConversation().getUid());
		notificationResponse.setSenderId(notification.getSenderId());
		notificationResponse.setTitle(notification.getTitle());
		notificationResponse.setNotificationType(notification.getNotificationType());
		notificationResponse.setContent(notification.getContent());
		notificationResponse.setCreateAt(notification.getCreatedAt());
		
		return notificationResponse;
	}
}
