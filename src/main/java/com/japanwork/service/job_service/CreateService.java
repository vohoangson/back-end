package com.japanwork.service.job_service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.model.*;
import com.japanwork.payload.request.JobRequest;
import com.japanwork.repository.job.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateService {
    @Autowired
    private JobRepository jobRepository;

    public Job perform(
            JobRequest jobRequest,
            Company company,
            Business business,
            Contract contract,
            Level level,
            City city,
            District district
    ) {
        Job job = new Job();

        job.setName(jobRequest.getName());
        job.setCompany(company);
        job.setBusinesses(business);
        job.setContract(contract);
        job.setLevel(level);
        job.setCity(city);
        job.setDistrict(district);
        job.setAddress(jobRequest.getAddress());
        job.setDesc(jobRequest.getDesc());
        job.setRequiredEducation(jobRequest.getRequiredEducation());
        job.setRequiredExperience(jobRequest.getRequiredExperience());
        job.setRequiredLanguage(jobRequest.getRequiredLanguage());
        job.setBenefits(jobRequest.getBenefits());
        job.setJapaneseLevelRequirement(jobRequest.getJapaneseLevel());
        job.setApplicationDeadline(jobRequest.getApplicationDeadline());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        job.setStatus(CommonConstant.StatusTranslate.UNTRANSLATED);
        job.setCreatedAt(CommonFunction.getCurrentDateTime());
        job.setUpdatedAt(CommonFunction.getCurrentDateTime());

        Job result = jobRepository.save(job);
        return result;
    }
}
