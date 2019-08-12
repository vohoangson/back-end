package com.japanwork.constant;

public class UrlConstant {
	//Error
	public static final String URL_ERROR_403 = "/403";

	//Auth
	public static final String URL_LOGIN = "/v1/login";
	public static final String URL_OAUTH2_LOGIN = "/v1/login/redirect";
	public static final String URL_LOGOUT = "/v1/logout";
	public static final String URL_REGISTER = "/v1/register";
	public static final String URL_USER = "/v1/user";
	public static final String URL_USER_CHANGE_PASSWORD = "/v1/user/change_password";
	public static final String URL_USER_FORGET_PASSWORD = "/v1/forget_password";
	public static final String URL_USER_RESET_PASSWORD = "/v1/reset_password";
	public static final String URL_DELETE_ACCOUNT = "/v1/user/delete";
	public static final String URL_CONFIRM_ACCOUNT = "/v1/confirm_account";
	public static final String URL_RESEND_REGISTRATION_TOKEN = "/v1/resend_registration";
	public static final String URL_NOTIFICATIONS_ENDPOINT = "/v1/notifications/endpoint";

	//Business Type
	public static final String URL_BUSINESS = "/v1/businesses";
	public static final String URL_BUSINESS_ID = "/v1/business/{id}";
	public static final String URL_BUSINESS_UNDEL = "/v1/business/{id}/undelete";

	//Languague Type
	public static final String URL_LANGUAGUE = "/v1/languagues";
	public static final String URL_LANGUAGUE_ID = "/v1/languague/{id}";
	public static final String URL_LANGUAGUE_UNDEL = "/v1/languague/{id}/undelete";

	//Languague Type
	public static final String URL_LANGUAGUE_TYPE = "/v1/languague_certificates";
	public static final String URL_LANGUAGUE_TYPE_ID = "/v1/languague_certificate/{id}";
	public static final String URL_LANGUAGUE_TYPE_UNDEL = "/v1/languague_certificate/{id}/undelete";

	//District
	public static final String URL_DISTRICT = "/v1/districts";
	public static final String URL_DISTRICTS = "/v1/districts/list";
	public static final String URL_DISTRICTS_ID = "/v1/districts/list/{id}";
	public static final String URL_DISTRICT_ID = "/v1/district/{id}";
	public static final String URL_DISTRICT_UNDEL = "/v1/district/{id}/undelete";

	//Country
	public static final String URL_COUNTRY = "/v1/countries";
	public static final String URL_COUNTRY_ID = "/v1/country/{id}";
	public static final String URL_COUNTRY_UNDEL = "/v1/country/{id}/undelete";

	//City
	public static final String URL_CITY = "/v1/cities";
	public static final String URL_CITIES = "/v1/cities/list";
	public static final String URL_CITIES_ID = "/v1/cities/list/{code}";
	public static final String URL_CITY_ID = "/v1/city/{id}";
	public static final String URL_CITY_UNDEL = "/v1/city/{id}/undelete";

	//Company
	public static final String URL_COMPANY = "/v1/companies";
	public static final String URL_COMPANY_IDS = "/v1/companies_by_ids";
	public static final String URL_COMPANY_ID = "/v1/company/{id}";
	public static final String URL_MY_COMPANY = "/v1/company/my_company";
	public static final String URL_COMPANY_ID_JOB = "/v1/company/{id}/jobs";
	public static final String URL_COMPANY_UNDEL_ID = "/v1/company/{id}/undelete";
	public static final String URL_COMPANY_DEL_ID = "/v1/company/{id}/delete";
	public static final String URL_COMPANY_ID_JOB_APPLICATION = "/v1/company/{id}/job_applications";
	public static final String URL_COMPANY_ID_REQUEST = "/v1/company/{id}/requests";

	//Job
	public static final String URL_JOB = "/v1/jobs";
	public static final String URL_JOB_ID = "/v1/job/{id}";
	public static final String URL_JOB_UNDEL = "/v1/job/{id}/undelete";

	//Job Translation
    public static final String URL_JOB_TRANSLATION = "v1/job_translations";

	//Job Application
    public static final String URL_JOB_APPLICATION_CANDIDATE_JOIN = "/v1/job/{id}/apply";
	public static final String URL_JOB_APPLICATION_COMPANY_REJECT = "/v1/job_application/{id}/reject";
	public static final String URL_JOB_APPLICATION_CANCEL = "/v1/job_application/{id}/cancel";
	public static final String URL_JOB_APPLICATION_COMPANY_ACCEPT_APPLY = "/v1/job_application/{id}/accept_apply";
	public static final String URL_JOB_APPLICATION_COMPANY_APPROVE = "/v1/job_application/{id}/approve";
	public static final String URL_JOB_APPLICATION_ID = "/v1/job_application/{id}";
	public static final String URL_JOB_APPLICATION_ID_REQUEST = "/v1/job_application/{id}/request";

