package com.japanwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.request.LevelRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.LevelService;

@Controller
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	@PostMapping(value = UrlConstant.URL_LEVEL)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody LevelRequest levelRequest) {		
		return levelService.save(levelRequest);
	}
}
