package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.JobApplication;
import com.japanwork.payload.response.JobApplicationResponse;
import com.japanwork.repository.job_application.JobApplicationRepository;

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
	
	public JobApplication findByJobIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByJobIdAndDeletedAt(id, null);
	}
	
	public JobApplication findByIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByIdAndDeletedAt(id, null);
	}
	
	public JobApplication save(JobApplication jobApplication) {
		return jobApplicationRepository.save(jobApplication);
	}
	
	public JobApplicationResponse convertApplicationResponse(JobApplication jobApplication) {
		JobApplicationResponse ob = new JobApplicationResponse();
		ob.setId(jobApplication.getId());
		ob.setJob(jobService.convertJobResponse(jobApplication.getJob()));
		ob.setCandidate(candidateService.convertCandiateResponse(jobApplication.getCandidate()));
		ob.setTranslator(translatorService.convertTranslatorResponse(jobApplication.getTranslator()));
		ob.setSubmitApplicationAt(jobApplication.getSubmitApplicationAt());
		ob.setApproveApplicationAt(jobApplication.getApproveApplicationAt());
		ob.setRejectApplicationAt(jobApplication.getRejectApplicationAt());
		ob.setCandidateSupportConversaionId(jobApplication.getCandidateSupportConversaion().getId());
		ob.setCompanySupportConversationId(jobApplication.getCompanySupportConversation().getId());
		ob.setAllConversation(jobApplication.getAllConversation().getId());
		ob.setApplicationSucceedAt(jobApplication.getApplicationSucceedAt());
		ob.setCancelReason(jobApplication.getCancelReason());
		ob.setUserCancel(jobApplication.getUserCancel());
		ob.setCancelAt(jobApplication.getCancelAt());
		ob.setStatus(jobApplication.getStatus());
		
		return ob;
	}
}
