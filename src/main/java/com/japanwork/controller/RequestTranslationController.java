package com.japanwork.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.QRequestStatus;
import com.japanwork.model.QRequestTranslation;
import com.japanwork.model.RequestStatus;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelRequestTranslationRequest;
import com.japanwork.payload.request.RejectRequestTranslationRequest;
import com.japanwork.payload.request.RequestTranslationRequest;
import com.japanwork.payload.response.OwnerResponse;
import com.japanwork.payload.response.RequestTranslationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.RequestTranslationService;
import com.japanwork.support.CommonSupport;
import com.querydsl.core.BooleanBuilder;

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

	@GetMapping(UrlConstant.URL_COMPANY_REQUEST)
	@ResponseBody
	public ResponseDataAPI indexByCompany(
			@RequestParam(name = "language") String languageCode,
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(defaultValue = "", name = "name") String name,
			@RequestParam(defaultValue = "", name = "request_types") Set<String> requestTypes,
			@RequestParam(defaultValue = "", name = "language_ids") Set<UUID> languageIds,
			@RequestParam(defaultValue = "", name = "post_date") String postDate,
			@CurrentUser UserPrincipal userPrincipal) throws ParseException{
		User user = commonSupport.loadUserById(userPrincipal.getId());
		Language language = commonSupport.loadLanguage(languageCode);
		Company company = commonSupport.loadCompanyById(user.getPropertyId());
		CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(company, language);

		OwnerResponse ownerResponse = new OwnerResponse(
				company.getId(),
				companyTranslation.getName(),
				company.getUser().getRole().replaceAll("ROLE_", ""),
				company.getLogoUrl());

		BooleanBuilder booleanBuilder = new BooleanBuilder();

		QRequestTranslation qRequestTranslation = QRequestTranslation.requestTranslation;

		if(!name.isEmpty()) {
			booleanBuilder.and(qRequestTranslation.name.likeIgnoreCase("%"+name+"%"));
		}

		if(languageIds.size() > 0) {
			booleanBuilder.and(qRequestTranslation.language.id.in(languageIds));
		}

		if(!postDate.isEmpty()) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(postDate);
			Timestamp timestamp = new Timestamp(date.getTime());
			booleanBuilder.and(qRequestTranslation.createdAt.goe(timestamp));
		}

		booleanBuilder.and(qRequestTranslation.ownerId.eq(user.getPropertyId()));

		if(requestTypes.size() > 0) {
			booleanBuilder.and(qRequestTranslation.objectableType.in(requestTypes));
		} else {
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE);
			booleanBuilder.and(qRequestTranslation.objectableType.notIn(requestTypes));
		}

		return requestTranslationService.index(booleanBuilder.getValue(), page, paging, ownerResponse);
	}

	@GetMapping(UrlConstant.URL_CANDIDATES_REQUESTS)
	@ResponseBody
	public ResponseDataAPI indexByCandidate(
			@RequestParam(name = "language") String languageCode,
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(defaultValue = "", name = "name") String name,
			@RequestParam(defaultValue = "", name = "language_ids") Set<UUID> languageIds,
			@RequestParam(defaultValue = "", name = "post_date") String postDate,
			@CurrentUser UserPrincipal userPrincipal) throws ParseException{
		User user = commonSupport.loadUserById(userPrincipal.getId());
		Language language = commonSupport.loadLanguage(languageCode);
		Candidate candidate = commonSupport.loadCandidateById(user.getPropertyId());
		CandidateTranslation candidateTranslation = commonSupport.loadCandidateTranslation(candidate, language);

		OwnerResponse ownerResponse = new OwnerResponse(
				candidate.getId(),
				candidateTranslation.getFullName(),
				candidate.getUser().getRole().replaceAll("ROLE_", ""),
				candidate.getAvatar());

		BooleanBuilder booleanBuilder = new BooleanBuilder();
		QRequestTranslation qRequestTranslation = QRequestTranslation.requestTranslation;

		if(!name.isEmpty()) {
			booleanBuilder.and(qRequestTranslation.name.likeIgnoreCase("%"+name+"%"));
		}

		if(languageIds.size() > 0) {
			booleanBuilder.and(qRequestTranslation.language.id.in(languageIds));
		}

		if(!postDate.isEmpty()) {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(postDate);
			Timestamp timestamp = new Timestamp(date.getTime());
			booleanBuilder.and(qRequestTranslation.createdAt.goe(timestamp));
		}

		booleanBuilder.and(qRequestTranslation.ownerId.eq(user.getPropertyId()));
		booleanBuilder.and(qRequestTranslation.objectableType.eq(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE));

		return requestTranslationService.index(booleanBuilder.getValue(), page, paging, ownerResponse);

	}

	@GetMapping(UrlConstant.URL_REQUEST_TRANSLATIONS)
	@ResponseBody
	public ResponseDataAPI indexByTranslator(
			@RequestParam(name = "language") String languageCode,
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(defaultValue = "", name = "name") String name,
			@RequestParam(defaultValue = "", name = "request_types") Set<String> requestTypes,
			@RequestParam(defaultValue = "", name = "language_ids") Set<UUID> languageIds,
			@RequestParam(defaultValue = "", name = "post_date") String postDate,
			@RequestParam(defaultValue = "false", name = "your_request") Boolean yourRequest,
			@CurrentUser UserPrincipal userPrincipal) throws ParseException{
		User user = commonSupport.loadUserById(userPrincipal.getId());
		Language language = commonSupport.loadLanguage(languageCode);

		BooleanBuilder booleanBuilder = new BooleanBuilder();
		if(yourRequest) {
			QRequestTranslation qRequestTranslation = QRequestStatus.requestStatus.requestTranslation;

			if(!name.isEmpty()) {
				booleanBuilder.and(qRequestTranslation.name.likeIgnoreCase("%"+name+"%"));
			}

			if(languageIds.size() > 0) {
				booleanBuilder.and(qRequestTranslation.language.id.in(languageIds));
			}

			if(!postDate.isEmpty()) {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(postDate);
				Timestamp timestamp = new Timestamp(date.getTime());
				booleanBuilder.and(qRequestTranslation.createdAt.goe(timestamp));
			}

			if(requestTypes.size() > 0) {
				booleanBuilder.and(qRequestTranslation.objectableType.in(requestTypes));
			}

			booleanBuilder.and(QRequestStatus.requestStatus.translator.id.eq(user.getPropertyId()));
			return requestTranslationService.indexByTranslator(booleanBuilder.getValue(), page, paging, language);
		} else {
			QRequestTranslation qRequestTranslation = QRequestTranslation.requestTranslation;

			if(!name.isEmpty()) {
				booleanBuilder.and(qRequestTranslation.name.likeIgnoreCase("%"+name+"%"));
			}

			if(languageIds.size() > 0) {
				booleanBuilder.and(qRequestTranslation.language.id.in(languageIds));
			}

			if(!postDate.isEmpty()) {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(postDate);
				Timestamp timestamp = new Timestamp(date.getTime());
				booleanBuilder.and(qRequestTranslation.createdAt.goe(timestamp));
			}

			if(requestTypes.size() > 0) {
				booleanBuilder.and(qRequestTranslation.objectableType.in(requestTypes));
			}
			booleanBuilder.and(qRequestTranslation.translator.id.isNull());
			return requestTranslationService.newRequestTranslations(booleanBuilder.getValue(), page, paging, language);
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
				requestTranslationService.convertRequestTranslationResponse(requestTranslationService.owner(requestTranslation), requestTranslation, status),
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
				requestTranslationService.convertRequestTranslationResponse(requestTranslationService.owner(requestTranslation), requestTranslation, status),
				""
        );
	}
}
