package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.japanwork.exception.UnauthorizedException;
import com.japanwork.model.Candidate;
import com.japanwork.payload.request.CandidateExperienceRequest;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.payload.request.CandidateWishRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
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
			throw new BadRequestException(MessageConstant.CADIDATE_ALREADY);
		}
		
		return candidateService.savePersonal(candidatePersonalRequest, userPrincipal);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ID_PERSONAL)
	@ResponseBody
	public BaseDataResponse updateCandidatePersonal(@Valid @RequestBody CandidatePersonalRequest candidatePersonalRequest, 
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws UnauthorizedException{
		if(!checkPermission(userPrincipal, id)) {
			throw new UnauthorizedException(MessageConstant.ERROR_403);
		}
		return candidateService.updatePersonal(candidatePersonalRequest, id, userPrincipal);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ID_WISH)
	@ResponseBody
	public BaseDataResponse updateCandidateWish(@Valid @RequestBody CandidateWishRequest candidateWishRequest, 
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws UnauthorizedException{
		if(!checkPermission(userPrincipal, id)) {
			throw new UnauthorizedException(MessageConstant.ERROR_403);
		}
		return candidateService.updateWish(candidateWishRequest, id, userPrincipal);
	}
	
	@PostMapping(UrlConstant.URL_CANDIDATE_ID_EXPERIENCE)
	@ResponseBody
	public BaseDataResponse createCandidateExperience(@Valid @RequestBody CandidateExperienceRequest candidateExperienceRequest,
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws UnauthorizedException{	
		if(!checkPermission(userPrincipal, id)) {
			throw new UnauthorizedException(MessageConstant.ERROR_403);
		}
		return candidateService.createExperience(candidateExperienceRequest, id);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ID_EXPERIENCE)
	@ResponseBody
	public BaseDataResponse updateCandidateExperience(@Valid @RequestBody CandidateExperienceRequest candidateExperienceRequest,
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) throws UnauthorizedException{	
		if(!checkPermission(userPrincipal, id)) {
			throw new UnauthorizedException(MessageConstant.ERROR_403);
		}
		return candidateService.updateExperience(candidateExperienceRequest, id, userPrincipal);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE)
	@ResponseBody
	public BaseDataMetaResponse listCandidate(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging){		
		return candidateService.findAllByIsDelete(page, paging);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_ID)
	@ResponseBody
	public BaseDataResponse findCadidateByIdAndIsDelete(@PathVariable UUID id){		
		return candidateService.findByIdAndIsDelete(id);
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_ID)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {		
		return candidateService.isDel(id, true);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_UNDEL)
	@ResponseBody
	public BaseDataResponse unDel(@PathVariable UUID id) {		
		return candidateService.isDel(id, false);
	}
	
	private boolean checkPermission(UserPrincipal userPrincipal, UUID id) {
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_CANDIDATE")) {		
			Candidate candidate = candidateService.findCandidateByIdAndIsDelete(id);
			if(!candidate.getUser().getId().equals(userPrincipal.getId())) {
				return false;
			}
		}
		
		return true;
	}
}
