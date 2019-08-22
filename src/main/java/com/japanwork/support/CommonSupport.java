package com.japanwork.support;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.model.Language;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.conversation.ConversationRepository;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.job_application.JobApplicationRepository;
import com.japanwork.repository.language.LanguageRepository;
import com.japanwork.repository.request_translation.RequestTranslationRepository;
import com.japanwork.repository.translator.TranslatorRepository;
import com.japanwork.repository.user.UserRepository;

@Component
public class CommonSupport {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private TranslatorRepository translatorRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ConversationRepository conversationRepository;
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    @Autowired
    private RequestTranslationRepository requestTranslationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Company loadCompanyById(UUID id) throws ResourceNotFoundException {
        Company company = companyRepository.findByIdAndDeletedAt(id, null);
        if(company == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.COMPANY_NOT_FOUND
            );
        }
        return company;
    }

    public Company loadCompanyByUser(UUID userId) throws ResourceNotFoundException {
        Company company = companyRepository.findByUserIdAndDeletedAt(userId, null);
        if(company == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.COMPANY_NOT_FOUND
            );
        }
        return company;
    }
    
    public Language loadLanguageById(UUID id) throws ResourceNotFoundException {
        Language language = languageRepository.findByIdAndDeletedAt(id, null);
        if(language == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.LANGUAGE_NOT_FOUND
            );
        }
        return language;
    }

    public Job loadJobById(UUID id) throws ResourceNotFoundException {
        Job job = jobRepository.findByIdAndDeletedAt(id, null);
        if(job == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.JOB_NOT_FOUND
            );
        }
        return job;
    }

    public Translator loadTranslatorById(UUID id) throws ResourceNotFoundException {
        Translator translator = translatorRepository.findByIdAndDeletedAt(id, null);
        if(translator == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.TRANSLATOR_NOT_FOUND
            );
        }
        return translator;
    }

    public Translator loadTranslatorByUser(UUID userId) throws ResourceNotFoundException {
        Translator translator = translatorRepository.findByUserIdAndDeletedAt(userId, null);
        if(translator == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.TRANSLATOR_NOT_FOUND
            );
        }
        return translator;
    }
    
    public Candidate loadCandidateById(UUID id) throws ResourceNotFoundException {
        Candidate candidate = candidateRepository.findByIdAndDeletedAt(id, null);
        if(candidate == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.CANDIDATE_NOT_FOUND
            );
        }
        return candidate;
    }
    
    public Candidate loadCandidateByUser(UUID userId) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByUserIdAndDeletedAt(userId, null);
		if(candidate == null) {
			throw new ResourceNotFoundException(
                    MessageConstant.CANDIDATE_NOT_FOUND
            );
		}
			
		return candidate;
	}
    
    public Conversation loadConversationById(UUID id) throws ResourceNotFoundException {
    	Conversation conversation = conversationRepository.findByIdAndDeletedAt(id, null);
        if(conversation == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.CONVERSATION_NOT_FOUND
            );
        }
        return conversation;
    }
    
    public JobApplication loadJobApplicationById(UUID id, UUID userId) 
    		throws ResourceNotFoundException, ForbiddenException {
    	JobApplication jobApplication = jobApplicationRepository.findByIdAndDeletedAt(id, null);
        if(jobApplication == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.JOB_APPLICATION_NOT_FOUND
            );
        }
        User user = userRepository.findByIdAndDeletedAt(userId, null);
        if(user.getRole().equals(CommonConstant.Role.CANDIDATE)) {
			if(!user.getPropertyId().equals(jobApplication.getCandidate().getId())) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
		} else if(user.getRole().equals(CommonConstant.Role.COMPANY)) {
			if(!user.getPropertyId().equals(jobApplication.getJob().getCompany().getId())) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
		} else if(user.getRole().equals(CommonConstant.Role.TRANSLATOR)) {
			if(jobApplication.getTranslator() == null || !user.getPropertyId().equals(jobApplication.getTranslator().getId())) {
				throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
			}
		}
        return jobApplication;
    }
    
    public RequestTranslation loadRequestTransationById(UUID id) throws ResourceNotFoundException {
    	RequestTranslation requestTranslation = requestTranslationRepository.findByIdAndDeletedAt(id, null);
        if(requestTranslation == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.REQUEST_TRANSLATION_NOT_FOUND
            );
        }
        return requestTranslation;
    }
    
    public User loadUserById(UUID id) throws ResourceNotFoundException {
    	User user = userRepository.findByIdAndDeletedAt(id, null);
        if(user == null) {
            throw new ResourceNotFoundException(
                    MessageConstant.USER_NOT_FOUND
            );
        }
        return user;
    }
}
