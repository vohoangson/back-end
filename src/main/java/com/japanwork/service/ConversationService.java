package com.japanwork.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.japanwork.constant.CommonConstant;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.JobApplication;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
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
	
	public Set<Conversation> listConversationByUser(UserPrincipal userPrincipal) {
		User user = userService.getUser(userPrincipal);
    	if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
    		Candidate candidate = candidateService.myCandidate(userPrincipal);
    		return conversationRepository.findByCandidateAndDeletedAt(candidate, null);
    	} else if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
    		Company company = companyService.myCompany(userPrincipal);
    		return conversationRepository.findByCompanyAndDeletedAt(company, null);
    	} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
    		Translator translator = translatorService.myTranslator(userPrincipal);
    		return conversationRepository.findByTranslatorAndDeletedAt(translator, null);
    	}		
		return null;
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
