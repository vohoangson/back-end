package com.japanwork.controller;

import java.util.List;
import java.util.Set;
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

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.RequestStatus;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.User;
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
import com.japanwork.service.UserService;

@Controller
public class RequestTranslationController {
	
	@Autowired
	private RequestTranslationService requestTranslationService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(UrlConstant.URL_REQUEST_TRANSLATIONS)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody RequestTranslationRequest requestTranslationRequest, 
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		List<RequestTranslationResponse> response = null;
		if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			if(requestTranslationService.checkRequestTranslationByCandidate(requestTranslationRequest, userPrincipal)){
				throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ALREADY, MessageConstant.REQUEST_TRANSLATION_ALREADY_MSG);
			}
			response = requestTranslationService.createRequestTranslationCanidate(requestTranslationRequest, userPrincipal);
		} else {
			if(requestTranslationService.checkRequestTranslationByCompany(requestTranslationRequest, userPrincipal)){
				throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ALREADY, MessageConstant.REQUEST_TRANSLATION_ALREADY_MSG);
			}
			if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY)) {
				response = requestTranslationService.createRequestTranslationCompany(requestTranslationRequest, userPrincipal);
			} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB)) {
				response = requestTranslationService.createRequestTranslationJob(requestTranslationRequest, userPrincipal);
			} else {
				response = requestTranslationService.createRequestTranslationJobApplication(requestTranslationRequest, userPrincipal);
			}
		}
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
			@RequestParam(defaultValue = "", name = "name") String name,
			@RequestParam(defaultValue = "", name = "request_types") Set<String> requestTypes,
			@RequestParam(defaultValue = "", name = "language_ids") Set<UUID> languageIds,
			@RequestParam(defaultValue = "", name = "post_date") String postDate,
			@RequestParam(defaultValue = "false", name = "your_request") Boolean yourRequest,
			@CurrentUser UserPrincipal userPrincipal){
		
		RequestTranslationFilterRequest filterRequest = new RequestTranslationFilterRequest();
		filterRequest.setName(name);
		filterRequest.setRequestTypes(requestTypes);
		filterRequest.setLanguageIds(languageIds);
		filterRequest.setPostDate(postDate);
		filterRequest.setYourRequest(yourRequest);
		User user = userService.getUser(userPrincipal);
		if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			return requestTranslationService.requestTranslationsByCompany(userPrincipal, filterRequest, page, paging);
		} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			if(yourRequest) {
				return requestTranslationService.requestTranslationsByTranslator(userPrincipal, filterRequest, page, paging);
			} else {
				return requestTranslationService.newRequestTranslations(userPrincipal, filterRequest, page, paging);
			}
		} else {
			return requestTranslationService.requestTranslationsByCandidate(userPrincipal, filterRequest, page, paging);
		}
		
	}
	
	@GetMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_ID)
	@ResponseBody
	public BaseDataResponse requestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = requestTranslationService.requestTranslation(id, userPrincipal);
		RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
		return new BaseDataResponse(requestTranslationService.convertRequestTranslationResponse(requestTranslation, status));
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID_REQUEST)
	@ResponseBody
	public BaseDataResponse requestTranslationByJobApplication(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = requestTranslationService.requestTranslationByJobApplication(id, userPrincipal);
		if(requestTranslation == null) {
			return new BaseDataResponse(null);
		}
		
		RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
		return new BaseDataResponse(requestTranslationService.convertRequestTranslationResponse(requestTranslation, status));
	}
}
