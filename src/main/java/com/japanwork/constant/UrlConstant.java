package com.japanwork.constant;

public class UrlConstant {
	//Error
	public static final String URL_ERROR_403 = "/403";

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
	public static final String URL_NOTIFICATIONS_ENDPOINT = "/notifications/endpoint";

	//Business Type
	public static final String URL_BUSINESS = "/businesses";
	public static final String URL_BUSINESS_ID = "/business/{id}";
	public static final String URL_BUSINESS_UNDEL = "/business/undelete/{id}";

	//Languague Type
	public static final String URL_LANGUAGUE = "/languagues";
	public static final String URL_LANGUAGUE_ID = "/languagues/{id}";
	public static final String URL_LANGUAGUE_UNDEL = "/languagues/undelete/{id}";

	//Languague Type
	public static final String URL_LANGUAGUE_TYPE = "/languague-certificates";
	public static final String URL_LANGUAGUE_TYPE_ID = "/languague-certificates/{id}";
	public static final String URL_LANGUAGUE_TYPE_UNDEL = "/languague-certificates/undelete/{id}";

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
	public static final String URL_CITIES_ID = "/cities/list/{code}";
	public static final String URL_CITY_ID = "/cities/{id}";
	public static final String URL_CITY_UNDEL = "/cities/undelete/{id}";

	//Company
	public static final String URL_COMPANY = "/companies";
	public static final String URL_COMPANY_IDS = "/companies-by-ids";
	public static final String URL_COMPANY_ID = "/companies/{id}";
	public static final String URL_MY_COMPANY = "/companies/my_company";
	public static final String URL_COMPANY_ID_JOB = "/companies/{id}/jobs";
	public static final String URL_COMPANY_UNDEL_ID = "/companies/undelete/{id}";
	public static final String URL_COMPANY_DEL_ID = "/companies/delete/{id}";
	public static final String URL_COMPANY_ID_JOB_APPLICATION = "/companies/{id}/job_applications";
	public static final String URL_COMPANY_ID_REQUEST = "/companies/{id}/requests";

	//Job
	public static final String URL_JOB = "/jobs";
	public static final String URL_JOB_ID = "/jobs/{id}";
	public static final String URL_JOB_UNDEL = "/jobs/undelete/{id}";

	//Job Translation
    public static final String URL_JOB_TRANSLATION = "v1/job_translations";
    public static final String URL_SHOW_JOB_TRANSLATION = "v1/job_translations";

	//Job Application
    public static final String URL_JOB_APPLICATION_CANDIDATE_JOIN = "/jobs/{id}/apply";
	public static final String URL_JOB_APPLICATION_COMPANY_REJECT = "/job-applications/{id}/reject";
	public static final String URL_JOB_APPLICATION_CANCEL = "/job-applications/{id}/cancel";
	public static final String URL_JOB_APPLICATION_COMPANY_ACCEPT_APPLY = "/job-applications/{id}/accept-apply";
	public static final String URL_JOB_APPLICATION_COMPANY_APPROVE = "/job-applications/{id}/approve";
	public static final String URL_JOB_APPLICATION_ID = "/job-applications/{id}";
	public static final String URL_JOB_APPLICATION_ID_REQUEST = "/job-applications/{id}/request";

	//Request translation
	public static final String URL_REQUEST_TRANSLATION_TRANSLATOR_JOIN = "/requests/{id}/apply";
	public static final String URL_REQUEST_TRANSLATION_ACCEPT_APPLY = "/requests/{id}/accept-apply";
	public static final String URL_REQUEST_TRANSLATION_CONFIRM_FINISHED = "/requests/{id}/check-finished";
	public static final String URL_REQUEST_TRANSLATION_ACCEPT_FINISHED = "/requests/{id}/accept-finished";
	public static final String URL_REQUEST_TRANSLATION_REFUSE_FINISHED = "/requests/{id}/refuse-finished";
	public static final String URL_REQUEST_TRANSLATION_CANCEL = "/requests/{id}/cancel";
	public static final String URL_REQUEST_TRANSLATION_REJECT = "/requests/{id}/reject";
	public static final String URL_REQUEST_TRANSLATIONS = "/requests";
	public static final String URL_REQUEST_TRANSLATIONS_ID = "/requests/{id}";

