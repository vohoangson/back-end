package com.japanwork.controller;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.JobTranslationRequest;
import com.japanwork.payload.response.BaseSuccessResponse;
import com.japanwork.service.JobTranslationService;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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
        Job job           = commonSupport.loadJob(id );
        Language language = commonSupport.loadLanguage(jobTranslationRequest.getLanguageId());

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
