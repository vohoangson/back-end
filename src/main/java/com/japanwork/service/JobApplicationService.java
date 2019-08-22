package com.japanwork.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.Candidate;
import com.japanwork.model.Conversation;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.model.JobApplicationStatus;
import com.japanwork.model.PageInfo;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelJobApplicationRequest;
import com.japanwork.payload.request.RejectJobApplicationRequest;
import com.japanwork.payload.response.JobApplicationResponse;
import com.japanwork.repository.job_application.JobApplicationRepository;
import com.japanwork.repository.job_application_status.JobApplicationStatusRepository;

@Service
public class JobApplicationService {
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Autowired 
	private JobApplicationStatusRepository jobApplicationStatusRepository;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private JobApplicationStatusService jobApplicationStatusService;
	
	@Autowired
	private RequestTranslationService requestTranslationService;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private NotificationService notificationService;
	
	public JobApplicationResponse create(Job job, Candidate candidate) {		
		this.checkCandidateApplyJob( job, candidate);
		
		JobApplication jobApplication = new JobApplication();
		jobApplication.setJob(job);
		jobApplication.setCandidate(candidate);
		jobApplication.setCreatedAt(CommonFunction.dateTimeNow());
		jobApplication.setDeletedAt(null);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				result, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE);
		
		notificationService.addNotification(
				result.getCandidate().getId(),
				null,
				result.getId(),
				result.getJob().getCompany().getId(), 
				CommonConstant.NotificationContent.CANDIDATE_JOINED,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				result.getJob().getCompany().getUser().getId());
		return this.convertApplicationResponse(jobApplication, status);
	}
	
	public JobApplicationResponse rejectCandidate(RejectJobApplicationRequest rejectJobApplicationRequest, 
			JobApplication jobApplication) {
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_REJECT_FAIL);
		}

		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.REJECT_CANDIDATE,
				rejectJobApplicationRequest.getReason(),
				jobApplication.getJob().getCompany().getId());
		
		notificationService.addNotification(
				jobApplication.getJob().getCompany().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getId(), 
				CommonConstant.NotificationContent.CONPANY_REJECT_APPLY,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getCandidate().getUser().getId());
		
		jobApplication.setUpdatedAt(CommonFunction.dateTimeNow());
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public JobApplicationResponse cancelJobApplication(CancelJobApplicationRequest cancelJobApplicationRequest, 
			JobApplication jobApplication, User user) {
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)
				&& !jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.ON_GOING)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_CANCEL_FAIL);
		}
		
		if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
			if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
				requestTranslationService.cancelRequestTranslation(jobApplication.getId(), user.getPropertyId(), 
						CommonConstant.NotificationContent.CANDIDATE_CANCEL);
			} else {
				requestTranslationService.cancelRequestTranslation(jobApplication.getId(), user.getPropertyId(), 
						CommonConstant.NotificationContent.COMPANY_CANCEL);
			}
			
		}
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.CANCELED,
				cancelJobApplicationRequest.getReason(),
				user.getPropertyId());
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			notificationService.addNotification(
					jobApplication.getCandidate().getId(),
					null,
					jobApplication.getId(),
					jobApplication.getJob().getCompany().getId(), 
					CommonConstant.NotificationContent.CANDIDATE_CANCEL,
					CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
					jobApplication.getJob().getCompany().getUser().getId());
			if(jobApplication.getTranslator() != null) {
				notificationService.addNotification(
						jobApplication.getCandidate().getId(),
						null,
						jobApplication.getId(),
						jobApplication.getTranslator().getId(), 
						CommonConstant.NotificationContent.CANDIDATE_CANCEL,
						CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
						jobApplication.getTranslator().getUser().getId());
			}
		} else {
			String content = "";
			if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
				content = CommonConstant.NotificationContent.COMPANY_CANCEL;
			} else {
				content = CommonConstant.NotificationContent.COMPANY_REFUSED_CANDIDATE;
			}
			notificationService.addNotification(
					jobApplication.getJob().getCompany().getId(),
					null,
					jobApplication.getId(),
					jobApplication.getCandidate().getId(), 
					content,
					CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
					jobApplication.getCandidate().getUser().getId());
			
			if(jobApplication.getTranslator() != null) {
				notificationService.addNotification(
						jobApplication.getJob().getCompany().getId(),
						null,
						jobApplication.getId(),
						jobApplication.getTranslator().getId(), 
						content,
						CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
						jobApplication.getTranslator().getUser().getId());
			}
		}
		
		
		jobApplication.setUpdatedAt(CommonFunction.dateTimeNow());
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public JobApplicationResponse acceptApplyCandidate(JobApplication jobApplication) {		
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_ACCEPT_APPLY_FAIL);
		}
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN);
		
		notificationService.addNotification(
				jobApplication.getJob().getCompany().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getId(), 
				CommonConstant.NotificationContent.CONPANY_ACCEPTED_APPLY,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getCandidate().getUser().getId());
		
		jobApplication.setUpdatedAt(CommonFunction.dateTimeNow());
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}
	
	public void translatorJoinJobApplication(JobApplication jobApplication, Translator translator) {		
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_ACCEPT_APPLY_FAIL);
		}
		
		Conversation conversationSupportCandidate = conversationService.create(translator, null, 
				jobApplication.getCandidate());
		
		Conversation conversationSupportCompany = conversationService.create(translator, 
				jobApplication.getJob().getCompany(), null);
		
		Conversation conversationAll = conversationService.create(translator, jobApplication.getJob().getCompany(), 
				jobApplication.getCandidate());
		
		jobApplication.setUpdatedAt(CommonFunction.dateTimeNow());
		jobApplication.setTranslator(translator);
		jobApplication.setAllConversation(conversationAll);
		jobApplication.setCandidateSupportConversaion(conversationSupportCandidate);
		jobApplication.setCompanySupportConversation(conversationSupportCompany);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		
		jobApplicationStatusService.save(
				result, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.ON_GOING);
		
		notificationService.addNotification(
				translator.getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getId(), 
				CommonConstant.NotificationContent.HELPER_JOINED,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getCandidate().getUser().getId());
		
		notificationService.addNotification(
				translator.getId(),
				null,
				jobApplication.getId(),
				jobApplication.getJob().getCompany().getId(), 
				CommonConstant.NotificationContent.HELPER_JOINED,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getJob().getCompany().getUser().getId());
	}
	
	public void cancelRequestTranslation(JobApplication jobApplication, UUID userCreateId, String reason) {
		jobApplicationStatusService.save(
				jobApplication, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.CANCELED_TRANSLATOR,
				reason,
				userCreateId);
		
		notificationService.addNotification(
				jobApplication.getTranslator().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getId(), 
				CommonConstant.NotificationContent.HELPER_CANCEL,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getCandidate().getUser().getId());
		
		notificationService.addNotification(
				jobApplication.getTranslator().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getJob().getCompany().getId(), 
				CommonConstant.NotificationContent.HELPER_CANCEL,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getJob().getCompany().getUser().getId());
		
		jobApplication.setUpdatedAt(CommonFunction.dateTimeNow());
		jobApplication.setTranslator(null);
		JobApplication result = jobApplicationRepository.save(jobApplication);
		
		jobApplicationStatusService.save(
				result, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN);
	}
	
	public JobApplicationResponse approveCandidate(JobApplication jobApplication) {
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.ON_GOING)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_APPROVE_CANDIDATE_FAIL);
		}
		
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.StatusApplyJob.FINISHED);
		
		jobApplication.setUpdatedAt(CommonFunction.dateTimeNow());
		JobApplication result = jobApplicationRepository.save(jobApplication);
		
		notificationService.addNotification(
				jobApplication.getJob().getCompany().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getId(), 
				CommonConstant.NotificationContent.COMPANY_ACCEPTED_CANDIDATE,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getCandidate().getUser().getId());
		
		notificationService.addNotification(
				jobApplication.getJob().getCompany().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getTranslator().getId(), 
				CommonConstant.NotificationContent.COMPANY_ACCEPTED_CANDIDATE,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION,
				jobApplication.getTranslator().getUser().getId());
		return this.convertApplicationResponse(result, status);
	}
	
	public void checkCandidateApplyJob(Job job, Candidate candidate) throws ForbiddenException, BadRequestException{
		JobApplication jobApplication = jobApplicationRepository.findByJobAndCandidateAndDeletedAt(job, candidate, null);
		if(jobApplication != null) {
			JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
			if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.REJECT_CANDIDATE) 
					|| jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.CANCELED)) {
				throw new ForbiddenException(MessageConstant.JOB_APPLICATION_FORBIDDEN_ERROR);
			} else {
				throw new BadRequestException(MessageConstant.JOB_APPLIACTION_APPLY_FAIL);
			}
		}
	}
	
	public ResponseDataAPI indexByCompany(User user, int page, int paging) {
		Page<JobApplication> pages = jobApplicationRepository.findAllByJobCompanyIdAndDeletedAt(PageRequest.of(page-1, paging), user.getPropertyId(),null);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<JobApplicationResponse> list = new ArrayList<JobApplicationResponse>();

		if(pages.getContent().size() > 0) {
			for (JobApplication jobApplication : pages.getContent()) {
				JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
				list.add(this.convertApplicationResponse(jobApplication, jobApplicationStatus));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS, 
				list, 
				pageInfo, 
				null);
	}
	
	public ResponseDataAPI indexByCandidate(User user, int page, int paging) {
		Page<JobApplication> pages = jobApplicationRepository.findAllByCandidateIdAndDeletedAt(PageRequest.of(page-1, paging), user.getPropertyId(),null);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<JobApplicationResponse> list = new ArrayList<JobApplicationResponse>();

		if(pages.getContent().size() > 0) {
			for (JobApplication jobApplication : pages.getContent()) {
				JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
				list.add(this.convertApplicationResponse(jobApplication, jobApplicationStatus));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS, 
				list, 
				pageInfo, 
				null);
	}
	
	public ResponseDataAPI indexByTranslator(User user, int page, int paging) {
		Page<JobApplicationStatus> pages = jobApplicationStatusRepository.findByTranslator(PageRequest.of(page-1, paging), user.getPropertyId());
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<JobApplicationResponse> list = new ArrayList<JobApplicationResponse>();

		if(pages.getContent().size() > 0) {
			for (JobApplicationStatus jobApplicationStatus : pages.getContent()) {
				JobApplication jobApplication = jobApplicationStatus.getJobApplication();
				if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.CANCELED_TRANSLATOR)) {
					jobApplication.setTranslator(jobApplicationStatus.getTranslator());
				}
				list.add(this.convertApplicationResponse(jobApplication, jobApplicationStatus));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS, 
				list, 
				pageInfo, 
				null);
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
