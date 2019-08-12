package com.japanwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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
import com.japanwork.model.Message;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.MessageRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.MessageResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.MessageService;

@Controller
public class MessageController {	
	@Autowired
	private MessageService messageService;
	
	@PostMapping(UrlConstant.URL_CONVERSATION)
	@ResponseBody
	public BaseDataResponse addMessage(@CurrentUser UserPrincipal userPrincipal, @PathVariable UUID id, 
			@Valid @RequestBody MessageRequest messageRequest) {
		Message message =  messageService.addMessage(userPrincipal, id, messageRequest);
		BaseDataResponse response = new BaseDataResponse(messageService.convertMassageResponse(message));
		return response;
	}
	
	@GetMapping(UrlConstant.URL_CONVERSATION)
	@ResponseBody
	public BaseDataMetaResponse messages(@PathVariable UUID id, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@CurrentUser UserPrincipal userPrincipal) {
		Page<Message> pages = messageService.listMessage(id, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<MessageResponse> list = new ArrayList<MessageResponse>();
		for (Message message : pages.getContent()) {
			list.add(messageService.convertMassageResponse(message));
		}
		return new BaseDataMetaResponse(list, pageInfo);
	}
}
