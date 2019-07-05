package com.japanwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Conversation;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.ConversationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.ConversationService;

@Controller
public class ConversationController {
	@Autowired
	private ConversationService conversationService;

	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_ALL)
	public BaseDataResponse createConversationAll(@PathVariable UUID id) {
		Conversation conversation = conversationService.createConversationAll(id);
		return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_CANDIDATE)
	@ResponseBody
	public BaseDataResponse createConversationSupportCandidate(@PathVariable UUID id) {
		Conversation conversation = conversationService.createConversationSupportCandidate(id);
		return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION_COMPANY)
	@ResponseBody
	public BaseDataResponse createConversationSupportCompany(@PathVariable UUID id) {
		Conversation conversation =  conversationService.createConversationSupportCompany(id);
		return new BaseDataResponse(conversationService.convertConversationResponse(conversation));
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_CONVERSATION)
	@ResponseBody
	public BaseDataResponse listConversationByUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable UUID id) {
		List<Conversation> list = conversationService.listConversationByUser(userPrincipal, id);
		List<ConversationResponse> listConversationResponse = new ArrayList<ConversationResponse>();
		if(list != null) {
			for (Conversation conversation : list) {
				listConversationResponse.add(conversationService.convertConversationResponse(conversation));
			}
		}
		return new BaseDataResponse(listConversationResponse);
	}
}