	//Request translation
	public static final String URL_REQUEST_TRANSLATION_TRANSLATOR_JOIN = "/v1/request/{id}/apply";
	public static final String URL_REQUEST_TRANSLATION_ACCEPT_APPLY = "/v1/request/{id}/accept_apply";
	public static final String URL_REQUEST_TRANSLATION_CONFIRM_FINISHED = "/v1/request/{id}/check_finished";
	public static final String URL_REQUEST_TRANSLATION_ACCEPT_FINISHED = "/v1/request/{id}/accept_finished";
	public static final String URL_REQUEST_TRANSLATION_REFUSE_FINISHED = "/v1/request/{id}/refuse_finished";
	public static final String URL_REQUEST_TRANSLATION_CANCEL = "/v1/request/{id}/cancel";
	public static final String URL_REQUEST_TRANSLATION_REJECT = "/v1/request/{id}/reject";
	public static final String URL_REQUEST_TRANSLATIONS = "/v1/requests";
	public static final String URL_REQUEST_TRANSLATIONS_ID = "/v1/request/{id}";

	//Notification
	public static final String URL_CONVERSATION_ID= "/v1/conversation/{id}";
	public static final String URL_NOTIFICATION = "/v1/notifications";
	public static final String URL_NOTIFICATION_UNREADS_NUMBER = "/v1/notification/unreads_number";
	public static final String URL_NOTIFICATION_MARK_ALL_READ = "/v1/notifications/mark_all_read";
	public static final String URL_NOTIFICATION_MARK_READS = "/v1/notifications/mark_reads";

	//Contract
	public static final String URL_CONTRACT = "/v1/contracts";
	public static final String URL_CONTRACT_ID = "/v1/contract/{id}";
	public static final String URL_CONTRACT_UNDEL = "/v1/contract/{id}/undelete";

	//Level
	public static final String URL_LEVEL = "/v1/levels";
	public static final String URL_LEVEL_ID = "/v1/level/{id}";
	public static final String URL_LEVEL_UNDEL = "/v1/level/{id}/undelete";

	//Candidate
	public static final String URL_CANDIDATE = "/v1/candidates";
	public static final String URL_CANDIDATE_IDS = "/v1/candidates_by_ids";
	public static final String URL_MY_CANDIDATE = "/v1/candidate/my_candidate";
	public static final String URL_CANDIDATE_ID = "/v1/candidate/{id}";
	public static final String URL_CANDIDATE_UNDEL = "/v1/candidate/{id}/undelete";
	public static final String URL_CANDIDATE_JOB_FAVORITE_ID = "/v1/candidate/job/{id}/favorite";
	public static final String URL_CANDIDATE_JOB_FAVORITE = "/v1/candidate/jobs/favorite";
	public static final String URL_CANDIDATE_ID_JOB_APPLICATION = "/v1/candidate/{id}/job_applications";
	public static final String URL_CANDIDATE_ID_REQUEST = "/v1/candidate/{id}/requests";

	//Candidate personal
	public static final String URL_CANDIDATE_PERSONAL = "/v1/candidate/personal";
	public static final String URL_CANDIDATE_ID_PERSONAL = "/v1/candidate/{id}/personal";

	//Candidate expected
	public static final String URL_CANDIDATE_ID_WISH = "/v1/candidate/{id}/wish";

	//Candidate experience
	public static final String URL_CANDIDATE_ID_EXPERIENCE = "/v1/candidate/{id}/experience";

	//Candidate translation
    public static final String URL_CANDIDATE_TRANSLATION = "/v1/v1/candidate/{id}/candidate_translations";

	//Upload file on AWS
	public static final String URL_AMW_UPLOAD_FILE = "/v1/storage/upload_file";
	public static final String URL_AMW_DELETE_FILE = "/v1/storage/delete_file";

	//Translator
	public static final String URL_TRANSLATOR = "/v1/translators";
	public static final String URL_TRANSLATOR_IDS = "/v1/translators_by_ids";
	public static final String URL_TRANSLATOR_ID = "/v1/translator/{id}";
	public static final String URL_MY_TRANSLATOR = "/v1/translator/my_translator";
	public static final String URL_TRANSLATOR_UNDEL_ID = "/v1/translator/{id}/undelete";
	public static final String URL_TRANSLATOR_DEL_ID = "/v1/translator/{id}/delete";
	public static final String URL_TRANSLATOR_ID_JOB_APPLICATION = "/v1/translator/{id}/job_applications";
	public static final String URL_TRANSLATOR_ID_REQUEST = "/v1/translator/{id}/requests";

	//Company Translation
    public static final String URL_COMPANY_TRANSLATION = "/v1/company_translations";
    public static final String URL_COMPANY_TRANSLATION_BY_ID = "/v1/company_translations/{id}";
}
