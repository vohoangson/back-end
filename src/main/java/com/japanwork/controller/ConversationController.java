package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public BaseDataResponse createConversation(@Valid @RequestBody ConversationRequest conversationRequest, 
			@CurrentUser UserPrincipal userPrincipal) {
		
		return conversationService.save(conversationRequest, userPrincipal);
	}
	
	@PatchMapping(UrlConstant.URL_CONVERSATION)
	@ResponseBody
	public BaseDataResponse addCandidate(@Valid @RequestBody ConversationRequest conversationRequest, 
			@CurrentUser UserPrincipal userPrincipal) {
		
		return conversationService.save(conversationRequest, userPrincipal);
	}
}
