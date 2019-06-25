package com.japanwork.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Conversation;
import com.japanwork.model.WebSocketChatMessage;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.ConversationService;

@Controller
public class ConversationController {
	@Autowired
	private ConversationService conversationService;
	
//	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_ALL)
//	@ResponseBody
	@MessageMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_ALL)
	@SendTo("/topic/javainuse")
	public BaseDataResponse createConversationAll(@PathVariable UUID id, @Payload WebSocketChatMessage webSocketChatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
		Conversation conversation = conversationService.createConversationAll(id);
		//return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
		return new BaseDataResponse(webSocketChatMessage);
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
