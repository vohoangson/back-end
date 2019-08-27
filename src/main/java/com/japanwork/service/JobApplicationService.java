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
import com.japanwork.model.Company;
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
		jobApplication.setCreatedAt(CommonFunction.getCurrentDateTime());
		jobApplication.setDeletedAt(null);
		JobApplication result = jobApplicationRepository.save(jobApplication);

		JobApplicationStatus status = jobApplicationStatusService.save(
				result,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE);

		notificationService.addNotification(
				result.getCandidate().getUser().getId(),
				null,
				result.getId(),
				result.getJob().getCompany().getUser().getId(),
				CommonConstant.NotificationContent.CANDIDATE_JOINED,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);
		return this.convertApplicationResponse(jobApplication, status);
	}

	public JobApplicationResponse rejectCandidate(RejectJobApplicationRequest rejectJobApplicationRequest,
			JobApplication jobApplication) {
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_COMPANY_APPROVE_CANDIDATE)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_REJECT_FAIL);
		}

		Company company = jobApplication.getJob().getCompany();
		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.REJECT_CANDIDATE,
				rejectJobApplicationRequest.getReason(),
				company.getId());

		notificationService.addNotification(
				company.getUser().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getUser().getId(),
				CommonConstant.NotificationContent.CONPANY_REJECT_APPLY,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

		jobApplication.setUpdatedAt(CommonFunction.getCurrentDateTime());
		JobApplication result = jobApplicationRepository.save(jobApplication);
		return this.convertApplicationResponse(result, status);
	}

	public JobApplicationResponse cancelJobApplication(CancelJobApplicationRequest cancelJobApplicationRequest,
			JobApplication jobApplication, User user) {
		UUID candidateUserId = jobApplication.getCandidate().getUser().getId();
		UUID companyUserId = jobApplication.getJob().getCompany().getUser().getId();
		UUID translatorUserId = jobApplication.getTranslator().getUser().getId();
		UUID jobApplicationId = jobApplication.getId();
		
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)
				&& !jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.ON_GOING)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_CANCEL_FAIL);
		}

		if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
			if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
				requestTranslationService.cancelRequestTranslation(jobApplicationId, user.getPropertyId(),
						CommonConstant.NotificationContent.CANDIDATE_CANCEL);
			} else {
				requestTranslationService.cancelRequestTranslation(jobApplicationId, user.getPropertyId(),
						CommonConstant.NotificationContent.COMPANY_CANCEL);
			}
		}

		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.CANCELED,
				cancelJobApplicationRequest.getReason(),
				user.getPropertyId());
		
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			notificationService.addNotification(
					candidateUserId,
					null,
					jobApplicationId,
					companyUserId,
					CommonConstant.NotificationContent.CANDIDATE_CANCEL,
					CommonConstant.NotificationType.STATUS_JOB_APPLICATION);
			if(jobApplication.getTranslator() != null) {
				notificationService.addNotification(
						candidateUserId,
						null,
						jobApplicationId,
						translatorUserId,
						CommonConstant.NotificationContent.CANDIDATE_CANCEL,
						CommonConstant.NotificationType.STATUS_JOB_APPLICATION);
			}
		} else {
			String content = "";
			if(jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN)) {
				content = CommonConstant.NotificationContent.COMPANY_CANCEL;
			} else {
				content = CommonConstant.NotificationContent.COMPANY_REFUSED_CANDIDATE;
			}
			notificationService.addNotification(
					companyUserId,
					null,
					jobApplicationId,
					candidateUserId,
					content,
					CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

			if(jobApplication.getTranslator() != null) {
				notificationService.addNotification(
						companyUserId,
						null,
						jobApplicationId,
						translatorUserId,
						content,
						CommonConstant.NotificationType.STATUS_JOB_APPLICATION);
			}
		}


		jobApplication.setUpdatedAt(CommonFunction.getCurrentDateTime());
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
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN);

		notificationService.addNotification(
				jobApplication.getJob().getCompany().getUser().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getUser().getId(),
				CommonConstant.NotificationContent.CONPANY_ACCEPTED_APPLY,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

		jobApplication.setUpdatedAt(CommonFunction.getCurrentDateTime());
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

		jobApplication.setUpdatedAt(CommonFunction.getCurrentDateTime());
		jobApplication.setTranslator(translator);
		jobApplication.setAllConversation(conversationAll);
		jobApplication.setCandidateSupportConversaion(conversationSupportCandidate);
		jobApplication.setCompanySupportConversation(conversationSupportCompany);
		JobApplication result = jobApplicationRepository.save(jobApplication);

		jobApplicationStatusService.save(
				result,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.ON_GOING);

		notificationService.addNotification(
				translator.getUser().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getCandidate().getUser().getId(),
				CommonConstant.NotificationContent.HELPER_JOINED,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

		notificationService.addNotification(
				translator.getUser().getId(),
				null,
				jobApplication.getId(),
				jobApplication.getJob().getCompany().getUser().getId(),
				CommonConstant.NotificationContent.HELPER_JOINED,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);
	}

	public void cancelRequestTranslation(JobApplication jobApplication, UUID userCreateId, String reason) {
		jobApplicationStatusService.save(
				jobApplication,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.CANCELED_TRANSLATOR,
				reason,
				userCreateId);
		
		UUID translatorUserId = jobApplication.getTranslator().getUser().getId();
		UUID jobApplicationId = jobApplication.getId();
		
		notificationService.addNotification(
				translatorUserId,
				null,
				jobApplicationId,
				jobApplication.getCandidate().getUser().getId(),
				CommonConstant.NotificationContent.HELPER_CANCEL,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

		notificationService.addNotification(
				translatorUserId,
				null,
				jobApplicationId,
				jobApplication.getJob().getCompany().getUser().getId(),
				CommonConstant.NotificationContent.HELPER_CANCEL,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

		jobApplication.setUpdatedAt(CommonFunction.getCurrentDateTime());
		jobApplication.setTranslator(null);
		JobApplication result = jobApplicationRepository.save(jobApplication);

		jobApplicationStatusService.save(
				result,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.WAITING_FOR_TRANSLATOR_JOIN);
	}

	public JobApplicationResponse approveCandidate(JobApplication jobApplication) {
		JobApplicationStatus jobApplicationStatus = jobApplication.getJobApplicationStatus().stream().findFirst().get();
		if(!jobApplicationStatus.getStatus().equals(CommonConstant.StatusApplyJob.ON_GOING)) {
			throw new BadRequestException(MessageConstant.JOB_APPLIACTION_APPROVE_CANDIDATE_FAIL);
		}

		JobApplicationStatus status = jobApplicationStatusService.save(
				jobApplication,
				CommonFunction.getCurrentDateTime(),
				CommonConstant.StatusApplyJob.FINISHED);

		jobApplication.setUpdatedAt(CommonFunction.getCurrentDateTime());
		JobApplication result = jobApplicationRepository.save(jobApplication);

		UUID companyUserId = jobApplication.getJob().getCompany().getUser().getId();
		UUID jobApplicationId = jobApplication.getId();
		notificationService.addNotification(
				companyUserId,
				null,
				jobApplicationId,
				jobApplication.getCandidate().getUser().getId(),
				CommonConstant.NotificationContent.COMPANY_ACCEPTED_CANDIDATE,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);

		notificationService.addNotification(
				companyUserId,
				null,
				jobApplicationId,
				jobApplication.getTranslator().getUser().getId(),
				CommonConstant.NotificationContent.COMPANY_ACCEPTED_CANDIDATE,
				CommonConstant.NotificationType.STATUS_JOB_APPLICATION);
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
				pageInfo
        );
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
				pageInfo
        );
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
				pageInfo
        );
	}

	public JobApplicationResponse convertApplicationResponse(JobApplication jobApplication, JobApplicationStatus status) {
		JobApplicationResponse ob = new JobApplicationResponse();
		ob.setId(jobApplication.getId());
		ob.setJob(jobService.jobShortResponse(jobApplication.getJob()));
		ob.setCandidate(candidateService.candiateShortResponse(jobApplication.getCandidate()));
		if(jobApplication.getTranslator() != null) {
			ob.setTranslator(translatorService.translatorShortResponse(jobApplication.getTranslator()));
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
