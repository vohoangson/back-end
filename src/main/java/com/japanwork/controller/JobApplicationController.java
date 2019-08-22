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

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Candidate;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.model.JobApplicationStatus;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelJobApplicationRequest;
import com.japanwork.payload.request.RejectJobApplicationRequest;
import com.japanwork.payload.response.JobApplicationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.JobApplicationService;
import com.japanwork.support.CommonSupport;

@Controller
public class JobApplicationController {
	@Autowired
	private JobApplicationService jobApplicationService;
	
	@Autowired
	private CommonSupport commonSupport;
	
	@PostMapping(UrlConstant.URL_JOB_APPLICATIONS_CANDIDATE_JOIN)
	@ResponseBody
	public ResponseDataAPI create(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		Job job = commonSupport.loadJobById(id);
		Candidate candidate = commonSupport.loadCandidateByUser(userPrincipal.getId());
		JobApplicationResponse jobApplicationResponse = jobApplicationService.create(job, candidate);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, jobApplicationResponse, null, null);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_COMPANY_REJECT)
	@ResponseBody
	public ResponseDataAPI rejectCandidate(@Valid @RequestBody RejectJobApplicationRequest rejectJobApplicationRequest,
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplication jobApplication = commonSupport.loadJobApplicationById(id, userPrincipal.getId());
		JobApplicationResponse jobApplicationResponse = jobApplicationService
															.rejectCandidate(
																rejectJobApplicationRequest, 
																jobApplication);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, jobApplicationResponse, null, null);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_COMPANY_ACCEPT_APPLY)
	@ResponseBody
	public ResponseDataAPI acceptApplyCandidate(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplication jobApplication = commonSupport.loadJobApplicationById(id, userPrincipal.getId());
		JobApplicationResponse jobApplicationResponse = jobApplicationService.acceptApplyCandidate(jobApplication);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, jobApplicationResponse, null, null);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_CANCEL)
	@ResponseBody
	public ResponseDataAPI cancelJobApplication(@Valid @RequestBody CancelJobApplicationRequest cancelJobApplicationRequest,
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplication jobApplication = commonSupport.loadJobApplicationById(id, userPrincipal.getId());
		User user = commonSupport.loadUserById(userPrincipal.getId());
		JobApplicationResponse jobApplicationResponse = jobApplicationService
															.cancelJobApplication(
																cancelJobApplicationRequest, 
																jobApplication, 
																user);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, jobApplicationResponse, null, null);
	}
	
	@PatchMapping(UrlConstant.URL_JOB_APPLICATIONS_COMPANY_APPROVE)
	@ResponseBody
	public ResponseDataAPI approveJobApplication(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplication jobApplication = commonSupport.loadJobApplicationById(id, userPrincipal.getId());
		JobApplicationResponse jobApplicationResponse = jobApplicationService.approveCandidate(jobApplication);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, jobApplicationResponse, null, null);
	}
	
	@GetMapping(UrlConstant.URL_COMPANY_JOB_APPLICATION)
	@ResponseBody
	public ResponseDataAPI indexByCompany(@CurrentUser UserPrincipal userPrincipal, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		User user = commonSupport.loadUserById(userPrincipal.getId());
		return jobApplicationService.indexByCompany(user, page, paging);
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATES_JOB_APPLICATIONS)
	@ResponseBody
	public ResponseDataAPI indexByCandidate(@CurrentUser UserPrincipal userPrincipal, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		User user = commonSupport.loadUserById(userPrincipal.getId());
		return jobApplicationService.indexByCandidate(user, page, paging);
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATORS_JOB_APPLICATIONS)
	@ResponseBody
	public ResponseDataAPI indexByTranslator(@CurrentUser UserPrincipal userPrincipal, 
			@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging) {
		User user = commonSupport.loadUserById(userPrincipal.getId());
		return jobApplicationService.indexByTranslator(user, page, paging);
	}
	
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION)
	@ResponseBody
	public ResponseDataAPI show(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplication jobApplication = commonSupport.loadJobApplicationById(id, userPrincipal.getId());
		JobApplicationStatus status = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS, 
				jobApplicationService.convertApplicationResponse(jobApplication, status), 
				null, 
				null);
	}
}
