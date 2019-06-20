package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.Conversation;
import com.japanwork.model.JobApplication;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.CandidateResponse;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.ConversationResponse;
import com.japanwork.repository.conversation.ConversationRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class ConversationService {

	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private JobApplicationService jobApplicationService;
	
	public BaseDataResponse createConversationAll(UUID id) {		
		JobApplication jobApplication = jobApplicationService.findByJobIdAndIsDelete(id);
		
		if(jobApplication.getAllConversation() == null) {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Conversation conversation = new Conversation();
			conversation.setTranslator(jobApplication.getTranslator());
			conversation.setCandidate(jobApplication.getCandidate());
			conversation.setCompany(jobApplication.getJob().getCompany());
			conversation.setCreateAt(timestamp);
			conversation.setDelete(false);
			
			Conversation result = conversationRepository.save(conversation);
			
			jobApplication.setAllConversation(result);
			jobApplicationService.save(jobApplication);
			
			return new BaseDataResponse(convertTranslatorResponse(result));
		}
		
		return new BaseDataResponse(convertTranslatorResponse(jobApplication.getAllConversation()));
	}
	
	public BaseDataResponse createConversationSupportCandidate(UUID id) {		
		JobApplication jobApplication = jobApplicationService.findByJobIdAndIsDelete(id);
		
		if(jobApplication.getCandidateSupportConversaion() == null) {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Conversation conversation = new Conversation();
			conversation.setTranslator(jobApplication.getTranslator());
			conversation.setCandidate(jobApplication.getCandidate());
			conversation.setCreateAt(timestamp);
			conversation.setDelete(false);
			
			Conversation result = conversationRepository.save(conversation);
			
			jobApplication.setCandidateSupportConversaion(conversation);
			jobApplicationService.save(jobApplication);
			
			return new BaseDataResponse(convertTranslatorResponse(result));
		}
		
		return new BaseDataResponse(convertTranslatorResponse(jobApplication.getCandidateSupportConversaion()));
	}
	
	public BaseDataResponse createConversationSupportCompany(UUID id) {		
		JobApplication jobApplication = jobApplicationService.findByJobIdAndIsDelete(id);
		
		if(jobApplication.getCompanySupportConversation() == null) {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			Conversation conversation = new Conversation();
			
			conversation.setTranslator(jobApplication.getTranslator());
			conversation.setCompany(jobApplication.getJob().getCompany());
			conversation.setCreateAt(timestamp);
			conversation.setDelete(false);
			
			Conversation result = conversationRepository.save(conversation);
			
			jobApplication.setCompanySupportConversation(conversation);
			jobApplicationService.save(jobApplication);
			
			return new BaseDataResponse(convertTranslatorResponse(result));
		}
		return new BaseDataResponse(convertTranslatorResponse(jobApplication.getCompanySupportConversation()));
	}
	
	public BaseDataResponse listConversationByUser(UserPrincipal userPrincipal, int page, int paging) {
		UUID idUser = userPrincipal.getId();
		String role = userService.findById(idUser).getRole();
		List<Conversation> list = new ArrayList<Conversation>();
		if(role.equals("TRANSLATOR")) {
			list = conversationRepository.findByTranslatorIdAndIsDelete(idUser, false);
		}
		
		if(role.equals("COMPANY")) {
			list = conversationRepository.findByCompanyIdAndIsDelete(idUser, false);
		}
		
		if(role.equals("CANDIDATE")) {
			list = conversationRepository.findByCandidateIdAndIsDelete(idUser, false);
		}
		
		List<ConversationResponse> listConversationResponse = new ArrayList<ConversationResponse>();
		if(list != null) {
			for (Conversation conversation : list) {
				listConversationResponse.add(convertTranslatorResponse(conversation));
			}
		}
		return new BaseDataResponse(listConversationResponse);
	}
	
	public ConversationResponse convertTranslatorResponse(Conversation conversation) {
		CompanyResponse companyResponse = new CompanyResponse();
		if(conversation.getCompany() != null) {
			companyResponse = companyService.convertCompanyResponse(conversation.getCompany());
		}
		
		CandidateResponse candidateResponse = new CandidateResponse();
		
		if(conversation.getCandidate() != null) {
			candidateResponse = candidateService.convertCandiateResponse(conversation.getCandidate());
		}
		
		ConversationResponse conversationResponse = new ConversationResponse(
				conversation.getId(),
				companyResponse, 
				candidateResponse,
				translatorService.convertTranslatorResponse(conversation.getTranslator()));
		return conversationResponse;
	}
}
