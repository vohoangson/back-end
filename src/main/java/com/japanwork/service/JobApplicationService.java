package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.model.JobApplicationStatus;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelJobApplicationRequest;
import com.japanwork.payload.request.RejectJobApplicationRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
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
	private JobApplicationStatusService jobApplicationStatusService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestTranslationService requestTranslationService;
	
	@Autowired
	private ConversationService conversationService;
	
	public JobApplication findByIdAndIsDelete(UUID id) {
		return jobApplicationRepository.findByIdAndDeletedAt(id, null);
	}
	
	public JobApplication save(JobApplication jobApplication) {
		return jobApplicationRepository.save(jobApplication);
	}
	
	public JobApplicationResponse createJobApplication(UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException {		
		
		Job job = jobService.findByIdAndIsDelete(id);
		if(job == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		Candidate candidate = candidateService.myCandidate(userPrincipal);
		this.checkCandidateApplyJob( job, candidate);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		JobApplication jobApplication = new JobApplication();
		jobApplication.setJob(job);
		jobApplication.setCandidate(candidate);
		jobApplication.setCreatedAt(timestamp);
		jobApplication.setDeletedAt(null);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				result.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE,
				null);
		return this.convertApplicationResponse(jobApplication, status);
	}
	
	public JobApplicationResponse rejectCandiadte(RejectJobApplicationRequest rejectJobApplicationRequest, UUID id, 
			UserPrincipal userPrincipal) throws ResourceNotFoundException {
		JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
		if(jobApplication == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_REJECT_FAIL, 
					MessageConstant.JOB_APPLIACTION_REJECT_FAIL_MSG);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.REJECT_CANDIDATE,
				rejectJobApplicationRequest.getReason(),
				jobApplication.getJob().getCompany().getId(),
				null);
		
		jobApplication.setUpdatedAt(timestamp);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public JobApplicationResponse cancelJobApplication(CancelJobApplicationRequest cancelJobApplicationRequest, UUID id, 
			UserPrincipal userPrincipal) throws ResourceNotFoundException {
		JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
		if(jobApplication == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)
				&& !jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.ON_GOING)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_CANCEL_FAIL, 
					MessageConstant.JOB_APPLIACTION_CANCEL_FAIL_MSG);
		}
		
		if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
			requestTranslationService.cancelRequestTranslation(jobApplication.getId(), CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		User user = userService.getUser(userPrincipal);
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.CANCELED,
				cancelJobApplicationRequest.getReason(),
				user.getPropertyId(),
				null);
		
		jobApplication.setUpdatedAt(timestamp);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public JobApplicationResponse acceptApplyCandidate(UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException {
		JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
		if(jobApplication == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_ACCEPT_APPLY_FAIL, 
					MessageConstant.JOB_APPLIACTION_ACCEPT_APPLY_FAIL_MSG);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN,
				null);
		
		jobApplication.setUpdatedAt(timestamp);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public void translatorJoinJobApplication(UUID id, Translator translator) {
		JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
		if(jobApplication == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_ACCEPT_APPLY_FAIL, 
					MessageConstant.JOB_APPLIACTION_ACCEPT_APPLY_FAIL_MSG);
		}
		
		Conversation conversationSupportCandidate = conversationService.createConversationSupportCandidate(jobApplication.getTranslator(), 
				jobApplication.getCandidate());
		
		Conversation conversationSupportCompany = conversationService.createConversationSupportCompany(jobApplication.getTranslator(), 
				jobApplication.getJob().getCompany());
		
		Conversation conversationAll = conversationService.createConversationAll(jobApplication.getTranslator(), 
				jobApplication.getJob().getCompany(), jobApplication.getCandidate());
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		jobApplicationStatusService.save(
				jobApplication.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.ON_GOING,
				translator);
		
		jobApplication.setUpdatedAt(timestamp);
		jobApplication.setTranslator(translator);
		jobApplication.setAllConversation(conversationAll);
		jobApplication.setCandidateSupportConversaion(conversationSupportCandidate);
		jobApplication.setCompanySupportConversation(conversationSupportCompany);
		jobApplicationRepository.save(jobApplication);
	}
	
	public void cancelRequestTranslation(UUID id) {
		JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
		if(jobApplication == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}

		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		jobApplicationStatusService.save(
				jobApplication.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN,
				null);
		
		jobApplication.setUpdatedAt(timestamp);
		jobApplication.setTranslator(null);
		jobApplicationRepository.save(jobApplication);
	}
	
	public JobApplicationResponse approveCandidate(UUID id, UserPrincipal userPrincipal) throws ResourceNotFoundException {
		JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
		if(jobApplication == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.ON_GOING)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_APPROVE_CANDIDATE_FAIL, 
					MessageConstant.JOB_APPLIACTION_APPROVE_CANDIDATE_FAIL_MSG);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication.getId(), 
				timestamp, 
				CommonConstant.StatusApplyJob.FINISHED,
				jobApplicationStatus.getTranslator());
		
		jobApplication.setUpdatedAt(timestamp);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public void checkCandidateApplyJob(Job job, Candidate candidate) throws ForbiddenException, BadRequestException{
		JobApplication jobApplication = jobApplicationRepository.findByJobAndCandidateAndDeletedAt(job, candidate, null);
		if(jobApplication != null) {
			JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
			if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.REJECT_CANDIDATE) 
					|| jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.CANCELED)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_JOB_APPLICATION_MSG);
			} else {
				throw new BadRequestException(MessageConstant.JOB_APPLIACTION_APPLY_FAIL, MessageConstant.JOB_APPLIACTION_APPLY_FAIL_MSG);
			}
		}
	}
	
	public BaseDataMetaResponse show(UserPrincipal userPrincipal, int page, int paging) {
		User user = userService.getUser(userPrincipal);
		
		return null;
	}
	
	
	public JobApplicationResponse convertApplicationResponse(JobApplication jobApplication, JobApplicationStatus status) {
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
				
		ob.setStatus(jobApplicationStatusService.convertJobApplicationStatusResponse(status));
		ob.setCreatedAt(jobApplication.getCreatedAt());
		return ob;
	}
}
