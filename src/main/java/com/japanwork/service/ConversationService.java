package com.japanwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.common.CommonFunction;
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
	public Conversation create(Translator translator, Company company, Candidate candidate) {
		Conversation conversation = new Conversation();
		conversation.setTranslator(translator);
		conversation.setCandidate(candidate);
		conversation.setCompany(company);
		conversation.setCreatedAt(CommonFunction.getCurrentDateTime());

		Conversation result = conversationRepository.save(conversation);
		return result;
	}

	public ConversationResponse convertConversationResponse(Conversation conversation) {
		CompanyResponse companyResponse = new CompanyResponse();
		if(conversation.getCompany() != null) {
			companyResponse = new CompanyResponse().companyFullSerializer(conversation.getCompany());
		}

		CandidateResponse candidateResponse = new CandidateResponse();

		if(conversation.getCandidate() != null) {
			candidateResponse = candidateService.candiateShortResponse(conversation.getCandidate());
		}

		ConversationResponse conversationResponse = new ConversationResponse(
				conversation.getId(),
				companyResponse,
				candidateResponse,
				translatorService.translatorShortResponse(conversation.getTranslator()));
		return conversationResponse;
	}
}
