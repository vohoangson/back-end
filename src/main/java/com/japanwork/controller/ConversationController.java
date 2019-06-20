package com.japanwork.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.ConversationService;

@Controller
public class ConversationController {
	@Autowired
	private ConversationService conversationService;
	
	@GetMapping(UrlConstant.URL_JOB_ID_CONVERSATION_ALL)
	@ResponseBody
	public BaseDataResponse createConversationAll(@PathVariable UUID id) {
		
		return conversationService.createConversationAll(id);
	}
	
	@GetMapping(UrlConstant.URL_JOB_ID_CONVERSATION_CANDIDATE)
	@ResponseBody
	public BaseDataResponse createConversationSupportCandidate(@PathVariable UUID id) {
		
		return conversationService.createConversationSupportCandidate(id);
	}
	
	@GetMapping(UrlConstant.URL_JOB_ID_CONVERSATION_COMPANY)
	@ResponseBody
	public BaseDataResponse createConversationSupportCompany(@PathVariable UUID id) {
		
		return conversationService.createConversationSupportCompany(id);
	}
	
//	@GetMapping(UrlConstant.URL_JOB_ID_CONVERSATION_ALL)
//	@ResponseBody
//	public BaseDataResponse listConversationByUser(@CurrentUser UserPrincipal userPrincipal, 
//			@RequestParam(defaultValue = "1", name = "page") int page, 
//			@RequestParam(defaultValue = "25", name = "paging") int paging) {
//		return conversationService.listConversationByUser(userPrincipal, page, paging);
//	}
}
