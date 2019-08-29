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
	public static final String URL_PROFILE = "/v1/user/profile";
	public static final String URL_USER_CHANGE_PASSWORD = "/v1/user/change_password";
	public static final String URL_USER_FORGET_PASSWORD = "/v1/forget_password";
	public static final String URL_USER_RESET_PASSWORD = "/v1/reset_password";
	public static final String URL_CONFIRM_ACCOUNT = "/v1/confirm_account";
	public static final String URL_RESEND_REGISTRATION_TOKEN = "/v1/resend_registration";
	public static final String URL_NOTIFICATIONS_ENDPOINT = "/v1/notifications/endpoint";

	//Business Type
	public static final String URL_BUSINESSES = "/v1/businesses";
	public static final String URL_BUSINESS = "/v1/businesses/{id}";

	//Languague Type
	public static final String URL_LANGUAGUES = "/v1/languagues";
	public static final String URL_LANGUAGUE = "/v1/languagues/{id}";
	//Languague certificate
	public static final String URL_LANGUAGUE_TYPES = "/v1/languague_certificates";
	public static final String URL_LANGUAGUE_TYPE = "/v1/languague_certificates/{id}";

	//District
	public static final String URL_DISTRICTS = "/v1/districts";
	public static final String URL_DISTRICTS_BATCH = "/v1/districts/batch";
	public static final String URL_DISTRICT = "/v1/districts/{id}";

	//Country
	public static final String URL_COUNTRIES = "/v1/countries";
	public static final String URL_COUNTRY = "/v1/countries/{id}";
	public static final String URL_COUNTRIES_CITIES = "/v1/countries/{code}/cities";

	//City
	public static final String URL_CITIES = "/v1/cities";
	public static final String URL_CITIES_BATCH = "/v1/cities/batch";
	public static final String URL_CITY = "/v1/cities/{id}";
	public static final String URL_CITIES_DISTRICTS = "/v1/cities/{id}/districts";

	//Company
	public static final String URL_COMPANIES = "/v1/companies";
	public static final String URL_COMPANY_IDS = "/v1/companies_by_ids";
	public static final String URL_COMPANY = "/v1/companies/{id}";
	public static final String URL_COMPANY_UNDELETE = "/v1/companies/{id}/undelete";
	public static final String URL_MY_COMPANY = "/v1/companies/my_company";
	public static final String URL_COMPANY_JOB = "/v1/companies/{id}/jobs";
	public static final String URL_COMPANY_JOB_APPLICATION = "/v1/companies/{id}/job_applications";
	public static final String URL_COMPANY_REQUEST = "/v1/companies/{id}/requests";

	//Company Translation
    public static final String URL_COMPANY_TRANSLATION = "/v1/companies/{id}/company_translations";

	//Job
	public static final String URL_JOBS = "/v1/jobs";
	public static final String URL_JOB = "/v1/jobs/{id}";
	public static final String URL_JOB_UNDELETE = "/v1/jobs/{id}/undelete";

	//Job Translation
    public static final String URL_JOB_TRANSLATION = "/v1/jobs/{id}/job_translations/{language}";
    public static final String URL_JOB_TRANSLATIONS = "/v1/jobs/{id}/job_translations";

	//Job Application
    public static final String URL_JOB_APPLICATIONS_CANDIDATE_JOIN = "/v1/jobs/{id}/apply";
	public static final String URL_JOB_APPLICATIONS_COMPANY_REJECT = "/v1/job_applications/{id}/reject";
	public static final String URL_JOB_APPLICATIONS_CANCEL = "/v1/job_applications/{id}/cancel";
	public static final String URL_JOB_APPLICATIONS_COMPANY_ACCEPT_APPLY = "/v1/job_applications/{id}/accept_apply";
	public static final String URL_JOB_APPLICATIONS_COMPANY_APPROVE = "/v1/job_applications/{id}/approve";
	public static final String URL_JOB_APPLICATION = "/v1/job_applications/{id}";
	public static final String URL_JOB_APPLICATIONS_REQUEST = "/v1/job_applications/{id}/request";

	//Request translation
	public static final String URL_REQUEST_TRANSLATIONS_TRANSLATOR_JOIN = "/v1/requests/{id}/apply";
	public static final String URL_REQUEST_TRANSLATIONS_ACCEPT_APPLY = "/v1/requests/{id}/accept_apply";
	public static final String URL_REQUEST_TRANSLATIONS_CONFIRM_FINISHED = "/v1/requests/{id}/check_finished";
	public static final String URL_REQUEST_TRANSLATIONS_ACCEPT_FINISHED = "/v1/requests/{id}/accept_finished";
	public static final String URL_REQUEST_TRANSLATIONS_REFUSE_FINISHED = "/v1/requests/{id}/refuse_finished";
	public static final String URL_REQUEST_TRANSLATIONS_CANCEL = "/v1/requests/{id}/cancel";
	public static final String URL_REQUEST_TRANSLATIONS_REJECT = "/v1/requests/{id}/reject";
	public static final String URL_REQUEST_TRANSLATIONS = "/v1/requests";
	public static final String URL_REQUEST_TRANSLATION = "/v1/requests/{id}";

	//Notification
	public static final String URL_CONVERSATION= "/v1/conversations/{id}/messages";
	public static final String URL_CONVERSATION_UPLOAD_FILE= "/v1/conversations/{id}/upload_file";
	public static final String URL_NOTIFICATIONS = "/v1/notifications";
	public static final String URL_NOTIFICATIONS_UNREADS_NUMBER = "/v1/notifications/unreads_number";
	public static final String URL_NOTIFICATIONS_MARK_ALL_READ = "/v1/notifications/mark_all_read";
	public static final String URL_NOTIFICATIONS_MARK_READS = "/v1/notifications/mark_reads";

	//Contract
	public static final String URL_CONTRACTS = "/v1/contracts";
	public static final String URL_CONTRACT = "/v1/contract/{id}";

	//Level
	public static final String URL_LEVELS = "/v1/levels";
	public static final String URL_LEVEL = "/v1/levels/{id}";

	//Candidate
	public static final String URL_CANDIDATES = "/v1/candidates";
	public static final String URL_CANDIDATE_IDS = "/v1/candidates_by_ids";
	public static final String URL_MY_CANDIDATE = "/v1/candidates/my_candidate";
	public static final String URL_CANDIDATE = "/v1/candidates/{id}";
	public static final String URL_CANDIDATE_UNDELETE = "/v1/candidates/{id}/undelete";
	public static final String URL_CANDIDATES_JOB_FAVORITE = "/v1/candidates/jobs/{id}/favorite";
	public static final String URL_CANDIDATES_JOB_FAVORITES = "/v1/candidates/jobs/favorite";
	public static final String URL_CANDIDATES_JOB_APPLICATIONS = "/v1/candidates/{id}/job_applications";
	public static final String URL_CANDIDATES_REQUESTS = "/v1/candidates/{id}/requests";

	//Candidate personal
	public static final String URL_CANDIDATE_PERSONALS = "/v1/candidates/personal";
	public static final String URL_CANDIDATE_PERSONAL = "/v1/candidates/{id}/personal";

	//Candidate expected
	public static final String URL_CANDIDATE_EXPECTED = "/v1/candidates/{id}/expected";

	//Candidate experience
	public static final String URL_CANDIDATE_EXPERIENCE = "/v1/candidates/{id}/experience";

	//Candidate translation
    public static final String URL_CANDIDATE_TRANSLATION = "/v1/candidates/{id}/candidate_translations";

	//Upload file on AWS
	public static final String URL_AMW = "/v1/storages";

	//Translator
	public static final String URL_TRANSLATORS = "/v1/translators";
	public static final String URL_TRANSLATOR_IDS = "/v1/translators_by_ids";
	public static final String URL_TRANSLATOR = "/v1/translators/{id}";
	public static final String URL_MY_TRANSLATOR = "/v1/translators/my_translator";
	public static final String URL_TRANSLATORS_UNDEL = "/v1/translators/{id}/undelete";
	public static final String URL_TRANSLATORS_JOB_APPLICATIONS = "/v1/translators/{id}/job_applications";
	public static final String URL_TRANSLATORS_REQUESTS = "/v1/translators/{id}/requests";
}
