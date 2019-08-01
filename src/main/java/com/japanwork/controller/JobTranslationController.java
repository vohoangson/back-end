package com.japanwork.controller;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.JobTranslation;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.payload.response.BaseSuccessResponse;
import com.japanwork.service.JobTranslationService;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class JobTranslationController {
    @Autowired
    private JobTranslationService jobTranslationService;

    @Autowired
    private CommonSupport commonSupport;

    @PostMapping(UrlConstant.URL_JOB_TRANSLATION)
    @ResponseBody
    public BaseSuccessResponse create(@Valid @RequestBody JobTranslationRequest jobTranslationRequest)
            throws BadRequestException {

        commonSupport.loadJob(jobTranslationRequest.getJobId());
        commonSupport.loadTranslator(jobTranslationRequest.getTranslatorId());
        commonSupport.loadLanguage(jobTranslationRequest.getLanguageId());

        JobTranslation jobTranslation = jobTranslationService.save(jobTranslationRequest);
        return new BaseSuccessResponse(
                "success",
                null,
                null
        );
    }
}
