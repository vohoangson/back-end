package com.japanwork.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.model.Language;
import com.japanwork.model.PageInfo;
import com.japanwork.model.RequestStatus;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelRequestTranslationRequest;
import com.japanwork.payload.request.RejectRequestTranslationRequest;
import com.japanwork.payload.request.RequestTranslationFilterRequest;
import com.japanwork.payload.request.RequestTranslationRequest;
import com.japanwork.payload.response.ProfileResponse;
import com.japanwork.payload.response.RequestTranslationResponse;
import com.japanwork.repository.request_status.RequestStatusRepository;
import com.japanwork.repository.request_translation.RequestTranslationRepository;
import com.japanwork.security.UserPrincipal;
import com.japanwork.support.CommonSupport;

@Service
public class RequestTranslationService {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RequestTranslationRepository requestTranslationRepository;

	@Autowired
	private RequestStatusRepository requestStatusRepository;

	@Autowired
	private RequestStatusService requestStatusService;

	@Autowired
	private JobApplicationService jobApplicationService;

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private LanguageService languageService;

	@Autowired
    private CommonSupport commonSupport;

	@Autowired
	private TranslatorService translatorService;

	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationCanidate(RequestTranslationRequest requestTranslationRequest,
			Candidate candidate) throws ServerError, BadRequestException{
		try {
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();
			ProfileResponse profileResponse = new ProfileResponse(
					candidate.getId(), 
					candidate.getFullName(), 
					candidate.getUser().getRole().replaceAll("ROLE_", ""), 
					candidate.getAvatar());
			
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					if(!candidate.getId().equals(objectableId)) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
					}
					RequestTranslation resultRequest = this.save(candidate.getFullName(), candidate.getId(), objectableId, languageId,
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							resultRequest,
							CommonFunction.getCurrentDateTime(),
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(profileResponse, resultRequest, requestTranslationStatus));
				}
			}

			return list;
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
		}
	}

	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationCompany(RequestTranslationRequest requestTranslationRequest,
			Company company) throws ServerError, BadRequestException{
		try {
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

			ProfileResponse profileResponse = new ProfileResponse(
					company.getId(), 
					company.getName(), 
					company.getUser().getRole().replaceAll("ROLE_", ""), 
					company.getLogoUrl());
			
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					if(!company.getId().equals(objectableId)) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
					}

					RequestTranslation requestTranslation = this.save(company.getName(), company.getId(), objectableId, languageId,
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							requestTranslation,
							CommonFunction.getCurrentDateTime(),
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(profileResponse, requestTranslation, requestTranslationStatus));
				}
			}

			return list;
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
		}
	}

	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationJob(RequestTranslationRequest requestTranslationRequest,
			Company company) throws ServerError, BadRequestException{
		try {
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

			ProfileResponse profileResponse = new ProfileResponse(
					company.getId(), 
					company.getName(), 
					company.getUser().getRole().replaceAll("ROLE_", ""), 
					company.getLogoUrl());

			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					Job job = commonSupport.loadJobById(objectableId);
					if(job == null || !job.getCompany().getId().equals(company.getId())) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
					}

					RequestTranslation requestTranslation = this.save(job.getName(), company.getId(), objectableId, languageId,
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							requestTranslation,
							CommonFunction.getCurrentDateTime(),
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);

					list.add(this.convertRequestTranslationResponse(profileResponse, requestTranslation, requestTranslationStatus));
				}
			}

			return list;
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
		}
	}

	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationJobApplication(RequestTranslationRequest requestTranslationRequest,
			Company company) throws ServerError, BadRequestException{
		try {
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

			ProfileResponse profileResponse = new ProfileResponse(
					company.getId(), 
					company.getName(), 
					company.getUser().getRole().replaceAll("ROLE_", ""), 
					company.getLogoUrl());
			
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					JobApplication jobApplication = commonSupport.loadJobApplicationById(objectableId, company.getUser().getId());
					if(jobApplication == null || !jobApplication.getJob().getCompany().getId().equals(company.getId())) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
					}

					RequestTranslation requestTranslation = this.save(jobApplication.getJob().getName(), company.getId(), objectableId, languageId,
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							requestTranslation,
							CommonFunction.getCurrentDateTime(),
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(profileResponse, requestTranslation, requestTranslationStatus));
				}
			}

			return list;
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_CREATE_FAIL);
		}
	}

	public RequestTranslation save(String name, UUID ownerId, UUID objectableId, UUID languageId,
			RequestTranslationRequest requestTranslationRequest) {
		RequestTranslation requestTranslation = new RequestTranslation();
		requestTranslation.setName(name);
		requestTranslation.setOwnerId(ownerId);
		requestTranslation.setObjectableId(objectableId);
		requestTranslation.setLanguage(new Language(languageId));
		requestTranslation.setDesc(requestTranslationRequest.getDesc());
		requestTranslation.setObjectableType(requestTranslationRequest.getRequestType());
		requestTranslation.setCreatedAt(CommonFunction.getCurrentDateTime());
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		return resultRequest;
	}

	@Transactional
	public RequestTranslationResponse translatorJoinRequestTranslation(RequestTranslation requestTranslation,
			Translator translator) throws BadRequestException, ForbiddenException{
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL);
		}
		if(!this.checkTranslatorJoin(requestTranslation.getRequestStatus(), translator)) {
			throw new ForbiddenException(MessageConstant.REQUEST_TRANSLATION_FORBIDDEN_ERROR);
		}

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestTranslation.setTranslator(translator);
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE);

		notificationService.addNotification(
				translator.getUser().getId(),
				null,
				requestTranslation.getId(),
				this.userIdOfOwner(requestTranslation),
				CommonConstant.NotificationContent.HELPER_JOINED,
				CommonConstant.NotificationType.STATUS_REQUEST);

		
		return convertRequestTranslationResponse(this.owner(requestTranslation), requestTranslation, result);
	}

	public RequestTranslationResponse ownerAcceptApllyRequestTranslation(RequestTranslation requestTranslation,
			User user) throws BadRequestException{
		this.checkPermission(requestTranslation, user);

		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL);
		}

		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateById(requestTranslation.getOwnerId());
			Conversation conversation = conversationService.create(requestTranslation.getTranslator(), null,
					candidate);
			requestTranslation.setConversation(conversation);
		} else {
			Company company = commonSupport.loadCompanyById(requestTranslation.getOwnerId());
			Conversation conversation = conversationService.create(requestTranslation.getTranslator(),
					company, null);
			requestTranslation.setConversation(conversation);
		}

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.ON_GOING);

		notificationService.addNotification(
				user.getId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getUser().getId(),
				CommonConstant.NotificationContent.OWNER_ACCEPTED_APPLY,
				CommonConstant.NotificationType.STATUS_REQUEST);
		
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)) {
			JobApplication jobApplication = commonSupport.loadJobApplicationById(requestTranslation.getObjectableId(), user.getId());
			jobApplicationService.translatorJoinJobApplication(jobApplication, requestTranslation.getTranslator());
		}
		return convertRequestTranslationResponse(this.owner(requestTranslation), requestTranslation, result);
	}

	public RequestTranslationResponse translatorConfirmFinishedRequestTranslation(RequestTranslation requestTranslation,
			User user)throws BadRequestException{
		this.checkPermission(requestTranslation, user);

		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.ON_GOING)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CONFIRM_FINISH_FAIL);
		}

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.REVIEWED);

		notificationService.addNotification(
				user.getId(),
				null,
				requestTranslation.getId(),
				this.userIdOfOwner(requestTranslation),
				CommonConstant.NotificationContent.HELPER_FINISHED,
				CommonConstant.NotificationType.STATUS_REQUEST);
		return convertRequestTranslationResponse(this.owner(requestTranslation), requestTranslation, result);
	}

	public RequestTranslationResponse ownerAcceptFinishRequestTranslation(RequestTranslation requestTranslation,
			User user) throws BadRequestException{
		this.checkPermission(requestTranslation, user);

		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL);
		}

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.FINISHED);

		notificationService.addNotification(
				user.getId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getUser().getId(),
				CommonConstant.NotificationContent.OWNER_ACCEPTED_FINISHED,
				CommonConstant.NotificationType.STATUS_REQUEST);
		return convertRequestTranslationResponse(this.owner(requestTranslation), requestTranslation, result);
	}

	public RequestTranslationResponse ownerRefuseFinishRequestTranslation(RequestTranslation requestTranslation,
			User user) throws BadRequestException{
		this.checkPermission(requestTranslation, user);

		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_REFUSE_FINISH_FAIL);
		}

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.ON_GOING);
		notificationService.addNotification(
				user.getId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getUser().getId(),
				CommonConstant.NotificationContent.OWNER_REFUSED_FINISHED,
				CommonConstant.NotificationType.STATUS_REQUEST);
		return convertRequestTranslationResponse(this.owner(requestTranslation), requestTranslation, result);
	}

	public RequestTranslationResponse rejectRequestTranslation(RequestTranslation requestTranslation,
			User user, RejectRequestTranslationRequest reasonReject) throws BadRequestException{
		this.checkPermission(requestTranslation, user);

		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_REJECT_FAIL);
		}

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestStatusService.save(
				requestTranslation,
				timestamp,
				CommonConstant.RequestTranslationStatus.REJECTED,
				reasonReject.getReason(),
				requestTranslation.getOwnerId());

		notificationService.addNotification(
				user.getId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getUser().getId(),
				CommonConstant.NotificationContent.OWNER_REJECT_APPLY,
				CommonConstant.NotificationType.STATUS_REQUEST);

		requestTranslation.setTranslator(null);
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);

		return convertRequestTranslationResponse(this.owner(requestTranslation), resultRequest, result);
	}

	public RequestTranslationResponse cancelRequestTranslation(RequestTranslation requestTranslation,
			User user, CancelRequestTranslationRequest reasonCancel) throws BadRequestException{
		this.checkPermission(requestTranslation, user);

		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)
				|| requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)
				|| requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.FINISHED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CANCEL_FAIL);
		}

		UUID userCreateId = this.userCreateId(user, requestTranslation);

		Timestamp timestamp = CommonFunction.getCurrentDateTime();
		requestStatusService.save(
				requestTranslation,
				timestamp,
				CommonConstant.RequestTranslationStatus.CANCELED,
				reasonCancel.getReason(),
				userCreateId);

		requestTranslation.setTranslator(null);
		requestTranslation.setConversation(null);
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);

		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)
				&& requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.ON_GOING)) {
			JobApplication jobApplication = commonSupport.loadJobApplicationById(requestTranslation.getObjectableId(), user.getId());
			jobApplicationService.cancelRequestTranslation(jobApplication, userCreateId, reasonCancel.getReason());
		}

		RequestStatus result = requestStatusService.save(
				resultRequest,
				timestamp,
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);

		return convertRequestTranslationResponse(this.owner(requestTranslation), resultRequest, result);
	}

	public void cancelRequestTranslation(UUID objectableId, UUID userCreateId, String content) {
		List<RequestTranslation> list = requestTranslationRepository.findAllByObjectableIdAndObjectableTypeAndDeletedAt(
				objectableId, CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION, null);
		if(list.size() > 0) {
			for (RequestTranslation requestTranslation : list) {
				RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
				requestStatusService.save(
						requestTranslation,
						CommonFunction.getCurrentDateTime(),
						CommonConstant.RequestTranslationStatus.CANCELED,
						content,
						userCreateId);
				if(requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
					notificationService.addNotification(
							this.userIdOfOwner(requestTranslation),
							null,
							requestTranslation.getId(),
							requestTranslation.getTranslator().getUser().getId(),
							content,
							CommonConstant.NotificationType.STATUS_REQUEST);
				}
			}
		}
	}

	public ResponseDataAPI requestTranslationsByCompany(User user,
			RequestTranslationFilterRequest filterRequest, int page, int paging) throws IllegalArgumentException{
		Date date = null;
		if(!filterRequest.getPostDate().isEmpty()) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(filterRequest.getPostDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("0000-00-00 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(filterRequest.getLanguageIds().size() == 0) {
			filterRequest.setLanguageIds(languageService.languageIds());
		}

		if(filterRequest.getRequestTypes().size() == 0) {
			Set<String> requestTypes = new HashSet<String>();
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION);
			filterRequest.setRequestTypes(requestTypes);
		}
		Page<RequestTranslation> pages = requestTranslationRepository.findAllByCompany(PageRequest.of(page-1, paging),
				filterRequest.getName(), filterRequest.getRequestTypes(), filterRequest.getLanguageIds(), date, user.getPropertyId());
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

		Company company = commonSupport.loadCompanyByUser(user.getId());
		ProfileResponse profileResponse = new ProfileResponse(
				company.getId(), 
				company.getName(), 
				company.getUser().getRole().replaceAll("ROLE_", ""), 
				company.getLogoUrl());
		
		if(pages.getContent().size() > 0) {
			for (RequestTranslation requestTranslation : pages.getContent()) {
				RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
				list.add(convertRequestTranslationResponse(profileResponse, requestTranslation, status));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	public ResponseDataAPI requestTranslationsByCandidate(User user, RequestTranslationFilterRequest filterRequest,
			int page, int paging) throws IllegalArgumentException{
		Date date = null;
		if(!filterRequest.getPostDate().isEmpty()) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(filterRequest.getPostDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("0000-00-00 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(filterRequest.getLanguageIds().size() == 0) {
			filterRequest.setLanguageIds(languageService.languageIds());
		}
		Page<RequestTranslation> pages = requestTranslationRepository.findAllByCandidate(PageRequest.of(page-1, paging),
				filterRequest.getName(), filterRequest.getLanguageIds(), date, user.getPropertyId());
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

		Candidate candidate = commonSupport.loadCandidateByUser(user.getId());
		ProfileResponse profileResponse = new ProfileResponse(
				candidate.getId(), 
				candidate.getFullName(), 
				candidate.getUser().getRole().replaceAll("ROLE_", ""), 
				candidate.getAvatar());
		if(pages.getContent().size() > 0) {
			for (RequestTranslation requestTranslation : pages.getContent()) {
				RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
				list.add(convertRequestTranslationResponse(profileResponse, requestTranslation, status));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	public ResponseDataAPI requestTranslationsByTranslator(User user, RequestTranslationFilterRequest filterRequest,
			int page, int paging) throws IllegalArgumentException{
		Date date = null;
		if(!filterRequest.getPostDate().isEmpty()) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(filterRequest.getPostDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("0000-00-00 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(filterRequest.getLanguageIds().size() == 0) {
			filterRequest.setLanguageIds(languageService.languageIds());
		}

		if(filterRequest.getRequestTypes().size() == 0) {
			Set<String> requestTypes = new HashSet<String>();
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE);
			filterRequest.setRequestTypes(requestTypes);
		}
		Page<RequestStatus> pages = requestStatusRepository.findAllByTranslator(PageRequest.of(page-1, paging),
				filterRequest.getName(), filterRequest.getRequestTypes(), filterRequest.getLanguageIds(), date, user.getPropertyId());
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

		if(pages.getContent().size() > 0) {
			for (RequestStatus requestStatus : pages.getContent()) {
				list.add(convertRequestTranslationResponse(this.owner(requestStatus.getRequestTranslation()), requestStatus.getRequestTranslation(), requestStatus));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	public ResponseDataAPI newRequestTranslations(User user, RequestTranslationFilterRequest filterRequest, int page, int paging)
			throws IllegalArgumentException{
		Date date = null;
		if(!filterRequest.getPostDate().isEmpty()) {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(filterRequest.getPostDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("0000-00-00 00:00:00");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if(filterRequest.getLanguageIds().size() == 0) {
			filterRequest.setLanguageIds(languageService.languageIds());
		}

		if(filterRequest.getRequestTypes().size() == 0) {
			Set<String> requestTypes = new HashSet<String>();
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION);
			requestTypes.add(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE);
			filterRequest.setRequestTypes(requestTypes);
		}
		Page<RequestTranslation> pages = requestTranslationRepository.findNewRequestByTranslator(PageRequest.of(page-1, paging),
				filterRequest.getName(), filterRequest.getRequestTypes(), filterRequest.getLanguageIds(), date, user.getPropertyId());
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();

		if(pages.getContent().size() > 0) {
			for (RequestTranslation requestTranslation : pages.getContent()) {
				RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
				list.add(convertRequestTranslationResponse(this.owner(requestTranslation), requestTranslation, status));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	public void checkPermission(RequestTranslation requestTranslation, User user)
			throws ForbiddenException{
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE) && !user.getPropertyId().equals(requestTranslation.getOwnerId())) {
			if(!requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)
					|| !requestTranslation.getOwnerId().equals(user.getPropertyId())) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
		} else if(user.getRole().equals(CommonConstant.Role.COMPANY) && !user.getPropertyId().equals(requestTranslation.getOwnerId())) {
			if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)
					|| !requestTranslation.getOwnerId().equals(user.getPropertyId())) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
		} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
			if(!status.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)
					&& !requestTranslation.getTranslator().getUser().equals(user)) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
		}
	}

	public RequestTranslation requestTranslationByJobApplication(UUID id, UserPrincipal userPrincipal)
			throws ForbiddenException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByObjectableIdAndObjectableTypeAndDeletedAt(
				id, CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION, null);
		return requestTranslation;
	}

	public boolean checkRequestTranslation(RequestTranslationRequest requestTranslationRequest, UUID ownerId){
		for (UUID languageId : requestTranslationRequest.getLanguageId()) {
			for (UUID objectTableId : requestTranslationRequest.getObjectableId()) {
				RequestTranslation requestTranslation = requestTranslationRepository
														.findByOwnerIdAndObjectableIdAndObjectableTypeAndLanguageAndDeletedAt(
															ownerId,
															objectTableId,
															requestTranslationRequest.getRequestType(),
															new Language(languageId),
															null
														);
				if(requestTranslation != null) {
					return true;
				}
			}
		}
		return false;
	}

	public RequestTranslationResponse convertRequestTranslationResponse(ProfileResponse owner, RequestTranslation requestTranslation,
			RequestStatus requestTranslationStatus) {
		RequestTranslationResponse requestTranslationResponse = new RequestTranslationResponse();
		requestTranslationResponse.setId(requestTranslation.getId());
		requestTranslationResponse.setName(requestTranslation.getName());
		requestTranslationResponse.setOwner(owner);
		requestTranslationResponse.setObjectableId(requestTranslation.getObjectableId());
		if(requestTranslation.getTranslator() != null) {
			requestTranslationResponse.setTranslatorResponse(translatorService.translatorShortResponse(requestTranslation.getTranslator()));
		}

		requestTranslationResponse.setStatus(requestStatusService.convertRequestTranslationStatusResponse(requestTranslationStatus));
		requestTranslationResponse.setRequestType(requestTranslation.getObjectableType());

		if(requestTranslation.getConversation() != null) {
			requestTranslationResponse.setConversationId(requestTranslation.getConversation().getId());
		}

		requestTranslationResponse.setLanguageId(requestTranslation.getLanguage().getId());
		requestTranslationResponse.setCreatedAt(requestTranslation.getCreatedAt());
		requestTranslationResponse.setDesc(requestTranslation.getDesc());
		return requestTranslationResponse;
	}

	public UUID userCreateId(User user, RequestTranslation requestTranslation) {
		UUID userCreateId = null;
		if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			userCreateId = requestTranslation.getTranslator().getId();
			notificationService.addNotification(
					user.getId(),
					null,
					requestTranslation.getId(),
					this.userIdOfOwner(requestTranslation),
					CommonConstant.NotificationContent.HELPER_CANCEL,
					CommonConstant.NotificationType.STATUS_REQUEST);
		} else {
			userCreateId = requestTranslation.getOwnerId();
			notificationService.addNotification(
					user.getId(),
					null,
					requestTranslation.getId(),
					requestTranslation.getTranslator().getUser().getId(),
					CommonConstant.NotificationContent.OWNER_CANCEL,
					CommonConstant.NotificationType.STATUS_REQUEST);
		}

		return userCreateId;
	}

	public UUID userIdOfOwner(RequestTranslation requestTranslation) {
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateById(requestTranslation.getOwnerId());
			return candidate.getUser().getId();
		} else {
			Company company = commonSupport.loadCompanyById(requestTranslation.getOwnerId());
			return company.getUser().getId();
		}
	}

	public ProfileResponse owner(RequestTranslation requestTranslation) {
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateById(requestTranslation.getOwnerId());
			ProfileResponse profileResponse = new ProfileResponse(
					candidate.getId(), 
					candidate.getFullName(), 
					candidate.getUser().getRole().replaceAll("ROLE_", ""), 
					candidate.getAvatar());
			return profileResponse;
		} else {
			Company company = commonSupport.loadCompanyById(requestTranslation.getOwnerId());
			ProfileResponse profileResponse = new ProfileResponse(
					company.getId(), 
					company.getName(), 
					company.getUser().getRole().replaceAll("ROLE_", ""), 
					company.getLogoUrl());
			return profileResponse;
		}
	}
	
	public UUID receiverId(RequestTranslation requestTranslation) {
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = commonSupport.loadCandidateById(requestTranslation.getOwnerId());
			return candidate.getId();
		} else {
			Company company = commonSupport.loadCompanyById(requestTranslation.getOwnerId());
			return company.getId();
		}
	}

	public boolean checkTranslatorJoin(Set<RequestStatus> status, Translator translator) {
		for (RequestStatus historyStatus : status) {
			if(historyStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.CANCELED) ||
					historyStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REJECTED)) {
				if(historyStatus.getTranslator().equals(translator)) {
					return false;
				}
			}
		}
		return true;
	}
}
