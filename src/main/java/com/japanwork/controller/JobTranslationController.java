package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.service.JobTranslationService;
import com.japanwork.support.CommonSupport;

@Controller
public class JobTranslationController {
    @Autowired
    private JobTranslationService jobTranslationService;

    @Autowired
    private CommonSupport commonSupport;

    @PostMapping(UrlConstant.URL_JOB_TRANSLATION)
    @ResponseBody
    public ResponseDataAPI create(
            @PathVariable UUID id,
            @Valid @RequestBody JobTranslationRequest jobTranslationRequest
    ) throws BadRequestException {
        Job job           = commonSupport.loadJobById(id );
        Language language = commonSupport.loadLanguageById(jobTranslationRequest.getLanguageId());

        JobTranslation jobTranslation = jobTranslationService.save(
                job,
                language,
                jobTranslationRequest
        );

        ResponseDataAPI responseDataAPI = new ResponseDataAPI();
        responseDataAPI.savedSuccess();

        return responseDataAPI;
    }
}
