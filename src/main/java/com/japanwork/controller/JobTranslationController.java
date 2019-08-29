package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.*;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.job_translation_service.CreateJobTranslationService;
import com.japanwork.service.job_translation_service.UpdateJobTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.support.CommonSupport;

@Controller
public class JobTranslationController {
    @Autowired
    private CreateJobTranslationService createJobTranslationService;

    @Autowired
    private UpdateJobTranslationService updateJobTranslationService;

    @Autowired
    private CommonSupport commonSupport;

    @PostMapping(UrlConstant.URL_JOB_TRANSLATIONS)
    @ResponseBody
    public ResponseDataAPI create(
            @PathVariable UUID id,
            @Valid @RequestBody JobTranslationRequest jobTranslationRequest,
            @CurrentUser UserPrincipal userPrincipal) throws BadRequestException {

        Job job           = commonSupport.loadJobById(id );
        Language language = commonSupport.loadLanguage(jobTranslationRequest.getLanguage());

        if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        JobTranslation jobTranslation = createJobTranslationService.perform(
                job,
                language,
                jobTranslationRequest
        );

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );
    }

    @PatchMapping(UrlConstant.URL_JOB_TRANSLATION)
    @ResponseBody
    public ResponseDataAPI update(
            @Valid @RequestBody JobTranslationRequest jobTranslationRequest,
            @PathVariable UUID id,
            @PathVariable(name = "language") String languageCode,
            @CurrentUser UserPrincipal userPrincipal) {

        Job job = commonSupport.loadJobById(id);

        if(!(job.getCompany()).getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        Language language = commonSupport.loadLanguage(languageCode);
        JobTranslation jobTranslation = commonSupport.loadJobTranslation(job, language);

        JobTranslation result = updateJobTranslationService.perform(jobTranslation, jobTranslationRequest);

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );
    }
}
