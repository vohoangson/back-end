package com.japanwork.support;

import java.util.UUID;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFound;
import com.japanwork.model.*;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.language.LanguageRepository;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.translator.TranslatorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void loadCompany(UUID id) throws ResourceNotFound {
        Company company = companyRepository.findByIdAndDeletedAt(id, null);
        if(company == null) {
            throw new ResourceNotFound(
                    MessageConstant.COMPANY_NOT_FOUND_CODE,
                    MessageConstant.COMPANY_NOT_FOUND
            );
        }
    }

    public void loadLanguage(UUID id) throws ResourceNotFound {
        Language language = languageRepository.findByIdAndDeletedAt(id, null);
        if(language == null) {
            throw new ResourceNotFound(
                    MessageConstant.LANGUAGE_NOT_FOUND_CODE,
                    MessageConstant.LANGUAGE_NOT_FOUND
            );
        }
    }

    public void loadJob(UUID id) throws ResourceNotFound {
        Job job = jobRepository.findByIdAndDeletedAt(id, null);
        if(job == null) {
            throw new ResourceNotFound(
                    MessageConstant.JOB_NOT_FOUND_CODE,
                    MessageConstant.JOB_NOT_FOUND
            );
        }
    }

    public void loadTranslator(UUID id) throws ResourceNotFound {
        Translator translator = translatorRepository.findByIdAndDeletedAt(id, null);
        if(translator == null) {
            throw new ResourceNotFound(
                    MessageConstant.TRANSLATOR_NOT_FOUND_CODE,
                    MessageConstant.TRANSLATOR_NOT_FOUND
            );
        }
    }

    public void loadCandidate(UUID id) throws ResourceNotFound {
        Candidate candidate = candidateRepository.findByIdAndDeletedAt(id, null);
        if(candidate == null) {
            throw new ResourceNotFound(
                    MessageConstant.CANDIDATE_NOT_FOUND_CODE,
                    MessageConstant.CANDIDATE_NOT_FOUND
            );
        }
    }

}
