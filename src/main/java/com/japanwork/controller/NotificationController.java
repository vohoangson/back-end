package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.NotificationRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.NotificationService;

@Controller
public class NotificationController {
	@Autowired 
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping(UrlConstant.URL_CONVERSATION_ID)
	@ResponseBody
	public BaseDataResponse addNotification(@CurrentUser UserPrincipal userPrincipal, @PathVariable UUID id, 
			@Valid @RequestBody NotificationRequest notificationRequest) {
		BaseDataResponse response = notificationService.addNotification(userPrincipal, id, notificationRequest);
		rabbitTemplate.convertAndSend("notifications/"+userPrincipal.getId(), ""+userPrincipal.getId(), response);
		return response;
	}
	
	@GetMapping(UrlConstant.URL_CONVERSATION_ID)
	@ResponseBody
	public BaseDataMetaResponse listNotification(@PathVariable UUID id, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		return notificationService.listNotification(id, page, paging);
	}
}
