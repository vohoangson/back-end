package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.model.Candidate;
import com.japanwork.model.RequestStatus;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.payload.response.JobApplicationResponse;
import com.japanwork.repository.job_application.JobApplicationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class JobApplicationService {
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private RequestStatusService historyStatusService;
	
	public JobApplication findByJobIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByJobIdAndDeletedAt(id, null);
	}
	
	public JobApplication findByIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByIdAndDeletedAt(id, null);
	}
	
	public JobApplication save(JobApplication jobApplication) {
		return jobApplicationRepository.save(jobApplication);
	}
	
	public JobApplicationResponse createJobApplication(UUID id, UserPrincipal userPrincipal) {
		JobApplication jobApplication = new JobApplication();
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		Job job = jobService.findByIdAndIsDelete(id);
		Candidate candidate = candidateService.myCandidate(userPrincipal);
		
		jobApplication.setJob(job);
		jobApplication.setCandidate(candidate);
		jobApplication.setCreatedAt(timestamp);
		jobApplication.setDeletedAt(null);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		
		RequestStatus historyStatus = historyStatusService.save(
				result.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE,
				CommonConstant.HistoryStatusTypes.JOB_APPLICATION,
				null,
				null);
		return this.convertApplicationResponse(jobApplication, historyStatus);
	}
	
	public JobApplicationResponse convertApplicationResponse(JobApplication jobApplication, RequestStatus historyStatus) {
		JobApplicationResponse ob = new JobApplicationResponse();
		ob.setId(jobApplication.getId());
		ob.setJob(jobService.convertJobResponse(jobApplication.getJob()));
		ob.setCandidate(candidateService.convertCandiateResponse(jobApplication.getCandidate()));
		if(jobApplication.getTranslator() != null) {
			ob.setTranslator(translatorService.convertTranslatorResponse(jobApplication.getTranslator()));
		}
		
		if(jobApplication.getCandidateSupportConversaion() != null) {
			ob.setCandidateSupportConversaionId(jobApplication.getCandidateSupportConversaion().getId());
		}
		
		if(jobApplication.getCompanySupportConversation() != null) {
			ob.setCompanySupportConversationId(jobApplication.getCompanySupportConversation().getId());
		}
		
		if(jobApplication.getAllConversation() != null) {
			ob.setAllConversation(jobApplication.getAllConversation().getId());
		}
				
		ob.setStatus(historyStatusService.convertRequestTranslationStatusResponse(historyStatus));
		ob.setCreatedAt(jobApplication.getCreatedAt());
		return ob;
	}
}
