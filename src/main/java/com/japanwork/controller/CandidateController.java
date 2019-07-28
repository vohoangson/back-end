package com.japanwork.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.Candidate;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.request.CandidateExperienceRequest;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.payload.request.CandidateWishRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.CandidateResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.CandidateService;
import com.japanwork.service.UserService;

@Controller
public class CandidateController {
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(UrlConstant.URL_CANDIDATE_PERSONAL)
	@ResponseBody
	public BaseDataResponse createCandidatePersonal (@Valid @RequestBody CandidatePersonalRequest candidatePersonalRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		if(candidateService.checkCandidateByUser(userService.findById(userPrincipal.getId()))) {
			throw new BadRequestException(MessageConstant.CANDIDATE_ALREADY, MessageConstant.CADIDATE_ALREADY_MSG);
		}
		Candidate candidate = candidateService.savePersonal(candidatePersonalRequest, userPrincipal);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ID_PERSONAL)
	@ResponseBody
	public BaseDataResponse updateCandidatePersonal(@Valid @RequestBody CandidatePersonalRequest candidatePersonalRequest, 
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws ForbiddenException{
		if(!checkPermission(userPrincipal, id)) {
			throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
		}
		Candidate candidate = candidateService.updatePersonal(candidatePersonalRequest, id, userPrincipal);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ID_WISH)
	@ResponseBody
	public BaseDataResponse updateCandidateWish(@Valid @RequestBody CandidateWishRequest candidateWishRequest, 
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws ForbiddenException{
		if(!checkPermission(userPrincipal, id)) {
			throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
		}
		
		Candidate candidate = candidateService.updateWish(candidateWishRequest, id, userPrincipal);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ID_EXPERIENCE)
	@ResponseBody
	public BaseDataResponse updateCandidateExperience(@Valid @RequestBody CandidateExperienceRequest candidateExperienceRequest,
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws ForbiddenException{	
		if(!checkPermission(userPrincipal, id)) {
			throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
		}
		Candidate candidate = candidateService.updateExperience(candidateExperienceRequest, id, userPrincipal);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE)
	@ResponseBody
	public BaseDataMetaResponse listCandidate(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging){	
		Page<Candidate> pages = candidateService.findAllByIsDelete(page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		
		List<CandidateResponse> list = new ArrayList<CandidateResponse>();
		
		if(pages.getContent().size() > 0) {
			for (Candidate candidate : pages.getContent()) {
				list.add(candidateService.convertCandiateResponse(candidate));
			}
		}
		
		BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
		return response;
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_IDS)
	@ResponseBody
	public BaseDataMetaResponse listCandidateByIds(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(name = "ids") Set<UUID> ids) {
		Page<Candidate> pages = candidateService.candidatesByIds(ids, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<CandidateResponse> list = new ArrayList<CandidateResponse>();

		if(pages.getContent().size() > 0) {
			for (Candidate candidate: pages.getContent()) {
				list.add(candidateService.convertCandiateResponse(candidate));
			}
		}

		return new BaseDataMetaResponse(list, pageInfo);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_ID)
	@ResponseBody
	public BaseDataResponse findCadidateByIdAndIsDelete(@PathVariable UUID id){		
		Candidate candidate = candidateService.findByIdAndIsDelete(id);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	@GetMapping(UrlConstant.URL_MY_CANDIDATE)
	@ResponseBody
	public BaseDataResponse myCandidate(@CurrentUser UserPrincipal userPrincipal){		
		Candidate candidate = candidateService.myCandidate(userPrincipal);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_ID)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		candidateService.isDel(id, timestamp);
		BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.CANDIDATE_DELETE_SUCCESS, MessageConstant.CANDIDATE_DELETE_SUCCESS_MSG);
		return new BaseDataResponse(baseMessageResponse);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_UNDEL)
	@ResponseBody
	public BaseDataResponse favoriteJob(@PathVariable UUID id) {		
		Candidate candidate = candidateService.isDel(id, null);
		return new BaseDataResponse(candidateService.convertCandiateResponse(candidate));
	}
	
	private boolean checkPermission(UserPrincipal userPrincipal, UUID id) {
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CANDIDATE")) {		
			Candidate candidate = candidateService.findByIdAndIsDelete(id);
			if(!candidate.getUser().getId().equals(userPrincipal.getId())) {
				return false;
			}
		}
		
		return true;
	}
}
