package com.japanwork.support;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Academy;
import com.japanwork.model.Business;
import com.japanwork.model.Candidate;
import com.japanwork.model.City;
import com.japanwork.model.Company;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Contract;
import com.japanwork.model.Conversation;
import com.japanwork.model.District;
import com.japanwork.model.Experience;
import com.japanwork.model.Job;
import com.japanwork.model.JobApplication;
import com.japanwork.model.Language;
import com.japanwork.model.Level;
import com.japanwork.model.RequestTranslation;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.repository.academy.AcademyRepository;
import com.japanwork.repository.business_type.BusinessTypeRepository;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.repository.city.CityRepository;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.company_tranlation.CompanyTranslationRepository;
import com.japanwork.repository.contract.ContractRepository;
import com.japanwork.repository.conversation.ConversationRepository;
import com.japanwork.repository.district.DistrictRepository;
import com.japanwork.repository.experience.ExperienceRepository;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.job_application.JobApplicationRepository;
import com.japanwork.repository.language.LanguageRepository;
import com.japanwork.repository.level.LevelRepository;
import com.japanwork.repository.request_translation.RequestTranslationRepository;
import com.japanwork.repository.translator.TranslatorRepository;
import com.japanwork.repository.user.UserRepository;

@Component
public class CommonSupport {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyTranslationRepository companyTranslationRepository;

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

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BusinessTypeRepository businessTypeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public Company loadCompanyById(UUID id) throws ResourceNotFoundException {
        Company company = companyRepository.findByIdAndDeletedAt(id, null);
        if(company == null) {
            throw new ResourceNotFoundException(MessageConstant.COMPANY_NOT_FOUND);
        }
        return company;
    }

    public CompanyTranslation loadCompanyTranslation(
            Company company,
            Language language
    ) throws ResourceNotFoundException {
        CompanyTranslation companyTranslation = companyTranslationRepository.findByCompanyAndLanguageAndDeletedAt(
                company,
                language,
                null
        );
        if(companyTranslation == null) {
            throw new ResourceNotFoundException(MessageConstant.COMPANY_TRANSLATION_NOT_FOUND);
        }
        return companyTranslation;
    }

    public Company loadCompanyByUser(UUID userId) throws ResourceNotFoundException {
        Company company = companyRepository.findByUserIdAndDeletedAt(userId, null);
        if(company == null) {
            throw new ResourceNotFoundException(MessageConstant.COMPANY_NOT_FOUND);
        }
        return company;
    }

    public Language loadLanguageById(UUID id) throws ResourceNotFoundException {
        Language language = languageRepository.findByIdAndDeletedAt(id, null);
        if(language == null) {
            throw new ResourceNotFoundException(MessageConstant.LANGUAGE_NOT_FOUND);
        }
        return language;
    }

    public Language loadLanguage(String code) throws ResourceNotFoundException {
        Language language = languageRepository.findByCodeAndDeletedAt(code, null);
        if(language == null) {
            throw new ResourceNotFoundException(MessageConstant.LANGUAGE_NOT_FOUND);
        }
        return language;
    }

    public Job loadJobById(UUID id) throws ResourceNotFoundException {
        Job job = jobRepository.findByIdAndDeletedAt(id, null);
        if(job == null) {
            throw new ResourceNotFoundException(MessageConstant.JOB_NOT_FOUND);
        }
        return job;
    }

    public Translator loadTranslatorById(UUID id) throws ResourceNotFoundException {
        Translator translator = translatorRepository.findByIdAndDeletedAt(id, null);
        if(translator == null) {
            throw new ResourceNotFoundException(MessageConstant.TRANSLATOR_NOT_FOUND);
        }
        return translator;
    }

    public Translator loadTranslatorByUser(UUID userId) throws ResourceNotFoundException {
        Translator translator = translatorRepository.findByUserIdAndDeletedAt(userId, null);
        if(translator == null) {
            throw new ResourceNotFoundException(MessageConstant.TRANSLATOR_NOT_FOUND);
        }
        return translator;
    }

    public Candidate loadCandidateById(UUID id) throws ResourceNotFoundException {
        Candidate candidate = candidateRepository.findByIdAndDeletedAt(id, null);
        if(candidate == null) {
            throw new ResourceNotFoundException(MessageConstant.CANDIDATE_NOT_FOUND);
        }
        return candidate;
    }

    public Candidate loadCandidateByUser(UUID userId) throws ResourceNotFoundException{
		Candidate candidate = candidateRepository.findByUserIdAndDeletedAt(userId, null);
		if(candidate == null) {
			throw new ResourceNotFoundException(MessageConstant.CANDIDATE_NOT_FOUND);
		}

		return candidate;
	}

    public Conversation loadConversationById(UUID id) throws ResourceNotFoundException {
    	Conversation conversation = conversationRepository.findByIdAndDeletedAt(id, null);
        if(conversation == null) {
            throw new ResourceNotFoundException(MessageConstant.CONVERSATION_NOT_FOUND);
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
            throw new ResourceNotFoundException(MessageConstant.REQUEST_TRANSLATION_NOT_FOUND);
        }
        return requestTranslation;
    }

    public User loadUserById(UUID id) throws ResourceNotFoundException {
    	User user = userRepository.findByIdAndDeletedAt(id, null);
        if(user == null) {
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        return user;
    }

    public Academy loadAcademy(UUID id) throws ResourceNotFoundException {
        Academy academy = academyRepository.findByIdAndDeletedAt(id, null);

        if(academy== null) {
            throw new ResourceNotFoundException(MessageConstant.ACADEMY_NOT_FOUND);
        }
        return academy;
    }

    public Experience loadExperience(UUID id) throws ResourceNotFoundException {
        Experience experience = experienceRepository.findByIdAndDeletedAt(id, null);

        if(experience== null) {
            throw new ResourceNotFoundException(MessageConstant.EXPERIENCE_NOT_FOUND);
        }
        return experience;
    }

    public Level loadLevel(UUID id) throws ResourceNotFoundException {
        Level level = levelRepository.findByIdAndDeletedAt(id, null);

        if(level == null) {
            throw new ResourceNotFoundException(MessageConstant.LEVEL_NOT_FOUND);
        }
        return level;
    }

    public Contract loadContract(UUID id) throws ResourceNotFoundException {
        Contract contract = contractRepository.findByIdAndDeletedAt(id, null);

        if(contract == null) {
            throw new ResourceNotFoundException(MessageConstant.CONTRACT_NOT_FOUND);
        }
        return contract;
    }

    public Business loadBusiness(UUID id) throws ResourceNotFoundException {
        Business business = businessTypeRepository.findByIdAndDeletedAt(id, null);

        if(business == null) {
            throw new ResourceNotFoundException(MessageConstant.BUSINESS_NOT_FOUND);
        }
        return business;
    }

    public City loadCity(UUID id) throws ResourceNotFoundException {
        City city = cityRepository.findByIdAndDeletedAt(id, null);

        if(city == null) {
            throw new ResourceNotFoundException(MessageConstant.CITY_NOT_FOUND);
        }
        return city;
    }

    public District loadDistrict(UUID id) throws ResourceNotFoundException {
        District district = districtRepository.findByIdAndDeletedAt(id, null);

        if(district == null) {
            throw new ResourceNotFoundException(MessageConstant.CITY_NOT_FOUND);
        }
        return district;
    }
}
