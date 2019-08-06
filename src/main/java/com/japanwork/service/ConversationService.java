package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Translator;
import com.japanwork.payload.response.CandidateResponse;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.ConversationResponse;
import com.japanwork.repository.conversation.ConversationRepository;

@Service
public class ConversationService {

	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CandidateService candidateService;
	
	@Transactional
	public Conversation createConversationAll(Translator translator, Company company, Candidate candidate) {		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Conversation conversation = new Conversation();
		conversation.setTranslator(translator);
		conversation.setCandidate(candidate);
		conversation.setCompany(company);
		conversation.setCreatedAt(timestamp);
		conversation.setDeletedAt(null);
		
		Conversation result = conversationRepository.save(conversation);			
		return result;
	}
	
	public Conversation createConversationSupportCandidate(Translator translator, Candidate candidate) {		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Conversation conversation = new Conversation();
		conversation.setTranslator(translator);
		conversation.setCandidate(candidate);
		conversation.setCreatedAt(timestamp);
		conversation.setDeletedAt(null);
		
		Conversation result = conversationRepository.save(conversation);	
		return result;
	}
	
	public Conversation createConversationSupportCompany(Translator translator, Company company) {		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Conversation conversation = new Conversation();
		
		conversation.setTranslator(translator);
		conversation.setCompany(company);
		conversation.setCreatedAt(timestamp);
		conversation.setDeletedAt(null);
		
		Conversation result = conversationRepository.save(conversation);			
		return result;
	}
	
	public Conversation findByIdAndIsDelete(UUID id, Timestamp deletedAt) {
		return conversationRepository.findByIdAndDeletedAt(id, deletedAt);
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
