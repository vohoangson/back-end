package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.HistoryStatus;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.CancelRequestTranslationRequest;
import com.japanwork.payload.request.RejectRequestTranslationRequest;
import com.japanwork.payload.request.RequestTranslationRequest;
import com.japanwork.payload.response.RequestTranslationResponse;
import com.japanwork.repository.history_status.RequestTranslationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class RequestTranslationService {
	
	@Autowired
	private RequestTranslationRepository requestTranslationRepository;
	
	@Autowired
	private HistoryStatusService historyStatusService;
	
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
	
	@Transactional
	public List<RequestTranslationResponse> createRequestTranslation(RequestTranslationRequest requestTranslationRequest, UserPrincipal userPrincipal) 
			throws ServerError, BadRequestException{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			User user = userService.getUser(userPrincipal);
			List<RequestTranslationResponse> list = new ArrayList<RequestTranslationResponse>();
			for (UUID languageId : requestTranslationRequest.getLanguageId()) {
				for (UUID objectTableId : requestTranslationRequest.getObjectTableId()) {
					RequestTranslation requestTranslation = new RequestTranslation();
					if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY)) {
						Company company = companyService.findByUserAndIsDelete(user, null);
						requestTranslation.setOwnerId(company.getId());
						if(!company.getId().equals(objectTableId)) {
							throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
						}
						requestTranslation.setObjectTableId(objectTableId);
					} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB)) {
						Company company = companyService.findByUserAndIsDelete(user, null);
						requestTranslation.setOwnerId(company.getId());
						Job job = jobService.findByIdAndIsDelete(objectTableId);
						if(job == null || !job.getCompany().getId().equals(company.getId())) {
							throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
						}
						requestTranslation.setObjectTableId(objectTableId);
					} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
						Candidate candidate = candidateService.myCandidate(userPrincipal);
						requestTranslation.setOwnerId(candidate.getId());
						if(!candidate.getId().equals(objectTableId)) {
							throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
						}
						requestTranslation.setObjectTableId(objectTableId);
					} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)) {
						Company company = companyService.findByUserAndIsDelete(user, null);
						requestTranslation.setOwnerId(company.getId());
						JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(objectTableId);
						if(jobApplication == null || jobApplication.getJob().getCompany().getId().equals(company.getId())) {
							throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST, MessageConstant.REQUEST_TRANSLATION_BAD_REQUEST_MSG);
						}
						requestTranslation.setObjectTableId(jobApplication.getId());
					}
					requestTranslation.setLanguage(new Language(languageId));
					requestTranslation.setDesc(requestTranslationRequest.getDesc());
					requestTranslation.setObjectTableType(requestTranslationRequest.getRequestType());
					requestTranslation.setCreatedAt(timestamp);
					RequestTranslation resultRequest = requestTranslationRepository.save(requestTranslation);
					HistoryStatus requestTranslationStatus = historyStatusService.save(
							resultRequest, 
							timestamp, 
							CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER,
							CommonConstant.HistoryStatusTypes.REQUEST,
							null,
							null);
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
	
	public RequestTranslationResponse translatorJoinRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException, ForbiddenException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL,MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL_MSG);
		}
		Translator translator = translatorService.myTranslator(userPrincipal);
		if(!this.checkTranslatorJoin(requestTranslation.getHistoryStatus(), translator)) {
			throw new ForbiddenException(MessageConstant.ERROR_403_TRANSLATION_MSG);
		}
		requestTranslation.setTranslator(translator);
		requestTranslationRepository.save(requestTranslation);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE,
				CommonConstant.HistoryStatusTypes.REQUEST,
				translator,
				null);
		
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse ownerAcceptApllyRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL_MSG);
		}
		
		if(requestTranslation.getObjectTableType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
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
		
		requestTranslationRepository.save(requestTranslation);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.ON_GOING,
				CommonConstant.HistoryStatusTypes.REQUEST,
				requestTranslation.getTranslator(),
				null);
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse translatorConfirmFinishedRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.ON_GOING)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_CONFIRM_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_CONFIRM_FINISH_FAIL_MSG);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.REVIEWED,
				CommonConstant.HistoryStatusTypes.REQUEST,
				requestTranslation.getTranslator(),
				null);
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse ownerAcceptFinishRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL_MSG);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.FINISHED,
				CommonConstant.HistoryStatusTypes.REQUEST,
				requestTranslation.getTranslator(),
				null);
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse ownerRefuseFinishRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_REFUSE_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_REFUSE_FINISH_FAIL_MSG);
		}
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.ON_GOING,
				CommonConstant.HistoryStatusTypes.REQUEST,
				requestTranslation.getTranslator(),
				null);
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse rejectRequestTranslation(UUID id, UserPrincipal userPrincipal, RejectRequestTranslationRequest reasonReject) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL_MSG);
		}
		Translator translator = requestTranslation.getTranslator();
		UUID userCreateId = this.userCreateId(userPrincipal, requestTranslation);
		requestTranslation.setTranslator(null);
		requestTranslationRepository.save(requestTranslation);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.REJECTED, 
				reasonReject.getReason(),
				CommonConstant.HistoryStatusTypes.REQUEST,
				userCreateId,
				translator,
				null);
		
		date = new Date();
		timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER,
				CommonConstant.HistoryStatusTypes.REQUEST,
				null,
				null);
		
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public RequestTranslationResponse cancelRequestTranslation(UUID id, UserPrincipal userPrincipal, CancelRequestTranslationRequest reasonCancel) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = requestTranslation.getHistoryStatus().stream().findFirst().get();
		if(requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED) 
				|| requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_FINISH_FAIL_MSG);
		}
		Translator translator = requestTranslation.getTranslator();
		UUID userCreateId = this.userCreateId(userPrincipal, requestTranslation);
		requestTranslation.setTranslator(null);
		requestTranslation.setConversation(null);
		requestTranslationRepository.save(requestTranslation);
		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.CANCELED, 
				reasonCancel.getReason(),
				CommonConstant.HistoryStatusTypes.REQUEST,
				userCreateId,
				translator,
				null);
		
		date = new Date();
		timestamp = new Timestamp(date.getTime());
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER,
				CommonConstant.HistoryStatusTypes.REQUEST,
				null,
				null);
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public Page<RequestTranslation> yourRequestTranslation(UserPrincipal userPrincipal, int page, int paging){
		User user = userService.getUser(userPrincipal);
		if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			Translator translator = translatorService.findTranslatorByUser(user);
			try {
				Page<RequestTranslation> pages = requestTranslationRepository.findAllByTranslatorAndDeletedAt(PageRequest.of(page-1, paging), translator, null);
				return pages;
			} catch (IllegalArgumentException e) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
			}
		} else if(user.getRole().equals(CommonConstant.Role.COMPANY)){
			Company company = companyService.myCompany(userPrincipal);
			try {
				Page<RequestTranslation> pages = requestTranslationRepository.findAllByOwnerIdAndDeletedAt(PageRequest.of(page-1, paging), company.getId(), null);
				return pages;
			} catch (IllegalArgumentException e) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
			}
		} else {
			Candidate candidate = candidateService.myCandidate(userPrincipal);
			try {
				Page<RequestTranslation> pages = requestTranslationRepository.findAllByOwnerIdAndDeletedAt(PageRequest.of(page-1, paging), candidate.getId(), null);
				return pages;
			} catch (IllegalArgumentException e) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
			}
		}
	}
	
	public Page<RequestTranslation> newRequestTranslation(UserPrincipal userPrincipal, int page, int paging){
		try {
			Page<RequestTranslation> pages = requestTranslationRepository.findAllByTranslatorAndDeletedAt(PageRequest.of(page-1, paging), null, null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public boolean checkRequestTranslation(RequestTranslationRequest requestTranslationRequest, UserPrincipal userPrincipal){
		UUID ownerId = null;
		User user = userService.getUser(userPrincipal);
		if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY)) {
			Company company = companyService.findByUserAndIsDelete(user, null);
			ownerId = company.getId();
		} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB)) {
			Company company = companyService.findByUserAndIsDelete(user, null);
			ownerId = company.getId();
		} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
			Candidate candidate = candidateService.myCandidate(userPrincipal);
			ownerId = candidate.getId();
		} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)) {
			Company company = companyService.findByUserAndIsDelete(user, null);
			ownerId = company.getId();
		}
		
		for (UUID languageId : requestTranslationRequest.getLanguageId()) {
			for (UUID objectTableId : requestTranslationRequest.getObjectTableId()) {
				RequestTranslation requestTranslation = requestTranslationRepository.findByOwnerIdAndObjectTableIdAndObjectTableTypeAndLanguageAndDeletedAt(
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
	
	public RequestTranslationResponse convertRequestTranslationResponse(RequestTranslation requestTranslation, HistoryStatus requestTranslationStatus) {
		RequestTranslationResponse requestTranslationResponse = new RequestTranslationResponse();
		requestTranslationResponse.setId(requestTranslation.getId());
		requestTranslationResponse.setOwnerId(requestTranslation.getOwnerId());
		requestTranslationResponse.setObjectTableId(requestTranslation.getObjectTableId());
		if(requestTranslation.getTranslator() != null) {
			requestTranslationResponse.setTranslatorId(requestTranslation.getTranslator().getId());
		}
		
		requestTranslationResponse.setStatus(historyStatusService.convertRequestTranslationStatusResponse(requestTranslationStatus));
		requestTranslationResponse.setRequestType(requestTranslation.getObjectTableType());
		
		if(requestTranslation.getConversation() != null) {
			requestTranslationResponse.setConverstaionId(requestTranslation.getConversation().getId());
		}
		
		requestTranslationResponse.setLanguageCode(requestTranslation.getLanguage().getCode());
		requestTranslationResponse.setCreatedAt(requestTranslation.getCreatedAt());
		return requestTranslationResponse;
	}
	
	public UUID userCreateId(UserPrincipal userPrincipal, RequestTranslation requestTranslation) {
		UUID userCreateId = null;
		if(userService.findByIdAndIsDelete(userPrincipal.getId()).getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			userCreateId = requestTranslation.getTranslator().getId();
		} else {
			userCreateId = requestTranslation.getOwnerId();
		}
		
		return userCreateId;
	}
	
	public boolean checkTranslatorJoin(Set<HistoryStatus> status, Translator translator) {
		for (HistoryStatus historyStatus : status) {
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
