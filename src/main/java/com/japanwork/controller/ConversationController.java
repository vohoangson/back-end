package com.japanwork.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.ConversationRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.ConversationService;

@Controller
public class ConversationController {
	@Autowired
	private ConversationService conversationService;
	
	@PostMapping(UrlConstant.URL_CONVERSATION)
	@ResponseBody
	public BaseDataResponse createConversation(@RequestBody ConversationRequest conversationRequest, 
			@CurrentUser UserPrincipal userPrincipal) {
		
		return conversationService.save(conversationRequest, userPrincipal);
	}
	
	@PatchMapping(UrlConstant.URL_CONVERSATION_ID)
	@ResponseBody
	public BaseDataResponse addCandidate(@RequestBody ConversationRequest conversationRequest, @PathVariable UUID id) {
		
		return conversationService.update(conversationRequest, id);
	}
}