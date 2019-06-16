package com.japanwork.service;

import org.springframework.stereotype.Service;

import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Translator;
import com.japanwork.payload.request.ConversationRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.UserPrincipal;

@Service
public class ConversationService {

	public BaseDataResponse save(ConversationRequest conversationRequest, UserPrincipal userPrincipal) {
		Conversation conversation = new Conversation();
		conversation.setTranslator(new Translator(userPrincipal.getId()));
		if(conversationRequest.getCandidateId() != null) {
			conversation.setCandidate(new Candidate(conversationRequest.getCandidateId()));
		}
		if(conversationRequest.getCompanyId() != null) {
			conversation.setCompany(new Company(conversationRequest.getCompanyId()));
		}
		
		return new BaseDataResponse("");
	}
}
