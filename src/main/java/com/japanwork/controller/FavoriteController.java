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
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Job;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.JobResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.FavoriteService;
import com.japanwork.service.JobService;

@Controller
public class FavoriteController {
	
	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private JobService jobService;
	
	@PostMapping(UrlConstant.URL_CANDIDATE_JOB_FAVORITE_ID)
	@ResponseBody
	public BaseDataResponse canidateFavoriteJob(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return new BaseDataResponse(favoriteService.canidateFavoriteJob(id, userPrincipal));
	}
	
	@DeleteMapping(UrlConstant.URL_CANDIDATE_JOB_FAVORITE_ID)
	@ResponseBody
	public BaseDataResponse canidateUnFavoriteJob(@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal) {		
		return new BaseDataResponse(favoriteService.canidateUnFavoriteJob(id, userPrincipal));
	}
	
	@GetMapping(UrlConstant.URL_CANDIDATE_JOB_FAVORITE)
	@ResponseBody
	public BaseDataResponse listFavoriteJob(@CurrentUser UserPrincipal userPrincipal) {
		List<Job> list = favoriteService.listFavoriteJob(userPrincipal);
		List<JobResponse> listJobResponses = new ArrayList<JobResponse>();
		for (Job job : list) {
			listJobResponses.add(jobService.convertJobResponse(job));
		}
		return new BaseDataResponse(listJobResponses);
	}
}
