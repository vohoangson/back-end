package com.japanwork.controller;

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

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.JobApplication;
import com.japanwork.model.JobApplicationStatus;
import com.japanwork.payload.request.CancelJobApplicationRequest;
import com.japanwork.payload.request.RejectJobApplicationRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.JobApplicationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.JobApplicationService;

@Controller
public class JobApplicationController {
	@Autowired
	private JobApplicationService jobApplicationService;
	
	@PostMapping(UrlConstant.URL_JOB_APPLICATIONS_CANDIDATE_JOIN)
	@ResponseBody
	public BaseDataResponse createJobApplication(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplicationResponse jobApplicationResponse = jobApplicationService.createJobApplication(id, userPrincipal);
		return new BaseDataResponse(jobApplicationResponse);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_COMPANY_REJECT)
	@ResponseBody
	public BaseDataResponse rejectCandiadte(@Valid @RequestBody RejectJobApplicationRequest rejectJobApplicationRequest,@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplicationResponse jobApplicationResponse = jobApplicationService.rejectCandiadte(rejectJobApplicationRequest, id, userPrincipal);
		return new BaseDataResponse(jobApplicationResponse);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_COMPANY_ACCEPT_APPLY)
	@ResponseBody
	public BaseDataResponse acceptApplyCandidate(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplicationResponse jobApplicationResponse = jobApplicationService.acceptApplyCandidate(id, userPrincipal);
		return new BaseDataResponse(jobApplicationResponse);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_CANCEL)
	@ResponseBody
	public BaseDataResponse cancelJobApplication(@Valid @RequestBody CancelJobApplicationRequest cancelJobApplicationRequest,@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplicationResponse jobApplicationResponse = jobApplicationService.cancelJobApplication(cancelJobApplicationRequest, id, userPrincipal);
		return new BaseDataResponse(jobApplicationResponse);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_COMPANY_APPROVE)
	@ResponseBody
	public BaseDataResponse approveJobApplication(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplicationResponse jobApplicationResponse = jobApplicationService.approveCandidate(id, userPrincipal);
		return new BaseDataResponse(jobApplicationResponse);
	}
	
	@GetMapping(UrlConstant.URL_COMPANY_JOB_APPLICATION)
	@ResponseBody
	public BaseDataMetaResponse indexByCompany(@CurrentUser UserPrincipal userPrincipal, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		return jobApplicationService.indexByCompany(userPrincipal, page, paging);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATES_JOB_APPLICATIONS)
	@ResponseBody
	public BaseDataMetaResponse indexByCandidate(@CurrentUser UserPrincipal userPrincipal, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		return jobApplicationService.indexByCandidate(userPrincipal, page, paging);
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATORS_JOB_APPLICATIONS)
	@ResponseBody
	public BaseDataMetaResponse indexByTranslator(@CurrentUser UserPrincipal userPrincipal, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		return jobApplicationService.indexByTranslator(userPrincipal, page, paging);
	}
	
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION)
	@ResponseBody
	public BaseDataResponse findJobApplicationById(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id, userPrincipal);
		JobApplicationStatus status = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		return new BaseDataResponse(jobApplicationService.convertApplicationResponse(jobApplication, status));
	}
}
