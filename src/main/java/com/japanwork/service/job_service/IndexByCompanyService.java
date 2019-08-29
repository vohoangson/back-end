package com.japanwork.service.job_service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.Job;
import com.japanwork.model.Language;
import com.japanwork.model.PageInfo;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.job_translation.JobTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IndexByCompanyService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobTranslationRepository jobTranslationRepository;

    public ResponseDataAPI perform(int page, int paging, UUID id, Language language) throws ResourceNotFoundException {
        try {
            Page<Job> pages   = jobRepository.findAllByCompanyIdAndDeletedAt(PageRequest.of(page-1, paging), id, null);
            PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
            List<JobResponse> list = new ArrayList<JobResponse>();

            JobResponse jobResponse = new JobResponse();
            if(pages.getContent().size() > 0) {
                for (Job job : pages.getContent()) {
                    list.add(jobResponse.jobFullSerializer(
                            job,
                            jobTranslationRepository.findByJobAndLanguageAndDeletedAt(job, language, null)
                    ));
                }
            }

            return new ResponseDataAPI(
                    CommonConstant.ResponseDataAPIStatus.SUCCESS,
                    list,
                    pageInfo
            );
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
        }
    }
}
