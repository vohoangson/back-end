package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
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
	
	@Transactional
	public List<RequestTranslationResponse> createRequestTranslation(RequestTranslationRequest requestTranslationRequest, UserPrincipal userPrincipal) 
			throws ServerError{
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
						requestTranslation.setObjectTableId(objectTableId);
					} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB)) {
						Company company = companyService.findByUserAndIsDelete(user, null);
						requestTranslation.setOwnerId(company.getId());
						Job job = jobService.findByIdAndIsDelete(objectTableId);
						requestTranslation.setObjectTableId(job.getId());
					} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
						Candidate candidate = candidateService.myCandidate(userPrincipal);
						requestTranslation.setOwnerId(candidate.getId());
						requestTranslation.setObjectTableId(objectTableId);
					} else if(requestTranslationRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)) {
						Company company = companyService.findByUserAndIsDelete(user, null);
						requestTranslation.setOwnerId(company.getId());
						
						JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(objectTableId);
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
		
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REQUEST_TRANSLATION_FAIL);
		}
	}
	
	public RequestTranslationResponse translatorJoinRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.CANCELED) 
				&& !requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL,MessageConstant.REQUEST_TRANSLATION_TRANSLATOR_JOIN_FAIL_MSG);
		}
		Translator translator = translatorService.myTranslator(userPrincipal);
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
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
		if(!requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_OWNER_AGREE)) {
			throw new BadRequestException(MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL, 
											MessageConstant.REQUEST_TRANSLATION_ACCEPT_APPLY_FAIL_MSG);
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
	
	public RequestTranslationResponse translatorConfirmFinishedRequestTranslation(UUID id, UserPrincipal userPrincipal) 
			throws BadRequestException, ResourceNotFoundException{
		RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
		if(requestTranslation == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
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
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
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
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
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
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
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
		
		HistoryStatus requestTranslationStatus = historyStatusService.statusRequestTranslation(requestTranslation);
		if(requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.REVIEWED) 
				|| requestTranslationStatus.getStatus().equals(CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER)) {
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
				CommonConstant.RequestTranslationStatus.CANCELED, 
				reasonCancel.getReason(),
				CommonConstant.HistoryStatusTypes.REQUEST,
				userCreateId,
				translator,
				null);
		HistoryStatus result = historyStatusService.save(
				requestTranslation, 
				timestamp, 
				CommonConstant.RequestTranslationStatus.WAITING_FOR_HELPER,
				CommonConstant.HistoryStatusTypes.REQUEST,
				null,
				null);
		return convertRequestTranslationResponse(requestTranslation, result);
	}
	
	public List<RequestTranslationResponse> yourRequestTranslation(UserPrincipal userPrincipal){
		
		return null;
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
		
		if(requestTranslation.getConverstaion() != null) {
			requestTranslationResponse.setConverstaionId(requestTranslation.getConverstaion().getId());
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
}
