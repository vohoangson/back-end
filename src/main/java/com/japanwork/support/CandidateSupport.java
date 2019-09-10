package com.japanwork.support;

import com.japanwork.model.Academy;
import com.japanwork.model.Experience;
import com.japanwork.model.LanguageCertificate;
import com.japanwork.payload.response.AcademyResponse;
import com.japanwork.payload.response.ExperienceResponse;
import com.japanwork.payload.response.LanguageCertificateResponse;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CandidateSupport {
    public Set<AcademyResponse> getAcademyResponses(Set<Academy> academies){
        Set<AcademyResponse> list = new HashSet<AcademyResponse>();
        if (academies != null) {
            for (Academy academy : academies) {
                AcademyResponse academyResponse = new AcademyResponse();
                academyResponse.setId(academy.getId());
                academyResponse.setAcademyCenterName(academy.getAcademyCenterName());
                academyResponse.setMajorName(academy.getMajorName());
                academyResponse.setGrade(academy.getGrade());
                academyResponse.setGradeSystem(academy.getGradeSystem());
                academyResponse.setStartDate(academy.getStartDate());
                academyResponse.setEndDate(academy.getEndDate());

                list.add(academyResponse);
            }
        }
        return list;
    }

    public Set<ExperienceResponse> getExperienceResponses(Set<Experience> experiences){
        Set<ExperienceResponse> list = new HashSet<ExperienceResponse>();

        if (experiences != null) {
            for (Experience experience : experiences) {
                ExperienceResponse experienceResponse = new ExperienceResponse();
                experienceResponse.setId(experience.getId());
                experienceResponse.setOrganizaion(experience.getOrganizaion());
                experienceResponse.setDesc(experience.getDesc());
                experienceResponse.setLevelId(experience.getLevel().getId());
                experienceResponse.setBusinessId(experience.getBusiness().getId());
                experienceResponse.setStartDate(experience.getStartDate());
                experienceResponse.setEndDate(experience.getEndDate());

                list.add(experienceResponse);
            }
        }
        return list;
    }

    public Set<LanguageCertificateResponse> getLanguageCertificateResponses(Set<LanguageCertificate> languageCertificates){
        Set<LanguageCertificateResponse> list = new HashSet<LanguageCertificateResponse>();
        if (languageCertificates != null) {
            for (LanguageCertificate languageCertificate : languageCertificates) {
                LanguageCertificateResponse languageCertificateResponse = new LanguageCertificateResponse();

                languageCertificateResponse.setId(languageCertificate.getId());
                languageCertificateResponse.setScore(languageCertificate.getScore());
                languageCertificateResponse.setLanguageCertificateTypeId(languageCertificate.getLanguageCertificateType().getId());
                languageCertificateResponse.setTakenDate(languageCertificate.getTakenDate());

                list.add(languageCertificateResponse);
            }
        }
        return list;
    }
}
