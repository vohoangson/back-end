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
import com.japanwork.support.CommonSupport;

@Service
public class NotificationService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
    private EmailSenderService emailSenderService;
	
	@Autowired
	private CommonSupport commonSupport;
	
	public void addNotification(UUID senderId, UUID conversationId, UUID objectableId, UUID receiverId, String content,
			String objectableType) throws ForbiddenException{
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setObjectableId(objectableId);
		notification.setNotificationType(objectableType);
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
//		this.sendMail(objectableId, objectableType, senderId, receiverId, content);
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
	
	public void sendMail(UUID objectableId, String objectableType, UUID senderId, UUID receiverId,String content) {
		User sender = commonSupport.loadUserById(senderId);
		User receiver = commonSupport.loadUserById(receiverId);
		String contentEmail = "";
		String subject = "";
		String name = "";
		String profile ="";
		String language = "?language="+sender.getCountry().getLanguage().getCode();
		
		if(objectableType.equals(CommonConstant.NotificationType.STATUS_REQUEST) || objectableType.equals(CommonConstant.NotificationType.MESSAGE_REQUEST)){
			if(objectableType.equals(CommonConstant.NotificationType.STATUS_REQUEST)){
				subject += "[" + content + "]" + " job application: " + objectableId.toString().substring(0, 5);
				contentEmail +=  "[" + content + "]" + " job application: " ;
				contentEmail += "<a href='http://jwork.club/request/"+objectableId+"'>" + objectableId.toString().substring(0, 5)+"</a>";
			} else {
				if(sender.getCompany() != null) {
					name += sender.getCompany().getName();
					profile += "/companies/"+sender.getCompany().getId() + language;  
				} else if(sender.getCandidate() != null) {
					name += sender.getCandidate().getFullName();
					profile += "/candidates/"+sender.getCandidate().getId() + language;
				} else {
					name += sender.getTranslator().getName();
					profile += "/translators/"+sender.getTranslator().getId() + language;
				}
				
				subject += name + " has send a message in request " + objectableId.toString().substring(0, 5);
				contentEmail += "<a href='http://jwork.club"+profile+"'>" + name + "</a> has send a message in request "
							+ "<a href='http://jwork.club/request/"+objectableId+"'>" + objectableId.toString().substring(0, 5)+"</a>";
			}
		} else {
			if(objectableType.equals(CommonConstant.NotificationType.STATUS_JOB_APPLICATION)) {
				contentEmail +=  "[" + content + "]" + " job application: " ;
				contentEmail += "<a href='http://jwork.club/job-applications/"+objectableId+"'>" + objectableId.toString().substring(0, 5)+"</a>";
			} else {
				if(sender.getCompany() != null) {
					name += sender.getCompany().getName();
					profile += "/companies/"+sender.getCompany().getId() + language;  
				} else if(sender.getCandidate() != null) {
					name += sender.getCandidate().getFullName();
					profile += "/candidates/"+sender.getCandidate().getId() + language;
				} else {
					name += sender.getTranslator().getName();
					profile += "/translators/"+sender.getTranslator().getId() + language;
				}
				
				subject += name + " has send a message in job application " + objectableId.toString().substring(0, 5);
				contentEmail += "<a href='http://jwork.club"+profile+"'>" + name + "</a> has send a message in job application "
							+ "<a href='http://jwork.club/job-applications/"+objectableId+"'>" + objectableId.toString().substring(0, 5)+"</a>";
			}
		}
		
		emailSenderService.sendEmail(receiver.getEmail(), subject, contentEmail);
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
