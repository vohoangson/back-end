package com.japanwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Language;
import com.japanwork.service.LanguageService;

@Controller
public class LanguageController {
	@Autowired
	private LanguageService languageService;
	
	
	@GetMapping(UrlConstant.URL_LANGUAGUES)
	@ResponseBody
	public ResponseDataAPI index() {
		List<Language> list = languageService.index();
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
}
