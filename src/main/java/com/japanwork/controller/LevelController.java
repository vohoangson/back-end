package com.japanwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Level;
import com.japanwork.payload.request.LevelRequest;
import com.japanwork.service.LevelService;

@Controller
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	@GetMapping(UrlConstant.URL_LEVELS)
	@ResponseBody
	public ResponseDataAPI index() {
		List<Level> list = levelService.index();
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, list, null, null);
	}
	
	@PostMapping(value = UrlConstant.URL_LEVELS)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody LevelRequest levelRequest) {		
		Level level = levelService.create(levelRequest);
		return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, level, null, null);
	}
}
