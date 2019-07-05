package com.japanwork.controller;

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
	public BaseDataResponse addNotification(@CurrentUser UserPrincipal userPrincipal, @PathVariable UUID id, 
			@Valid @RequestBody NotificationRequest notificationRequest) {
		Notification notification =  notificationService.addNotification(userPrincipal, id, notificationRequest);
		BaseDataResponse response = new BaseDataResponse(notificationService.converNotificationResponse(notification));
		Conversation conversation = conversationService.findByIdAndIsDelete(id, false);
		if(conversation.getCandidate() != null) {
			rabbitTemplate.convertAndSend("notifications/"+conversation.getCandidate().getUser().getId(), ""+conversation.getCandidate().getUser().getId(), response);
		}
		
		if(conversation.getCompany() != null) {
			rabbitTemplate.convertAndSend("notifications/"+conversation.getCompany().getUser().getId(), ""+conversation.getCompany().getUser().getId(), response);
		}
		
		rabbitTemplate.convertAndSend("notifications/"+conversation.getTranslator().getUser().getId(), ""+conversation.getTranslator().getUser().getId(), response);
		return response;
	}
	
	@GetMapping(UrlConstant.URL_CONVERSATION_ID)
	@ResponseBody
	public BaseDataMetaResponse listNotification(@PathVariable UUID id, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		Page<Notification> pages = notificationService.listNotification(id, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		
		List<NotificationResponse> list = new ArrayList<NotificationResponse>();
		
		if(pages.getContent().size() > 0) {
			for (Notification notification : pages.getContent()) {
				list.add(notificationService.converNotificationResponse(notification));
			}
		}
		
		return new BaseDataMetaResponse(list, pageInfo);
	}
}
