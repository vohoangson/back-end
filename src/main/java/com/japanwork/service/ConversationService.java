package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.model.Conversation;
import com.japanwork.model.JobApplication;
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
	
	@Transactional
	public Conversation createConversationAll(UUID id) {		
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		
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
			
			return result;
		}
		
		return jobApplication.getAllConversation();
	}
	
	public Conversation createConversationSupportCandidate(UUID id) {		
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		
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
			
			return result;
		}
		
		return jobApplication.getCandidateSupportConversaion();
	}
	
	public Conversation createConversationSupportCompany(UUID id) {		
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		
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
			
			return result;
		}
		return jobApplication.getCompanySupportConversation();
	}
	
	public List<Conversation> listConversationByUser(UserPrincipal userPrincipal, UUID id) {
		UUID idUser = userPrincipal.getId();
		String role = userService.findById(idUser).getRole();
		List<Conversation> list = new ArrayList<Conversation>();
		
		JobApplication jobApplication = jobApplicationService.findByIdAndIsDelete(id);
		
		if(jobApplication.getAllConversation() != null) {
			list.add(jobApplication.getAllConversation());
		}
			
		if(role.equals("ROLE_TRANSLATOR") || role.equals("ROLE_COMPANY")) {
			
			if(jobApplication.getCompanySupportConversation() != null) {
				jobApplication.getCompanySupportConversation().setCandidate(null);
				list.add(jobApplication.getCompanySupportConversation());
			}
		}
		
		if(role.equals("ROLE_TRANSLATOR") || role.equals("ROLE_CANDIDATE")) {
			jobApplication.getCandidateSupportConversaion().getCompany();
			if(jobApplication.getCandidateSupportConversaion() != null) {
				list.add(jobApplication.getCandidateSupportConversaion());
			}
		}
		
		List<ConversationResponse> listConversationResponse = new ArrayList<ConversationResponse>();
		if(list != null) {
			for (Conversation conversation : list) {
				listConversationResponse.add(convertConversationResponse(conversation));
			}
		}
		return list;
	}
	
	public Conversation findByIdAndIsDelete(UUID id, boolean isDel) {
		return conversationRepository.findByIdAndIsDelete(id, isDel);
	}
	
	public ConversationResponse convertConversationResponse(Conversation conversation) {
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
