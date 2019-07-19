package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ServerError;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Job;
import com.japanwork.model.Language;
import com.japanwork.model.RequestDetail;
import com.japanwork.model.User;
import com.japanwork.payload.request.RequestDetailRequest;
import com.japanwork.repository.request_detail.RequestDetailRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class RequestDetailService {
	
	@Autowired
	private RequestDetailRepository requestDetailRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private CandidateService candidateService;
	
	public RequestDetail createRequestDetail(RequestDetailRequest requestDetailRequest, UserPrincipal userPrincipal) 
			throws ServerError{
		try {
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			User user = userService.getUser(userPrincipal);
			
			RequestDetail requestDetail = new RequestDetail();
			if(requestDetailRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_COMPANY)) {
				Company company = companyService.findByUserAndIsDelete(user, null);
				requestDetail.setOwnerId(company.getId());
				requestDetail.setObjectTableId(company.getId());
			}
			
			if(requestDetailRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB)) {
				Company company = companyService.findByUserAndIsDelete(user, null);
				requestDetail.setOwnerId(company.getId());
				Job job = jobService.findByIdAndIsDelete(requestDetailRequest.getObjectTableId());
				requestDetail.setObjectTableId(job.getId());
			}
			
			if(requestDetailRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_CANDIDATE)) {
				Candidate candidate = candidateService.myCandidate(userPrincipal);
				requestDetail.setOwnerId(candidate.getId());
				requestDetail.setObjectTableId(candidate.getId());
			}
			
			if(requestDetailRequest.getRequestType().equals(CommonConstant.RequestTranslationType.REQUEST_TRANSLATION_JOB_APPLICATION)) {
				Company company = companyService.findByUserAndIsDelete(user, null);
				requestDetail.setOwnerId(company.getId());
//				Job
				
//				requestDetail.setObjectTableId(candidate.getId());
			}
			
			requestDetail.setLanguage(new Language(requestDetailRequest.getLanguageId()));
			requestDetail.setCreatedAt(timestamp);
			return requestDetail;
		
		} catch (Exception e) {
			throw new ServerError(MessageConstant.COMPANY_CREATE_FAIL);
		}
	}
}
