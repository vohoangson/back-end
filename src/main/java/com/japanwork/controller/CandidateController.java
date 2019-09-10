package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.japanwork.model.*;
import com.japanwork.service.candidate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.Business;
import com.japanwork.model.Candidate;
import com.japanwork.model.City;
import com.japanwork.model.Contract;
import com.japanwork.model.Country;
import com.japanwork.model.District;
import com.japanwork.model.Level;
import com.japanwork.model.User;
import com.japanwork.payload.request.CandidateExpectedRequest;
import com.japanwork.payload.request.CandidateExperienceRequest;
import com.japanwork.payload.request.CandidatePersonalRequest;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.candidate.CreateCandidateService;
import com.japanwork.service.candidate.UpdateCandidateService;
import com.japanwork.service.candidate.UpdateCareerService;
import com.japanwork.service.candidate.UpdateExpectedService;
import com.japanwork.support.CommonSupport;

@Controller
public class CandidateController {
    @Autowired
    private CandidateBaseService candidateBaseService;

    @Autowired
    private IndexCandidateService indexCandidateService;

    @Autowired
	private CreateCandidateService createCandidateService;

    @Autowired
    private UpdateCandidateService updateCandidateService;

    @Autowired
    private UpdateExpectedService updateExpectedService;

    @Autowired
    private UpdateCareerService updateCareerService;

    @Autowired
    private CommonSupport commonSupport;

    @GetMapping(UrlConstant.URL_CANDIDATES)
    @ResponseBody
    public ResponseDataAPI index(@RequestParam(name = "language") String languageCode,
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "25", name = "paging") int paging) {
        Language language = commonSupport.loadLanguage(languageCode);
        ResponseDataAPI responseBody = indexCandidateService.perform(page, paging, language);
        return responseBody;
    }

    @PostMapping(UrlConstant.URL_CANDIDATE_PERSONALS)
    @ResponseBody
    public ResponseDataAPI create(
            @Valid @RequestBody CandidatePersonalRequest candidatePersonalRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        User user         = commonSupport.loadUserById(userPrincipal.getId());
        City city         = commonSupport.loadCity(candidatePersonalRequest.getResidentalCityId());
        District district = commonSupport.loadDistrict(candidatePersonalRequest.getResidentalDistrictId());

        Candidate candidate = createCandidateService.perform(candidatePersonalRequest, user, city, district);

        return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, candidate.getId(), "");
    }

	@PatchMapping(UrlConstant.URL_CANDIDATE_PERSONAL)
	@ResponseBody
	public ResponseDataAPI updatePersonal(
            @PathVariable UUID id,
            @Valid @RequestBody CandidatePersonalRequest candidatePersonalRequest,
            @CurrentUser UserPrincipal userPrincipal) throws ForbiddenException {

        Candidate candidate = commonSupport.loadCandidateById(id);

        if(!checkPermission(userPrincipal, candidate)) {
			throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
		}

        City city         = commonSupport.loadCity(candidatePersonalRequest.getResidentalCityId());
        District district = commonSupport.loadDistrict(candidatePersonalRequest.getResidentalDistrictId());

		candidate = updateCandidateService.perform(candidatePersonalRequest, candidate, city, district);

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				"",
				""
        );
	}

    @PatchMapping(UrlConstant.URL_CANDIDATE_EXPECTED)
    @ResponseBody
    public ResponseDataAPI updateExpected(
            @PathVariable UUID id,
            @Valid @RequestBody CandidateExpectedRequest candidateExpectedRequest,
            @CurrentUser UserPrincipal userPrincipal) throws ForbiddenException {

        Candidate candidate = commonSupport.loadCandidateById(id);

        if(!checkPermission(userPrincipal, candidate)) {
            throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
        }

        Country country   = commonSupport.loadCountry(candidateExpectedRequest.getExpectedWorkingCountryId());
        City city         = commonSupport.loadCity(candidateExpectedRequest.getExpectedWorkingCityId());
        District district = commonSupport.loadDistrict(candidateExpectedRequest.getExpectedWorkingDistrictId());
        Business business = commonSupport.loadBusiness(candidateExpectedRequest.getExpectedBusinessId());
        Level level       = commonSupport.loadLevel(candidateExpectedRequest.getExpectedLevelId());
        Contract contract = commonSupport.loadContract(candidateExpectedRequest.getExpectedContractId());

        candidate = updateExpectedService.perform(
                candidateExpectedRequest,
                candidate,
                country,
                city,
                district,
                business,
                level,
                contract
        );

        return new ResponseDataAPI(
                CommonConstant.ResponseDataAPIStatus.SUCCESS,
                "",
                ""
        );
    }

	@PatchMapping(UrlConstant.URL_CANDIDATE_EXPERIENCE)
	@ResponseBody
	public ResponseDataAPI updateCareer(
            @PathVariable UUID id,
            @Valid @RequestBody CandidateExperienceRequest candidateExperienceRequest,
            @CurrentUser UserPrincipal userPrincipal) throws ForbiddenException {

        Candidate candidate = commonSupport.loadCandidateById(id);

        if(!checkPermission(userPrincipal, candidate)) {
			throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
		}

        candidate = updateCareerService.perform(candidateExperienceRequest, candidate);

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				"",
				""
        );
	}

    @GetMapping(UrlConstant.URL_CANDIDATE)
    @ResponseBody
    public ResponseDataAPI show(@PathVariable UUID id, @RequestParam(name = "language") String languageCode){
        Language language = commonSupport.loadLanguage(languageCode);
        Candidate candidate = commonSupport.loadCandidateById(id);

        return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS,
                candidateBaseService.getCandidateResponse(candidate, language),
                "");
    }

    @GetMapping(UrlConstant.URL_MY_CANDIDATE)
    @ResponseBody
    public ResponseDataAPI myCandidate(@CurrentUser UserPrincipal userPrincipal, @RequestParam(name = "language") String languageCode){
        Language language = commonSupport.loadLanguage(languageCode);
        Candidate candidate = commonSupport.loadCandidateByUser(userPrincipal.getId());

        return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS,
                candidateBaseService.getCandidateResponse(candidate, language),
                ""
        );
    }

    private boolean checkPermission(UserPrincipal userPrincipal, Candidate candidate) {
        if (!candidate.getUser().getId().equals(userPrincipal.getId())) {
            return false;
        }
        return true;
	}
}
