package com.japanwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.CompanyTranslation;
import com.japanwork.model.Favorite;
import com.japanwork.model.Job;
import com.japanwork.model.JobTranslation;
import com.japanwork.model.Language;
import com.japanwork.model.User;
import com.japanwork.payload.response.CompanyResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.FavoriteService;
import com.japanwork.support.CommonSupport;

@Controller
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private CommonSupport commonSupport;
	
	@PostMapping(UrlConstant.URL_CANDIDATES_JOB_FAVORITE)
	@ResponseBody
	public ResponseDataAPI create(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		Job job = commonSupport.loadJobById(id);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		Favorite favorite = favoriteService.create(job, user);
		if(favorite != null) {
			return new ResponseDataAPI(
			        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                    "",
                    ""
            );
		} else {
			return new ResponseDataAPI(
			        CommonConstant.ResponseDataAPIStatus.FAILURE,
                    ""
            );
		}
	}

	@DeleteMapping(UrlConstant.URL_CANDIDATES_JOB_FAVORITE)
	@ResponseBody
	public ResponseDataAPI destroy(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {
		Job job = commonSupport.loadJobById(id);
		Favorite favorite = favoriteService.destroy(job, userPrincipal);
		if(favorite != null) {
			return new ResponseDataAPI(
			        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                    "",
                    ""
            );
		} else {
			return new ResponseDataAPI(
			        CommonConstant.ResponseDataAPIStatus.FAILURE,
                    "",
                    ""
            );
		}
	}

	@GetMapping(UrlConstant.URL_CANDIDATES_JOB_FAVORITES)
	@ResponseBody
	public ResponseDataAPI index(@CurrentUser UserPrincipal userPrincipal, @RequestParam(name = "language") String languageCode) {
		Language language = commonSupport.loadLanguage(languageCode);
		User user = commonSupport.loadUserById(userPrincipal.getId());
		List<JobTranslation> list = favoriteService.index(user, language);
		List<JobResponse> listJobResponses = new ArrayList<JobResponse>();
		for (JobTranslation jobTranslation : list) {
			Job job = jobTranslation.getJob();
			CompanyTranslation companyTranslation = commonSupport.loadCompanyTranslation(job.getCompany(), language);
			CompanyResponse companyResponse = new CompanyResponse().companyMainSerializer(job.getCompany(), companyTranslation);
			listJobResponses.add(new JobResponse().jobMainSerializer(job, jobTranslation, companyResponse));
		}
		return new ResponseDataAPI(
		        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                listJobResponses,
                ""
        );
	}
}
