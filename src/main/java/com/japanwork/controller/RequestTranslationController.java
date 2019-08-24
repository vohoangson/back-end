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
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.RequestStatus;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelRequestTranslationRequest;
import com.japanwork.payload.request.RejectRequestTranslationRequest;
import com.japanwork.payload.request.RequestTranslationFilterRequest;
import com.japanwork.payload.request.RequestTranslationRequest;
import com.japanwork.payload.response.RequestTranslationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.RequestTranslationService;
import com.japanwork.support.CommonSupport;

@Controller
public class RequestTranslationController {

	@Autowired
	private RequestTranslationService requestTranslationService;

	@Autowired
	private CommonSupport commonSupport;

	@PostMapping(UrlConstant.URL_REQUEST_TRANSLATIONS)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody RequestTranslationRequest requestTranslationRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		List<RequestTranslationResponse> response = null;
		if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateByUser(userPrincipal.getId());
			if(requestTranslationService.checkRequestTranslation(requestTranslationRequest, candidate.getId())){
				throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ALREADY);
			}
			response = requestTranslationService.createRequestTranslationCanidate(requestTranslationRequest, candidate);
		} else {
			Company company = commonSupport.loadCompanyByUser(userPrincipal.getId());
			if(requestTranslationService.checkRequestTranslation(requestTranslationRequest, company.getId())){
				throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ALREADY);
			}
			if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY)) {
				response = requestTranslationService.createRequestTranslationCompany(requestTranslationRequest, company);
			} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB)) {
				response = requestTranslationService.createRequestTranslationJob(requestTranslationRequest, company);
			} else {
				response = requestTranslationService.createRequestTranslationJobApplication(requestTranslationRequest, company);
			}
		}
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_TRANSLATOR_JOIN)
	@ResponseBody
	public ResponseDataAPI translatorJoinRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		Translator translator = commonSupport.loadTranslatorByUser(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.translatorJoinRequestTranslation(
																							requestTranslation,
																							translator);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_ACCEPT_APPLY)
	@ResponseBody
	public ResponseDataAPI ownerAcceptApllyRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.ownerAcceptApllyRequestTranslation(
																								requestTranslation,
																								user);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_CONFIRM_FINISHED)
	@ResponseBody
	public ResponseDataAPI translatorConfirmFinishedRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.translatorConfirmFinishedRequestTranslation(
																								requestTranslation,
																								user);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_ACCEPT_FINISHED)
	@ResponseBody
	public ResponseDataAPI ownerAcceptFinishRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.ownerAcceptFinishRequestTranslation(
																									requestTranslation,
																									user);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_REFUSE_FINISHED)
	@ResponseBody
	public ResponseDataAPI ownerRefuseFinishRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.ownerRefuseFinishRequestTranslation(
																										requestTranslation,
																										user);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_CANCEL)
	@ResponseBody
	public ResponseDataAPI cancelRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal, @RequestBody CancelRequestTranslationRequest reasonCancel){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.cancelRequestTranslation(
																				requestTranslation,
																				user,
																				reasonCancel);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@PatchMapping(UrlConstant.URL_REQUEST_TRANSLATIONS_REJECT)
	@ResponseBody
	public ResponseDataAPI rejectRequestTranslation(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal, @RequestBody RejectRequestTranslationRequest reasonReject){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		RequestTranslationResponse response = requestTranslationService.rejectRequestTranslation(
																								requestTranslation,
																								user,
																								reasonReject);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				response,
				""
        );
	}

	@GetMapping(UrlConstant.URL_REQUEST_TRANSLATIONS)
	@ResponseBody
	public ResponseDataAPI requestTranslations(
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
		User user = commonSupport.loadUserById(userPrincipal.getId());
		if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			return requestTranslationService.requestTranslationsByCompany(user, filterRequest, page, paging);
		} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			if(yourRequest) {
				return requestTranslationService.requestTranslationsByTranslator(user, filterRequest, page, paging);
			} else {
				return requestTranslationService.newRequestTranslations(user, filterRequest, page, paging);
			}
		} else {
			return requestTranslationService.requestTranslationsByCandidate(user, filterRequest, page, paging);
		}

	}

	@GetMapping(UrlConstant.URL_REQUEST_TRANSLATION)
	@ResponseBody
	public ResponseDataAPI show(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = commonSupport.loadRequestTransationById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		requestTranslationService.checkPermission(requestTranslation, user);
		RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				requestTranslationService.convertRequestTranslationResponse(requestTranslation, status),
				""
        );
	}

	@GetMapping(UrlConstant.URL_JOB_APPLICATIONS_REQUEST)
	@ResponseBody
	public ResponseDataAPI requestTranslationByJobApplication(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		RequestTranslation requestTranslation = requestTranslationService.requestTranslationByJobApplication(id, userPrincipal);
		if(requestTranslation == null) {
			return new ResponseDataAPI(
					CommonConstant.ResponseDataAPIStatus.SUCCESS,
					"",
					""
            );
		}

		RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				requestTranslationService.convertRequestTranslationResponse(requestTranslation, status),
				""
        );
	}
}
