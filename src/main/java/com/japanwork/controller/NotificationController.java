package com.japanwork.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Conversation;
import com.japanwork.model.Notification;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.NotificationRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.NotificationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.ConversationService;
import com.japanwork.service.NotificationService;

@Controller
public class NotificationController {
	@Autowired 
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private ConversationService conversationService;
	
	@PostMapping(UrlConstant.URL_CONVERSATION_ID)
	@ResponseBody
	public BaseDataResponse addMessage(@CurrentUser UserPrincipal userPrincipal, @PathVariable UUID id, 
			@Valid @RequestBody NotificationRequest notificationRequest) {
		Notification notification =  notificationService.addMessage(userPrincipal, id, notificationRequest);
		BaseDataResponse response = new BaseDataResponse(notificationService.converNotificationResponse(notification, null));
		Conversation conversation = conversationService.findByIdAndIsDelete(id, null);
		if(conversation.getCandidate() != null) {
			rabbitTemplate.convertAndSend("notifications/"+conversation.getCandidate().getUser().getId(), ""+conversation.getCandidate().getUser().getId(), response);
		}
		
		if(conversation.getCompany() != null) {
			rabbitTemplate.convertAndSend("notifications/"+conversation.getCompany().getUser().getId(), ""+conversation.getCompany().getUser().getId(), response);
		}
		
		rabbitTemplate.convertAndSend("notifications/"+conversation.getTranslator().getUser().getId(), ""+conversation.getTranslator().getUser().getId(), response);
		return response;
	}
	
	@GetMapping(UrlConstant.URL_NOTIFICATION)
	@ResponseBody
	public BaseDataMetaResponse notifications(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@CurrentUser UserPrincipal userPrincipal) {
		Page<Notification> pages = notificationService.notifications(userPrincipal, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		
		List<NotificationResponse> list = new ArrayList<NotificationResponse>();
		
		if(pages.getContent().size() > 0) {
			for (Notification notification : pages.getContent()) {
				Timestamp readAt = notificationService.readAt(notification.getId(), userPrincipal, null);
				list.add(notificationService.converNotificationResponse(notification, readAt));
			}
		}
		
		return new BaseDataMetaResponse(list, pageInfo);
	} 
	
//	@GetMapping(UrlConstant.URL_NOTIFICATION_UNREADS_NUMBER)
//	@ResponseBody
//	public BaseDataMetaResponse unreadNumber( @CurrentUser UserPrincipal userPrincipal) {
//		Page<Notification> pages = notificationService.unreadNumber(userPrincipal);
//		return new BaseDataMetaResponse(pages, null);
//	} 
	
	@GetMapping(UrlConstant.URL_CONVERSATION_ID)
	@ResponseBody
	public BaseDataMetaResponse messages(@PathVariable UUID id, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@CurrentUser UserPrincipal userPrincipal) {
		Page<Notification> pages = notificationService.listMessage(id, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		
		List<NotificationResponse> list = new ArrayList<NotificationResponse>();
		
		if(pages.getContent().size() > 0) {
			for (Notification notification : pages.getContent()) {
				Timestamp readAt = notificationService.readAt(notification.getId(), userPrincipal, null);
				list.add(notificationService.converNotificationResponse(notification, readAt));
			}
		}
		
		return new BaseDataMetaResponse(list, pageInfo);
	}
}
