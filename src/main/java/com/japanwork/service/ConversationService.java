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
		
		return new BaseDataResponse(result);
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
		
		return new BaseDataResponse(result);
	}
}