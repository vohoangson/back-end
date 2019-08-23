package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.japanwork.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Candidate;
import com.japanwork.model.Language;
import com.japanwork.payload.request.CandidateTranslationRequest;
import com.japanwork.service.CandidateTranslationService;
import com.japanwork.support.CommonSupport;

@Controller
public class CandidateTranslationController {
    @Autowired
    private CandidateTranslationService candidateTranslationService;

    @Autowired
    private CommonSupport commonSupport;

    @PostMapping(UrlConstant.URL_CANDIDATE_TRANSLATION)
    @ResponseBody
    public ResponseDataAPI create(
            @PathVariable UUID id,
            @Valid @RequestBody CandidateTranslationRequest candidateTranslationRequest
    ) throws BadRequestException {
        Candidate candidate = commonSupport.loadCandidateById(id);
        Language language   = commonSupport.loadLanguageById(candidateTranslationRequest.getLanguageId());

        candidateTranslationService.save(
                candidate,
                language,
                candidateTranslationRequest
        );

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );
    }
}
