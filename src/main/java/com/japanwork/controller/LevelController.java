package com.japanwork.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.UrlConstant;
import com.japanwork.model.Level;
import com.japanwork.payload.request.LevelRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.LevelService;

@Controller
public class LevelController {
	@Autowired
	private LevelService levelService;
	
	@GetMapping(UrlConstant.URL_LEVELS)
	@ResponseBody
	public BaseDataResponse listLevel() {
		List<Level> list = levelService.findAllByIsDelete();
		return new BaseDataResponse(list);
	}
	
	@PostMapping(value = UrlConstant.URL_LEVELS)
	@ResponseBody
	public BaseDataResponse create(@Valid @RequestBody LevelRequest levelRequest) {		
		Level level = levelService.save(levelRequest);
		return new BaseDataResponse(level);
	}
}
