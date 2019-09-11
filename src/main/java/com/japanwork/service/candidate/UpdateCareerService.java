package com.japanwork.service.candidate;

import com.japanwork.common.CommonFunction;
import com.japanwork.model.*;
import com.japanwork.payload.request.AcademyRequest;
import com.japanwork.payload.request.CandidateExperienceRequest;
import com.japanwork.payload.request.ExperienceRequest;
import com.japanwork.payload.request.LanguageCertificateRequest;
import com.japanwork.service.AcademyService;
import com.japanwork.service.ExperienceService;
import com.japanwork.service.LanguageCertificateService;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UpdateCareerService {
    @Autowired
    private AcademyService academyService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private LanguageCertificateService languageCertificateService;

    @Autowired
    private CommonSupport commonSupport;

    @Transactional(rollbackFor=Exception.class, propagation= Propagation.REQUIRES_NEW)
    public Candidate perform(CandidateExperienceRequest candidateExperienceRequest, Candidate candidate) {
        this.deleteExperiencer(candidate.getId());

        if (!candidateExperienceRequest.getAcademies().isEmpty()) {
            List<Academy> listAcademy = new ArrayList<>();
            for (AcademyRequest academyRequest : candidateExperienceRequest.getAcademies()) {
                Academy academy = new Academy();

                academy.setCandidate(candidate);
                academy.setAcademyCenterName(academyRequest.getAcademyCenterName());
                academy.setMajorName(academyRequest.getMajorName());
                academy.setGrade(academyRequest.getGrade());
                academy.setGradeSystem(academyRequest.getGradeSystem());
                academy.setStartDate(academyRequest.getStartDate());
                academy.setEndDate(academyRequest.getEndDate());
                academy.setCreatedAt(CommonFunction.getCurrentDateTime());
                academy.setUpdatedAt(CommonFunction.getCurrentDateTime());
                academy.setDeletedAt(null);

                listAcademy.add(academy);
            }
            Set<Academy> result = new HashSet<Academy>(academyService.saveAll(listAcademy));
            candidate.setAcademies(result);
        }

        if (!candidateExperienceRequest.getExperiences().isEmpty()) {
            List<Experience> listExperience = new ArrayList<>();
            for (ExperienceRequest experienceRequest : candidateExperienceRequest.getExperiences()) {
                Level level = commonSupport.loadLevel(experienceRequest.getLevelId());
                Business business = commonSupport.loadBusiness(experienceRequest.getBusinessId());
                Experience experience = new Experience();

                experience.setCandidate(candidate);
                experience.setOrganizaion(experienceRequest.getOrganizaion());
                experience.setDesc(experienceRequest.getDesc());
                experience.setLevel(level);
                experience.setBusiness(business);
                experience.setStartDate(experienceRequest.getStartDate());
                experience.setEndDate(experienceRequest.getEndDate());
                experience.setCreatedAt(CommonFunction.getCurrentDateTime());
                experience.setUpdatedAt(CommonFunction.getCurrentDateTime());
                experience.setDeletedAt(null);

                listExperience.add(experience);
            }

            Set<Experience> result = new HashSet<Experience>(experienceService.saveAll(listExperience));
            candidate.setExperiences(result);
        }

        if (!candidateExperienceRequest.getLanguageCertificates().isEmpty()) {
            List<LanguageCertificate> listLanguageCertificate = new ArrayList<>();
            for (LanguageCertificateRequest languageCertificateRequest : candidateExperienceRequest.getLanguageCertificates()) {
                LanguageCertificateType languageCertificateType = commonSupport.loadLanguageCertificateType(languageCertificateRequest.getLanguageCertificateTypeId());
                LanguageCertificate languageCertificate = new LanguageCertificate();

                languageCertificate.setCandidate(candidate);
                languageCertificate.setScore(languageCertificateRequest.getScore());
                languageCertificate.setLanguageCertificateType(languageCertificateType);
                languageCertificate.setTakenDate(languageCertificateRequest.getTakenDate());
                languageCertificate.setCreatedAt(CommonFunction.getCurrentDateTime());
                languageCertificate.setUpdatedAt(CommonFunction.getCurrentDateTime());
                languageCertificate.setDeletedAt(null);

                listLanguageCertificate.add(languageCertificate);
            }

            Set<LanguageCertificate> result = new HashSet<LanguageCertificate>(languageCertificateService.saveAll(listLanguageCertificate));
            candidate.setLanguageCertificates(result);
        }

        return candidate;
    }

    private void deleteExperiencer(UUID id) {
        academyService.del(id);
        experienceService.del(id);
        languageCertificateService.del(id);
    }
}
