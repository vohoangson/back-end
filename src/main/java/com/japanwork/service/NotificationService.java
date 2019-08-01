package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Notification;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.NotificationRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.NotificationResponse;
import com.japanwork.repository.notification.NotificationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class NotificationService {
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Autowired 
	private RabbitTemplate rabbitTemplate;
	
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
	public Notification addMessage(UserPrincipal userPrincipal, UUID id,
			NotificationRequest notificationRequest) throws ForbiddenException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		UUID senderId = null;
		User user = userService.getUser(userPrincipal);

		Conversation conversation = conversationService.findByIdAndIsDelete(id, null);
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			senderId = candidateService.myCandidate(userPrincipal).getId();
			if(!conversation.getCandidate().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
			if(conversation.getCompany() != null) {
				this.addNotification(null, conversation.getCompany().getUser().getId(), notificationRequest.getContent(), 
						notificationRequest.getType(), conversation.getId());
			}
			
			if(conversation.getTranslator() != null) {
				this.addNotification(null, conversation.getTranslator().getUser().getId(), notificationRequest.getContent(), 
						notificationRequest.getType(), conversation.getId());
			}
		}

		if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			senderId = companyService.myCompany(userPrincipal).getId();
			if(!conversation.getCompany().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
			if(conversation.getCandidate() != null) {
				this.addNotification(null, conversation.getCandidate().getUser().getId(), notificationRequest.getContent(), 
						notificationRequest.getType(), conversation.getId());
			}
			
			if(conversation.getTranslator() != null) {
				this.addNotification(null, conversation.getTranslator().getUser().getId(), notificationRequest.getContent(), 
						notificationRequest.getType(), conversation.getId());
			}
		}

		if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			senderId = translatorService.myTranslator(userPrincipal).getId();
			if(!conversation.getTranslator().getId().equals(senderId)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
			
			if(conversation.getCompany() != null) {
				this.addNotification(null, conversation.getCompany().getUser().getId(), notificationRequest.getContent(), 
						notificationRequest.getType(), conversation.getId());
			}
			
			if(conversation.getCandidate() != null) {
				this.addNotification(null, conversation.getCandidate().getUser().getId(), notificationRequest.getContent(), 
						notificationRequest.getType(), conversation.getId());
			}
		}
		
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setObjectableId(conversationService.findByIdAndIsDelete(id, null).getId());
		notification.setNotificationType(notificationRequest.getType());
		notification.setCreatedAt(timestamp);
		notification.setContent(notificationRequest.getContent());		
		notification.setDeletedAt(null);
		Notification result = notificationRepository.save(notification);
		return result;
	}

	public void addNotification(UUID objectableId, UUID userId, String content, String type, UUID subObjectableId) throws ForbiddenException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Notification notification = new Notification();
		notification.setObjectableId(objectableId);
		notification.setNotificationType(type);
		notification.setCreatedAt(timestamp);
		notification.setContent(content);
		notification.setReceiverId(userId);
		notification.setSubObjectableId(subObjectableId);
		notification.setDeletedAt(null);
		Notification result = notificationRepository.save(notification);
		BaseDataResponse response = new BaseDataResponse(this.converNotificationResponse(result));
		rabbitTemplate.convertAndSend("notifications/"+result.getReceiverId(), ""+result.getReceiverId(), response);
	}
	public Page<Notification> notifications(UserPrincipal userPrincipal, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Notification> pages = notificationRepository.findByReceiverIdAndDeletedAt(
			        PageRequest.of(page-1, paging, Sort.by("createdAt").descending()), userPrincipal.getId(), null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public int unreadNumber(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		try {
			int num = notificationRepository.countByReceiverIdAndIsReadAndDeletedAt(userPrincipal.getId(), false, null);
			return num;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Page<Notification> listMessage(UUID id, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Notification> pages = notificationRepository.findByObjectableIdAndDeletedAt(
			        PageRequest.of(page-1, paging, Sort.by("createdAt").descending()), id, null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
    public UUID readerId(UserPrincipal userPrincipal) throws ResourceNotFoundException {
    	User user = userService.getUser(userPrincipal);
    	if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
    		Candidate candidate = candidateService.myCandidate(userPrincipal);
    		return candidate.getId();
    	} else if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
    		Company company = companyService.myCompany(userPrincipal);
    		return company.getId();
    	} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
    		Translator translator = translatorService.myTranslator(userPrincipal);
    		return translator.getId();
    	}
    	return null;
    }
	
	public NotificationResponse converNotificationResponse(Notification notification) {
		NotificationResponse notificationResponse = new NotificationResponse();
		notificationResponse.setId(notification.getId());
		notificationResponse.setObjectableId(notification.getObjectableId());
		notificationResponse.setNotificationType(notification.getNotificationType());
		notificationResponse.setReceiverId(notification.getReceiverId());
		notificationResponse.setSenderId(notification.getSenderId());
		notificationResponse.setContent(notification.getContent());
		notificationResponse.setSubObjectableId(notification.getSubObjectableId());
		notificationResponse.setCreateAt(notification.getCreatedAt());
		notificationResponse.setRead(notification.isRead());
		return notificationResponse;
	}
}
