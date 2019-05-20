package com.japanwork.constant;

public class UrlConstant {
	//Error
	public static final String URL_ERROR_404 = "/404";
	public static final String URL_ERROR_403 = "/403";
	public static final String URL_ERROR_401 = "/401";
	public static final String URL_ERROR_400 = "/400";
	
	//Auth
	public static final String URL_LOGIN = "/login";
	public static final String URL_OAUTH2_LOGIN = "/login/redirect";
	public static final String URL_LOGOUT = "/logout";
	public static final String URL_REGISTER = "/register";
	public static final String URL_CONFIRM_ACCOUNT = "/confirm-account";
	public static final String URL_RESEND_REGISTRATION_TOKEN = "/resend-registration";		
	
	//Business Type
	public static final String URL_BUSINESS = "/business";
	public static final String URL_BUSINESS_ID = "/business/{id}";
	public static final String URL_BUSINESS_UNDEL = "/business/undelete/{id}";
	
	
	//District
	public static final String URL_DISTRICT = "/districts";
	public static final String URL_DISTRICT_ID = "/districts/{id}";
	public static final String URL_DISTRICT_UNDEL = "/districts/undelete/{id}";
	
	//District
	public static final String URL_CITY = "/citys";
	public static final String URL_CITY_ID = "/citys/{id}";
	public static final String URL_CITY_UNDEL = "/citys/undelete/{id}";
	
	//Company
	public static final String URL_COMPANY = "/companies";
	public static final String URL_COMPANY_ID = "/companies/{id}";
	public static final String URL_COMPANY_UNDEL = "/companies/undelete/{id}";
	
	//Job
	public static final String URL_JOB = "/jobs";
	public static final String URL_JOB_ID = "/jobs/{id}";
	public static final String URL_JOB_UNDEL = "/jobs/undelete/{id}";
	
	//Contract
	public static final String URL_CONTRACT = "/contracts";
	public static final String URL_CONTRACT_ID = "/contracts/{id}";
	public static final String URL_CONTRACT_UNDEL = "/contracts/undelete/{id}";
	
	//Level
	public static final String URL_LEVEL = "/levels";
	public static final String URL_LEVEL_ID = "/levels/{id}";
	public static final String URL_LEVEL_UNDEL = "/levels/undelete/{id}";
}
