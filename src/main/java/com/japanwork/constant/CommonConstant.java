package com.japanwork.constant;

public class CommonConstant {
	public interface StatusTranslate {
        int UNTRANSLATED = 1;
        int TRANSLATING = 2;
        int TRANSLATION_COMPLETED = 3;
        int TRANSLATION_NOT_COMPLETED = 4;
    }
	
	public interface StatusApplyJob {
        int WAITING_FOR_COMPANY_APPROVE = 1;
        int WAITING_FOR_TRANSLATOR_JOIN = 2;
        int ON_GOING = 3;
        int APPLICATION_SUCCEED = 4;
        int APPLICATION_CANCELED = 5;
    }
}
