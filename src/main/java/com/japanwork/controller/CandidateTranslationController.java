package com.japanwork.controller;

import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.Candidate;
import com.japanwork.model.CandidateTranslation;
import com.japanwork.model.Language;
import com.japanwork.payload.request.CandidateTranslationRequest;
import com.japanwork.payload.response.BaseSuccessResponse;
import com.japanwork.service.CandidateTranslationService;
import com.japanwork.support.CommonSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.UUID;

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
        Candidate candidate = commonSupport.loadCandidate(id);
        Language language   = commonSupport.loadLanguage(candidateTranslationRequest.getLanguageId());

        CandidateTranslation candidateTranslation = candidateTranslationService.save(
                candidate,
                language,
                candidateTranslationRequest
        );

        ResponseDataAPI responseDataAPI = new ResponseDataAPI();
        responseDataAPI.savedSuccess();

        return responseDataAPI;
    }
}
