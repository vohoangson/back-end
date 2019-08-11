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
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
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
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.RequestTranslationResponse;
import com.japanwork.repository.request_status.RequestStatusRepository;
import com.japanwork.repository.request_translation.RequestTranslationRepository;
import com.japanwork.security.UserPrincipal;

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
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private JobApplicationService jobApplicationService;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private LanguageService languageService;
	
	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationCanidate(RequestTranslationRequest requestTranslationRequest, 
			UserPrincipal userPrincipal) throws ServerError, BadRequestException{
		try {
			Candidate candidate = candidateService.myCandidate(userPrincipal);
			
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					if(!candidate.getId().equals(objectableId)) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, 
								MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
					}
					RequestTranslation resultRequest = this.save(candidate.getId(), objectableId, languageId, 
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							resultRequest, 
							CommonFunction.dateTimeNow(), 
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(resultRequest, requestTranslationStatus));
				}				
			}			
					
			return list;
		} catch (BadRequestException e) {
			throw e;	
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_FAIL);
		}
	}
	
	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationCompany(RequestTranslationRequest requestTranslationRequest, 
			UserPrincipal userPrincipal) throws ServerError, BadRequestException{
		try {
			Company company = companyService.myCompany(userPrincipal);
			
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					if(!company.getId().equals(objectableId)) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, 
								MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
					}
					
					RequestTranslation requestTranslation = this.save(company.getId(), objectableId, languageId, 
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							requestTranslation, 
							CommonFunction.dateTimeNow(), 
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(requestTranslation, requestTranslationStatus));
				}				
			}			
					
			return list;
		} catch (BadRequestException e) {
			throw e;	
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_FAIL);
		}
	}
	
	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationJob(RequestTranslationRequest requestTranslationRequest, 
			UserPrincipal userPrincipal) throws ServerError, BadRequestException{
		try {
			Company company = companyService.myCompany(userPrincipal);
			
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					Job job = jobService.findByIdAndIsDelete(objectableId);
					if(job == null || !job.getCompany().getId().equals(company.getId())) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, 
								MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
					}
					
					RequestTranslation requestTranslation = this.save(company.getId(), objectableId, languageId, 
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							requestTranslation, 
							CommonFunction.dateTimeNow(), 
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(requestTranslation, requestTranslationStatus));
				}				
			}			
					
			return list;
		} catch (BadRequestException e) {
			throw e;	
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_FAIL);
		}
	}
	
	@Transactional
	public List<RequestTranslationResponse> createRequestTranslationJobApplication(RequestTranslationRequest requestTranslationRequest, 
			UserPrincipal userPrincipal) throws ServerError, BadRequestException{
		try {
			Company company = companyService.myCompany(userPrincipal);
			
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectableId : requestTranslationRequest.getObjectableId()) {
					JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(objectableId, userPrincipal);
					if(jobApplication == null || !jobApplication.getJob().getCompany().getId().equals(company.getId())) {
						throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, 
								MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
					}
					
					RequestTranslation requestTranslation = this.save(company.getId(), objectableId, languageId, 
							requestTranslationRequest);
					RequestStatus requestTranslationStatus = requestStatusService.save(
							requestTranslation, 
							CommonFunction.dateTimeNow(), 
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
					list.add(this.convertRequestTranslationResponse(requestTranslation, requestTranslationStatus));
				}				
			}			
					
			return list;
		} catch (BadRequestException e) {
			throw e;	
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_FAIL);
		}
	}
	
	public RequestTranslation save(UUID ownerId, UUID objectableId, UUID languageId, 
			RequestTranslationRequest requestTranslationRequest) {
		RequestTranslation requestTranslation = new RequestTranslation();
		requestTranslation.setOwnerId(ownerId);
		requestTranslation.setObjectableId(objectableId);
		requestTranslation.setLanguage(new Language(languageId));
		requestTranslation.setDesc(requestTranslationRequest.getDesc());
		requestTranslation.setObjectableType(requestTranslationRequest.getRequestType());
		requestTranslation.setCreatedAt(CommonFunction.dateTimeNow());
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		return resultRequest;
	}
	
	@Transactional
	public RequestTranslationResponse translatorJoinRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException, ForbiddenException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL_MSG);
		}
		Translator translator = translatorService.myTranslator(userPrincipal);
		if(!this.checkTranslatorJoin(requestTranslation.getRequestStatus(), translator)) {
			throw new ForbiddenException(MessageConstant.ERROR_403_TRANSLATION_MSG);
		}
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
		requestTranslation.setTranslator(translator);
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE);
		
		notificationService.addNotification(
				translator.getId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getOwnerId(), 
				CommonConstant.NotificationContent.HELPER_JOINED,
				CommonConstant.NotificationType.STATUS_REQUEST,
				this.userIdOfOwner(requestTranslation));
		
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse ownerAcceptApllyRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException{
		RequestTranslation requestTranslation = this.requestTranslation(id, userPrincipal);
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL_MSG);
		}

		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {			
			Candidate candidate = candidateService.findByIdAndIsDelete(requestTranslation.getOwnerId());
			Conversation conversation = conversationService.createConversationSupportCandidate(requestTranslation.getTranslator(), 
					candidate);
			requestTranslation.setConversation(conversation);
		} else {
			Company company = companyService.findByIdAndIsDelete(requestTranslation.getOwnerId());			
			Conversation conversation = conversationService.createConversationSupportCompany(requestTranslation.getTranslator(), 
					company);
			requestTranslation.setConversation(conversation);
		}
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.ON_GOING);
		
		notificationService.addNotification(
				requestTranslation.getOwnerId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getId(), 
				CommonConstant.NotificationContent.OWNER_ACCEPTED_APPLY,
				CommonConstant.NotificationType.STATUS_REQUEST,
				requestTranslation.getTranslator().getUser().getId());
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)) {
			jobApplicationService.translatorJoinJobApplication(requestTranslation.getObjectableId(), requestTranslation.getTranslator());
		}
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse translatorConfirmFinishedRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException{
		RequestTranslation requestTranslation = this.requestTranslation(id, userPrincipal);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.ON_GOING)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CONFIRM_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_CONFIRM_FINISH_FAIL_MSG);
		}
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.REVIEWED);
		
		notificationService.addNotification(
				requestTranslation.getTranslator().getId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getOwnerId(), 
				CommonConstant.NotificationContent.HELPER_FINISHED,
				CommonConstant.NotificationType.STATUS_REQUEST,
				this.userIdOfOwner(requestTranslation));
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse ownerAcceptFinishRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = this.requestTranslation(id, userPrincipal);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL_MSG);
		}
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.FINISHED);
		
		notificationService.addNotification(
				requestTranslation.getOwnerId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getId(), 
				CommonConstant.NotificationContent.OWNER_ACCEPTED_FINISHED,
				CommonConstant.NotificationType.STATUS_REQUEST,
				requestTranslation.getTranslator().getUser().getId());
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse ownerRefuseFinishRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = this.requestTranslation(id, userPrincipal);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_REFUSE_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_REFUSE_FINISH_FAIL_MSG);
		}
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
		
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.ON_GOING);
		notificationService.addNotification(
				requestTranslation.getOwnerId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getId(), 
				CommonConstant.NotificationContent.OWNER_REFUSED_FINISHED,
				CommonConstant.NotificationType.STATUS_REQUEST,
				requestTranslation.getTranslator().getUser().getId());
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse rejectRequestTranslation(UUID id, UserPrincipal userPrincipal, 
			RejectRequestTranslationRequest reasonReject) throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = this.requestTranslation(id, userPrincipal);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_REJECT_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_REJECT_FAIL_MSG);
		}
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
		requestStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.REJECTED, 
				reasonReject.getReason(),
				requestTranslation.getOwnerId());
		
		notificationService.addNotification(
				requestTranslation.getOwnerId(),
				null,
				requestTranslation.getId(),
				requestTranslation.getTranslator().getId(), 
				CommonConstant.NotificationContent.OWNER_REJECT_APPLY,
				CommonConstant.NotificationType.STATUS_REQUEST,
				requestTranslation.getTranslator().getUser().getId());
		
		requestTranslation.setTranslator(null);
		requestTranslation.setUpdatedAt(timestamp);
		RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
				
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
		
		return convertRequestTranslationResponse(resultRequest, result);
	}
	
	public RequestTranslationResponse cancelRequestTranslation(UUID id, UserPrincipal userPrincipal, 
			CancelRequestTranslationRequest reasonCancel) throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = this.requestTranslation(id, userPrincipal);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
		if(requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED) 
				|| requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)
				|| requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.FINISHED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CANCEL_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_CANCEL_FAIL_MSG);
		}
		
		UUID userCreateId = this.userCreateId(userPrincipal, requestTranslation);		
		
		Timestamp timestamp = CommonFunction.dateTimeNow();
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
			jobApplicationService.cancelRequestTranslation(requestTranslation.getObjectableId(),userCreateId,reasonCancel.getReason());
		}
		
		RequestStatus result = requestStatusService.save(
				resultRequest, 
				CommonFunction.dateTimeNow(), 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER);
		
		return convertRequestTranslationResponse(resultRequest, result);
	}
	
	public void cancelRequestTranslation(UUID objectableId, UUID userCreateId, String content) {
		List<RequestTranslation> list = requestTranslationRepository.findAllByObjectableIdAndObjectableTypeAndDeletedAt(
				objectableId, CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION, null);
		if(list.size() > 0) {
			for (RequestTranslation requestTranslation : list) {
				RequestStatus requestTranslationStatus = requestTranslation.getRequestStatus().stream().findFirst().get();
				
				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());
				
				requestStatusService.save(
						requestTranslation, 
						timestamp, 
						CommonConstant.RequestTranslationStatus.CANCELED, 
						content,
						userCreateId);
				if(requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
					notificationService.addNotification(
							userCreateId,
							null,
							requestTranslation.getId(),
							requestTranslation.getTranslator().getId(), 
							content,
							CommonConstant.NotificationType.STATUS_REQUEST,
							requestTranslation.getTranslator().getUser().getId());
				}
			}
		}
	}
	
	public BaseDataMetaResponse requestTranslationsByCompany(UserPrincipal userPrincipal, 
			RequestTranslationFilterRequest filterRequest, int page, int paging) throws IllegalArgumentException{
		
		User user = userService.getUser(userPrincipal);
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
		
		if(pages.getContent().size() > 0) {
			for (RequestTranslation requestTranslation : pages.getContent()) {
				RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
				list.add(convertRequestTranslationResponse(requestTranslation, status));
			}
		}
		
		BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
		return response;
	}
	
	public BaseDataMetaResponse requestTranslationsByCandidate(UserPrincipal userPrincipal, 
			RequestTranslationFilterRequest filterRequest, int page, int paging) throws IllegalArgumentException{
		
		User user = userService.getUser(userPrincipal);
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
		
		if(pages.getContent().size() > 0) {
			for (RequestTranslation requestTranslation : pages.getContent()) {
				RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
				list.add(convertRequestTranslationResponse(requestTranslation, status));
			}
		}
		
		BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
		return response;
	}
	
	public BaseDataMetaResponse requestTranslationsByTranslator(UserPrincipal userPrincipal, 
			RequestTranslationFilterRequest filterRequest, int page, int paging) throws IllegalArgumentException{
		
		User user = userService.getUser(userPrincipal);
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
				list.add(convertRequestTranslationResponse(requestStatus.getRequestTranslation(), requestStatus));
			}
		}
		
		BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
		return response;
	}
	
	public BaseDataMetaResponse newRequestTranslations(UserPrincipal userPrincipal, 
			RequestTranslationFilterRequest filterRequest, int page, int paging) throws IllegalArgumentException{
		
		User user = userService.getUser(userPrincipal);
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
				list.add(convertRequestTranslationResponse(requestTranslation, status));
			}
		}
		
		BaseDataMetaResponse response = new BaseDataMetaResponse(list, pageInfo);
		return response;
	}
	
	public RequestTranslation requestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ForbiddenException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		User user = userService.getUser(userPrincipal);
		if(user.getRole().equals(CommonConstant.Role.CANDIDATE) && !user.getPropertyId().equals(requestTranslation.getOwnerId())) {
			if(!requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)
					|| !requestTranslation.getOwnerId().equals(user.getPropertyId())) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
		} else if(user.getRole().equals(CommonConstant.Role.COMPANY) && !user.getPropertyId().equals(requestTranslation.getOwnerId())) {
			if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)
					|| !requestTranslation.getOwnerId().equals(user.getPropertyId())) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
		} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			RequestStatus status = requestTranslation.getRequestStatus().stream().findFirst().get();
			if(!status.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER) 
					&& !requestTranslation.getTranslator().getUser().equals(user)) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
		}
		return requestTranslation;
	}
	
	public RequestTranslation requestTranslationByJobApplication(UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException, ForbiddenException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByObjectableIdAndObjectableTypeAndDeletedAt(
				id, CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION, null);
		return requestTranslation;
	}
	
	public boolean checkRequestTranslationByCandidate(RequestTranslationRequest requestTranslationRequest, UserPrincipal userPrincipal){
		Candidate candidate = candidateService.myCandidate(userPrincipal);
		if(candidate == null) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, 
					MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
		}
		UUID ownerId = candidate.getId();
		return this.checkRequestTranslation(requestTranslationRequest, ownerId);
	}
	
	public boolean checkRequestTranslationByCompany(RequestTranslationRequest requestTranslationRequest, UserPrincipal userPrincipal){
		Company company = companyService.myCompany(userPrincipal);
		if(company == null) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, 
					MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
		}
		
		UUID ownerId = company.getId();		
		return this.checkRequestTranslation(requestTranslationRequest, ownerId);
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
	
	public RequestTranslationResponse convertRequestTranslationResponse(RequestTranslation requestTranslation, 
			RequestStatus requestTranslationStatus) {
		RequestTranslationResponse requestTranslationResponse = new RequestTranslationResponse();
		requestTranslationResponse.setId(requestTranslation.getId());
		requestTranslationResponse.setOwnerId(requestTranslation.getOwnerId());
		requestTranslationResponse.setObjectableId(requestTranslation.getObjectableId());
		if(requestTranslation.getTranslator() != null) {
			requestTranslationResponse.setTranslatorId(requestTranslation.getTranslator().getId());
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
	
	public UUID userCreateId(UserPrincipal userPrincipal, RequestTranslation requestTranslation) {
		UUID userCreateId = null;
		if(userService.findByIdAndIsDelete(userPrincipal.getId()).getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			userCreateId = requestTranslation.getTranslator().getId();
			notificationService.addNotification(
					userCreateId,
					null,
					requestTranslation.getId(),
					requestTranslation.getOwnerId(), 
					CommonConstant.NotificationContent.HELPER_CANCEL,
					CommonConstant.NotificationType.STATUS_REQUEST,
					this.userIdOfOwner(requestTranslation));
		} else {
			userCreateId = requestTranslation.getOwnerId();
			notificationService.addNotification(
					userCreateId,
					null,
					requestTranslation.getId(),
					requestTranslation.getTranslator().getId(), 
					CommonConstant.NotificationContent.OWNER_CANCEL,
					CommonConstant.NotificationType.STATUS_REQUEST,
					requestTranslation.getTranslator().getUser().getId());
		}
		
		return userCreateId;
	}
	
	public UUID userIdOfOwner(RequestTranslation requestTranslation) {
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = candidateService.findByIdAndIsDelete(requestTranslation.getOwnerId());
			return candidate.getUser().getId();
		} else {
			Company company = companyService.findByIdAndIsDelete(requestTranslation.getOwnerId());
			return company.getUser().getId();
		}
	}
	
	public UUID receiverId(RequestTranslation requestTranslation) {
		if(requestTranslation.getObjectableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = candidateService.findByIdAndIsDelete(requestTranslation.getOwnerId());
			return candidate.getId();
		} else {
			Company company = companyService.findByIdAndIsDelete(requestTranslation.getOwnerId());
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
