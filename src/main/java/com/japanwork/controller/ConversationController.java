package com.japanwork.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Conversation;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.ConversationService;

@Controller
public class ConversationController {
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;

	@MessageMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_ALL)
	public BaseDataResponse createConversationAll(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal, 
			SimpMessageHeaderAccessor headerAccessor) {
		Conversation conversation = conversationService.createConversationAll(id);
		headerAccessor.getSessionAttributes().put("username", userPrincipal.getId());
		messagingTemplate.convertAndSend(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_ALL, conversation);
		return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_CANDIDATE)
	@ResponseBody
	public BaseDataResponse createConversationSupportCandidate(@PathVariable UUID id) {
		
		return conversationService.createConversationSupportCandidate(id);
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_COMPANY)
	@ResponseBody
	public BaseDataResponse createConversationSupportCompany(@PathVariable UUID id) {
		
		return conversationService.createConversationSupportCompany(id);
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION)
	@ResponseBody
	public BaseDataResponse listConversationByUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable UUID id) {
		return conversationService.listConversationByUser(userPrincipal, id);
	}
}
