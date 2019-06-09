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
	public static final String URL_USER = "/user";
	public static final String URL_USER_CHANGE_PASSWORD = "/user/change-password";
	public static final String URL_USER_FORGET_PASSWORD = "/forget-password";
	public static final String URL_USER_RESET_PASSWORD = "/reset-password";
	public static final String URL_DELETE_ACCOUNT = "/user/delete";
	public static final String URL_CONFIRM_ACCOUNT = "/confirm-account";
	public static final String URL_RESEND_REGISTRATION_TOKEN = "/resend-registration";		
	
	//Business Type
	public static final String URL_BUSINESS = "/businesses";
	public static final String URL_BUSINESS_ID = "/business/{id}";
	public static final String URL_BUSINESS_UNDEL = "/business/undelete/{id}";
	
	//Business Type
	public static final String URL_LANGUAGUE_TYPE = "/languagues";
	public static final String URL_LANGUAGUE_TYPE_ID = "/languagues/{id}";
	public static final String URL_LANGUAGUE_TYPE_UNDEL = "/languagues/undelete/{id}";
	
	//Currency Unit
	public static final String URL_CURRENCYUNIT = "/currencyunit";
	public static final String URL_CURRENCYUNIT_ID = "/currencyunit/{id}";
	public static final String URL_CURRENCYUNIT_UNDEL = "/currencyunit/undelete/{id}";
	
	
	//District
	public static final String URL_DISTRICT = "/districts";
	public static final String URL_DISTRICTS = "/districts/list";
	public static final String URL_DISTRICTS_ID = "/districts/list/{id}";
	public static final String URL_DISTRICT_ID = "/districts/{id}";
	public static final String URL_DISTRICT_UNDEL = "/districts/undelete/{id}";
	
	
	//District
	public static final String URL_COUNTRY = "/countries";
	public static final String URL_COUNTRY_ID = "/country/{id}";
	public static final String URL_COUNTRY_UNDEL = "/country/undelete/{id}";
	
	//District
	public static final String URL_CITY = "/cities";
	public static final String URL_CITIES = "/cities/list";
	public static final String URL_CITIES_ID = "/cities/list/{id}";
	public static final String URL_CITY_ID = "/cities/{id}";
	public static final String URL_CITY_UNDEL = "/cities/undelete/{id}";
	
	//Company
	public static final String URL_COMPANY = "/companies";
	public static final String URL_COMPANY_ID = "/companies/{id}";
	public static final String URL_COMPANY_UNDEL_ID = "/companies/undelete/{id}";
	public static final String URL_COMPANY_DEL_ID = "/companies/delete/{id}";
	
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
	
	//CANDIDATE
	public static final String URL_CANDIDATE = "/candidates";
	public static final String URL_CANDIDATE_ID = "/candidates/{id}";
	public static final String URL_CANDIDATE_UNDEL = "/candidates/undelete/{id}";
	
	//CANDIDATE_PERSONAL
	public static final String URL_CANDIDATE_PERSONAL = "/candidates/personal";
	public static final String URL_CANDIDATE_ID_PERSONAL = "/candidates/{id}/personal";
	
	//CANDIDATE_WISH
	public static final String URL_CANDIDATE_ID_WISH = "/candidates/{id}/wish";
	
	//CANDIDATE_EXPERIENCES
	public static final String URL_CANDIDATE_ID_EXPERIENCE = "/candidates/{id}/experience";
	
	//UPLOAD FILE ON AMW
	public static final String URL_AMW_UPLOAD_FILE = "/storage/upload-file";
	public static final String URL_AMW_DELETE_FILE = "/storage/delete-file";
}
