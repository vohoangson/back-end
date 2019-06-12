package com.japanwork.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.payload.request.TranslatorRequest;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.TranslatorService;
import com.japanwork.service.UserService;

@Controller
public class TranslatorController {
	@Autowired
	private TranslatorService translatorService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(UrlConstant.URL_TRANSLATOR)
	@ResponseBody
	public BaseDataResponse createTranslator (@Valid @RequestBody TranslatorRequest translatorRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		if(translatorService.checkTranslatorByUser(userService.findById(userPrincipal.getId()))) {
			throw new BadRequestException(MessageConstant.TRANSLATOR_ALREADY);
		}
		
		return translatorService.save(translatorRequest, userPrincipal);
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATOR)
	@ResponseBody
	public BaseDataResponse listTranslator (){
		return translatorService.findAllByIsDelete();
	}
	
	@PatchMapping(UrlConstant.URL_TRANSLATOR_ID)
	@ResponseBody
	public BaseDataResponse updateTranslator(@Valid @RequestBody TranslatorRequest translatorRequest, 
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		return translatorService.update(translatorRequest, id, userPrincipal);
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATOR_ID)
	@ResponseBody
	public BaseDataResponse findTranslatorByIdAndIsDelete(@PathVariable UUID id){		
		return translatorService.findByIdAndIsDelete(id);
	}
	
	@DeleteMapping(UrlConstant.URL_TRANSLATOR_ID)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {		
		return translatorService.isDel(id, true);
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATOR_UNDEL_ID)
	@ResponseBody
	public BaseDataResponse unDel(@PathVariable UUID id) {		
		return translatorService.isDel(id, false);
	}
}
