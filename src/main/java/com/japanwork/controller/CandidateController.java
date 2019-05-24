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
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.CandidateAcademyRequest;
import com.japanwork.payload.request.CandidateExperiencesRequest;
import com.japanwork.payload.request.CandidateInfoRequest;
import com.japanwork.payload.request.CandidateJobRequest;
import com.japanwork.payload.request.CandidateLangugeRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.AcademyService;
import com.japanwork.service.CandidateService;
import com.japanwork.service.ExperienceService;
import com.japanwork.service.LanguageCertificateService;
import com.japanwork.service.UserService;

@Controller
public class CandidateController {
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AcademyService academyService;
	
	@Autowired
	private ExperienceService experienceService;
	
	@Autowired
	private LanguageCertificateService languageCertificateService;
	
	@PostMapping(UrlConstant.URL_CANDIDATE_INFO)
	@ResponseBody
	public BaseDataResponse createCandidateInfo(@Valid @RequestBody CandidateInfoRequest candidateInfoRequest, @CurrentUser UserPrincipal userPrincipal) {
		
		if(candidateService.checkCandidateByUser(userService.findById(userPrincipal.getId()))) {
			BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.INVALID_INPUT, MessageConstant.CADIDATE_ALREADY);
			BaseDataResponse baseDataResponse = new BaseDataResponse(baseMessageResponse);
			return baseDataResponse;
		}
		
		return candidateService.saveInfo(candidateInfoRequest, userPrincipal);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_INFO_ID)
	@ResponseBody
	public BaseDataResponse updateCandidateInfo(@Valid @RequestBody CandidateInfoRequest candidateInfoRequest, @PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return candidateService.updateInfo(candidateInfoRequest, id, userPrincipal);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_JOB_ID)
	@ResponseBody
	public BaseDataResponse updateCandidateJob(@Valid @RequestBody CandidateJobRequest candidateJobRequest, @PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return candidateService.updateJob(candidateJobRequest, id, userPrincipal);
	}
	
	@PostMapping(UrlConstant.URL_CANDIDATE_ACADEMY_ID)
	@ResponseBody
	public BaseDataResponse createCandidateAcademy(@Valid @RequestBody CandidateAcademyRequest candidateAcademyRequest, @PathVariable UUID id) {		
		return academyService.save(candidateAcademyRequest, id);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_ACADEMY_ID)
	@ResponseBody
	public BaseDataResponse updateCandidateAcademy(@Valid @RequestBody CandidateAcademyRequest candidateAcademyRequest, @PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return academyService.update(candidateAcademyRequest, id, userPrincipal);
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_ACADEMY_ID)
	@ResponseBody
	public BaseDataResponse deleteCandidateAcademy(@PathVariable UUID id) {		
		return academyService.del(id);
	}
	
	@PostMapping(UrlConstant.URL_CANDIDATE_EXPERIENCE_ID)
	@ResponseBody
	public BaseDataResponse createCandidateExperience(@Valid @RequestBody CandidateExperiencesRequest candidateExperiencesRequest, @PathVariable UUID id) {		
		return experienceService.save(candidateExperiencesRequest, id);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_EXPERIENCE_ID)
	@ResponseBody
	public BaseDataResponse updateCandidateExperience(@Valid @RequestBody CandidateExperiencesRequest candidateExperiencesRequest, @PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return experienceService.update(candidateExperiencesRequest, id, userPrincipal);
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_EXPERIENCE_ID)
	@ResponseBody
	public BaseDataResponse deleteCandidateExperience(@PathVariable UUID id) {		
		return experienceService.del(id);
	}
	
	@PostMapping(UrlConstant.URL_CANDIDATE_LANGUAGE_ID)
	@ResponseBody
	public BaseDataResponse createCandidateLanguage(@Valid @RequestBody CandidateLangugeRequest candidateLangugeRequest, @PathVariable UUID id) {		
		return languageCertificateService.save(candidateLangugeRequest, id);
	}
	
	@PatchMapping(UrlConstant.URL_CANDIDATE_LANGUAGE_ID)
	@ResponseBody
	public BaseDataResponse updateCandidateLanguage(@Valid @RequestBody CandidateLangugeRequest candidateLangugeRequest, @PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return languageCertificateService.update(candidateLangugeRequest, id, userPrincipal);
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_LANGUAGE_ID)
	@ResponseBody
	public BaseDataResponse deleteCandidateLanguage(@PathVariable UUID id) {		
		return languageCertificateService.del(id);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE)
	@ResponseBody
	public BaseDataResponse listCandidate(){		
		return candidateService.findAllByIsDelete();
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_ID)
	@ResponseBody
	public BaseDataResponse findCompanyByIdAndIsDelete(@PathVariable UUID id){		
		return candidateService.findByIdAndIsDelete(id);
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_ID)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {		
		return candidateService.del(id);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_UNDEL)
	@ResponseBody
	public BaseDataResponse unDel(@PathVariable UUID id) {		
		return candidateService.unDel(id);
	}
}
