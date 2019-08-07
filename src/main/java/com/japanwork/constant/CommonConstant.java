package com.japanwork.constant;

public class CommonConstant {
	
	public interface StatusTranslate {
        String UNTRANSLATED = "UNTRANSLATED";
        String TRANSLATING = "TRANSLATING";
        String TRANSLATION_COMPLETED = "TRANSLATION_COMPLETED";
        String TRANSLATION_NOT_COMPLETED = "TRANSLATION_NOT_COMPLETED";
    }
	
	public interface StatusApplyJob {
		String WAITING_FOR_COMPANY_APPROVE_CANDIDATE = "WAITING_FOR_COMPANY_APPROVE_CANDIDATE";
		String WAITING_FOR_TRANSLATOR_JOIN = "WAITING_FOR_TRANSLATOR_JOIN";
		String REJECT_CANDIDATE = "REJECT_CANDIDATE";
		String ON_GOING = "ON_GOING";
		String FINISHED = "FINISHED";
		String CANCELED = "CANCELED";
    }
	
	public interface RequestTranslationStatus {
		String WAITING_FOR_HELPER = "WAITING_FOR_HELPER";
		String WAITING_FOR_OWNER_AGREE = "WAITING_FOR_OWNER_AGREE";
		String ON_GOING = "ON_GOING";
		String FINISHED = "FINISHED";
		String REVIEWED = "REVIEWED";
		String CANCELED = "CANCELED";
		String REJECTED = "REJECTED";
    }
	
	public interface RequestTranslationType {
		String REQUEST_TRANSLATION_COMPANY = "REQUEST_COMPANY_TRANSLATION";
		String REQUEST_TRANSLATION_JOB = "REQUEST_JOB_TRANSLATION";
		String REQUEST_TRANSLATION_CANDIDATE = "REQUEST_CANDIDATE_TRANSLATION";
		String REQUEST_TRANSLATION_JOB_APPLICATION = "REQUEST_JOB_APPLICATION_SUPPORT";
    }
	
	public interface HistoryStatusTypes {
		String REQUEST = "REQUEST";
		String JOB_APPLICATION = "JOB_APPLICATION";
    }
	
	public interface Role {
		String COMPANY = "ROLE_COMPANY";
		String CANDIDATE = "ROLE_CANDIDATE";
		String ADMIN = "ROLE_ADMIN";
		String TRANSLATOR = "ROLE_TRANSLATOR";
    }
	
	public interface FavoriteType {
		String CANDIDATE_JOB = "CANDIDATE_JOB";
		String CANDIDATE_COMPANY = "CANDIDATE_COMPANY";
		String CANDIDATE_TRANSLATOR = "CANDIDATE_TRANSLATOR";
		String COMPANY_CANDIDATE = "COMPANY_CANDIDATE";
		String COMPANY_TRANSLATOR = "COMPANY_TRANSLATOR";
		String TRANSLATOR_CANDIDATE = "TRANSLATOR_CANDIDATE";
		String TRANSLATOR_COMPANY = "TRANSLATOR_COMPANY";
    }
	
	public interface NotificationType {
		String MESSAGE_REQUEST = "MESSAGE_REQUEST";
		String MESSAGE_JOB_APPLICATION_COMPANY_SUPPORT = "MESSAGE_JOB_APPLICATION_COMPANY_SUPPORT";
		String MESSAGE_JOB_APPLICATION_CANDIDATE_SUPPORT = "MESSAGE_JOB_APPLICATION_CANDIDATE_SUPPORT";
		String MESSAGE_JOB_APPLICATION_ALL = "MESSAGE_JOB_APPLICATION_ALL";
		String STATUS_REQUEST = "STATUS_REQUEST";
		String STATUS_JOB_APPLICATION = "STATUS_JOB_APPLICATION";
		String ADMIN = "ADMIN";
    }
	
	public interface NotificationContent {
		String HELPER_JOINED = "HELPER_JOINED";
		String HELPER_CANCEL = "HELPER_CANCEL";
		String HELPER_FINISHED = "HELPER_FINISHED";
		String OWNER_ACCEPTED_APPLY = "OWNER_ACCEPTED_APPLY";
		String OWNER_REJECT_APPLY = "OWNER_REJECT_APPLY";
		String OWNER_ACCEPTED_FINISHED = "OWNER_ACCEPTED_FINISHED";
		String OWNER_REFUSED_FINISHED = "OWNER_REFUSED_FINISHED";
		String OWNER_CANCEL = "OWNER_CANCEL";
		String CANDIDATE_JOINED = "CANDIDATE_JOINED";
		String CANDIDATE_CANCEL = "CANDIDATE_CANCEL";
		String CONPANY_ACCEPTED_APPLY = "CONPANY_ACCEPTED_APPLY";
		String CONPANY_REJECT_APPLY = "CONPANY_REJECT_APPLY";
		String COMPANY_ACCEPTED_CANDIDATE = "COMPANY_ACCEPTED_CANDIDATE";
		String COMPANY_REFUSED_CANDIDATE = "COMPANY_REFUSED_CANDIDATE";
    }
}
