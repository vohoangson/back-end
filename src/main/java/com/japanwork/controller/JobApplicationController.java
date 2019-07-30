package com.japanwork.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.JobApplication;
import com.japanwork.model.JobApplicationStatus;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.JobApplicationResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.JobApplicationService;

@Controller
public class JobApplicationController {
	@Autowired
	private JobApplicationService jobApplicationService;
	
	@PostMapping(UrlConstant.URL_JOB_APPLICATION_CANDIDATE_JOIN)
	@ResponseBody
	public BaseDataResponse createJobApplication(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		JobApplicationResponse jobApplicationResponse = jobApplicationService.createJobApplication(id, userPrincipal);
		return new BaseDataResponse(jobApplicationResponse);
	}
	
	@GetMapping(UrlConstant.URL_JOB_APPLICATION_ID)
	@ResponseBody
	public BaseDataResponse findJobApplicationById(@PathVariable UUID id) {
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		JobApplicationStatus status = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		return new BaseDataResponse(jobApplicationService.convertApplicationResponse(jobApplication, status));
	}
}
