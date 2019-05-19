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
	public static final String URL_BUSINESS = "/business/*";
	public static final String URL_BUSINESS_FIND_BY_ID = "/business/{id}";
	public static final String URL_BUSINESS_CREATE = "/business/create";
	public static final String URL_BUSINESS_UPDATE = "/business/update/{id}";
	public static final String URL_BUSINESS_DELETE = "/business/delete/{id}";
	
	
	//District
	public static final String URL_DISTRICT = "/district/*";
	public static final String URL_DISTRICT_FIND_BY_ID = "/district/{id}";
	public static final String URL_DISTRICT_CREATE = "/district/create";
	public static final String URL_DISTRICT_UPDATE = "/district/update/{id}";
	public static final String URL_DISTRICT_DELETE = "/district/delete/{id}";
	
	//District
	public static final String URL_CITY = "/city/*";
	public static final String URL_CITY_FIND_BY_ID = "/city/{id}";
	public static final String URL_CITY_CREATE = "/city/create";
	public static final String URL_CITY_UPDATE = "/city/update/{id}";
	public static final String URL_CITY_DELETE = "/city/delete/{id}";
	
	//Company
	public static final String URL_COMPANY = "/companies";
	public static final String URL_COMPANY_ID = "/companies/{id}";
	public static final String URL_COMPANY_UNDEL = "/companies/undelete/{id}";
}
