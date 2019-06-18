package com.japanwork.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.payload.request.ConversationRequest;
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
	
	public BaseDataResponse save(ConversationRequest conversationRequest, UserPrincipal userPrincipal) {
		Conversation conversation = new Conversation();
		conversation.setTranslator(translatorService.findTranslatorByUser(userService.findById(userPrincipal.getId())));
		if(conversationRequest.getCandidateId() != null) {
			conversation.setCandidate(new Candidate(conversationRequest.getCandidateId()));
		}
		if(conversationRequest.getCompanyId() != null) {
			conversation.setCompany(new Company(conversationRequest.getCompanyId()));
		}
		
		Conversation result = conversationRepository.save(conversation);
		
		return new BaseDataResponse(convertTranslatorResponse(result));
	}
	
	public BaseDataResponse update(ConversationRequest conversationRequest, UUID id) throws BadRequestException{
		Conversation conversation = conversationRepository.findByIdAndIsDelete(id, false);
		
		if(conversationRequest.getCandidateId() != null) {
			if(conversation.getCandidate() != null) {
				throw new BadRequestException("1231","123");
			}
			conversation.setCandidate(new Candidate(conversationRequest.getCandidateId()));
		}
		if(conversationRequest.getCompanyId() != null) {
			
			if(conversation.getCompany() != null) {
				throw new BadRequestException("123","123");
			}
			conversation.setCompany(new Company(conversationRequest.getCompanyId()));
		}
		
		Conversation result = conversationRepository.save(conversation);
		
		return new BaseDataResponse(convertTranslatorResponse(result));
	}
	
	public ConversationResponse convertTranslatorResponse(Conversation conversation) {
		CompanyResponse companyResponse = null;
		if(conversation.getCompany() != null) {
			companyResponse = companyService.convertCompanyResponse(conversation.getCompany());
		}
		
		CandidateResponse candidateResponse = null;
		
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