	//Conversation
	public static final String URL_JOB_APPLICATION_ID_CONVERSATION= "/job-applications/{id}/conversations";
	public static final String URL_JOB_APPLICATION_ID_CONVERSATION_ALL = "/job-applications/{id}/conversation-all";
	public static final String URL_JOB_APPLICATION_ID_CONVERSATION_CANDIDATE = "/job-applications/{id}/conversation-support-candidate";
	public static final String URL_JOB_APPLICATION_ID_CONVERSATION_COMPANY = "/job-applications/{id}/conversation-support-company";

	//Notification
	public static final String URL_CONVERSATION_ID= "/conversations/{id}";
	public static final String URL_NOTIFICATION = "/notifications";
	public static final String URL_NOTIFICATION_UNREADS_NUMBER = "/notifications/unreads-number";
	public static final String URL_NOTIFICATION_MARK_ALL_READ = "/notifications/mark-all-read";
	public static final String URL_NOTIFICATION_MARK_READS = "/notifications/mark-reads";

	//Contract
	public static final String URL_CONTRACT = "/contracts";
	public static final String URL_CONTRACT_ID = "/contracts/{id}";
	public static final String URL_CONTRACT_UNDEL = "/contracts/undelete/{id}";

	//Level
	public static final String URL_LEVEL = "/levels";
	public static final String URL_LEVEL_ID = "/levels/{id}";
	public static final String URL_LEVEL_UNDEL = "/levels/undelete/{id}";

	//Candidate
	public static final String URL_CANDIDATE = "/candidates";
	public static final String URL_CANDIDATE_IDS = "/candidates-by-ids";
	public static final String URL_MY_CANDIDATE = "/candidates/my-candidate";
	public static final String URL_CANDIDATE_ID = "/candidates/{id}";
	public static final String URL_CANDIDATE_UNDEL = "/candidates/undelete/{id}";
	public static final String URL_CANDIDATE_JOB_FAVORITE_ID = "/candidates/jobs/{id}/favorite";
	public static final String URL_CANDIDATE_JOB_FAVORITE = "/candidates/jobs/favorite";
	public static final String URL_CANDIDATE_ID_JOB_APPLICATION = "/candidates/{id}/job_applications";
	public static final String URL_CANDIDATE_ID_REQUEST = "/candidates/{id}/requests";

	//Candidate personal
	public static final String URL_CANDIDATE_PERSONAL = "/candidates/personal";
	public static final String URL_CANDIDATE_ID_PERSONAL = "/candidates/{id}/personal";

	//Candidate expected
	public static final String URL_CANDIDATE_ID_WISH = "/candidates/{id}/wish";

	//Candidate experience
	public static final String URL_CANDIDATE_ID_EXPERIENCE = "/candidates/{id}/experience";

	//Candidate translation
    public static final String URL_CANDIDATE_TRANSLATION = "/v1/candidates/{id}/candidate_translations";

	//Upload file on AWS
	public static final String URL_AMW_UPLOAD_FILE = "/storage/upload-file";
	public static final String URL_AMW_DELETE_FILE = "/storage/delete-file";

	//Translator
	public static final String URL_TRANSLATOR = "/translators";
	public static final String URL_TRANSLATOR_IDS = "/translators-by-ids";
	public static final String URL_TRANSLATOR_ID = "/translators/{id}";
	public static final String URL_MY_TRANSLATOR = "/translators/my-translator";
	public static final String URL_TRANSLATOR_UNDEL_ID = "/translators/undelete/{id}";
	public static final String URL_TRANSLATOR_DEL_ID = "/translators/delete/{id}";
	public static final String URL_TRANSLATOR_ID_JOB_APPLICATION = "/translators/{id}/job_applications";
	public static final String URL_TRANSLATOR_ID_REQUEST = "/translators/{id}/requests";

	//Company Translation
    public static final String URL_COMPANY_TRANSLATION = "/v1/company_translations";
    public static final String URL_COMPANY_TRANSLATION_BY_ID = "api/v1/company_translations/{id}";
}
