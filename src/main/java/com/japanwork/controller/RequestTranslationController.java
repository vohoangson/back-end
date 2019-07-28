package com.japanwork.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.payload.request.CancelRequestTranslationRequest;
import com.japanwork.payload.request.RejectRequestTranslationRequest;
import com.japanwork.payload.request.RequestTranslationFilterRequest;
import com.japanwork.payload.request.RequestTranslationRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.RequestTranslationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.RequestTranslationService;

@Controller
public class RequestTranslationController {
	
	@Autowired
	private RequestTranslationService requestTranslationService;
	
	@PostMapping(UrlConstant.URL_REQUEST_TRANSLATION)
	@ResponseBody
	public BaseDataResponse createRequestTranslation(@Valid @RequestBody RequestTranslationRequest requestTranslationRequest, 
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		if(requestTranslationService.checkRequestTranslation(requestTranslationRequest, userPrincipal)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ALREADY, MessageConstant.REQUEST_TRANSLATION_ALREADY_MSG);
		}
		List<RequestTranslationResponse> response = requestTranslationService.createRequestTranslation(requestTranslationRequest, userPrincipal);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_TRANSLATOR_JOIN)
	@ResponseBody
	public BaseDataResponse translatorJoinRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslationResponse response = requestTranslationService.translatorJoinRequestTranslation(id, userPrincipal);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_ACCEPT_APPLY)
	@ResponseBody
	public BaseDataResponse ownerAcceptApllyRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslationResponse response = requestTranslationService.ownerAcceptApllyRequestTranslation(id, userPrincipal);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_CONFIRM_FINISHED)
	@ResponseBody
	public BaseDataResponse translatorConfirmFinishedRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslationResponse response = requestTranslationService.translatorConfirmFinishedRequestTranslation(id, userPrincipal);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_ACCEPT_FINISHED)
	@ResponseBody
	public BaseDataResponse ownerAcceptFinishRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslationResponse response = requestTranslationService.ownerAcceptFinishRequestTranslation(id, userPrincipal);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_REFUSE_FINISHED)
	@ResponseBody
	public BaseDataResponse ownerRefuseFinishRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslationResponse response = requestTranslationService.ownerRefuseFinishRequestTranslation(id, userPrincipal);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_CANCEL)
	@ResponseBody
	public BaseDataResponse cancelRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal, @RequestBody CancelRequestTranslationRequest reasonCancel){
		RequestTranslationResponse response = requestTranslationService.cancelRequestTranslation(id, userPrincipal, reasonCancel);
		return new BaseDataResponse(response);
	}
	
	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATION_REJECT)
	@ResponseBody
	public BaseDataResponse rejectRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal, @RequestBody RejectRequestTranslationRequest reasonReject){
		RequestTranslationResponse response = requestTranslationService.rejectRequestTranslation(id, userPrincipal, reasonReject);
		return new BaseDataResponse(response);
	}
	
	@GetMapping(UrlConstant.URL_REQUEST_TRANSLATIONS)
	@ResponseBody
	public BaseDataMetaResponse requestTranslations(
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(defaultValue = "", name = "request_types") String requestTypes,
			@RequestParam(defaultValue = "", name = "language_ids") String languageIds,
			@RequestParam(defaultValue = "", name = "post_date") String postDate,
			@RequestParam(defaultValue = "false", name = "your_request") Boolean yourRequest,
			@CurrentUser UserPrincipal userPrincipal){
		
		RequestTranslationFilterRequest filterRequest = new RequestTranslationFilterRequest();
		filterRequest.setRequestTypes(CommonFunction.listParam(requestTypes));
		filterRequest.setLanguageIds(CommonFunction.listParam(languageIds));
		filterRequest.setPostDate(postDate);
		filterRequest.setYourRequest(yourRequest);
		
		return requestTranslationService.requestTranslations(userPrincipal, filterRequest, page, paging);
	}
	
	@GetMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_ID)
	@ResponseBody
	public BaseDataResponse requestTranslation(@PathVariable UUID id){
		RequestTranslationResponse response = requestTranslationService.requestTranslation(id);
		return new BaseDataResponse(response);
	}
}
