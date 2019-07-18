package com.japanwork.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.PageInfo;
import com.japanwork.model.Translator;
import com.japanwork.payload.request.TranslatorRequest;
import com.japanwork.payload.response.BaseDataMetaResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.TranslatorResponse;
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
			throw new BadRequestException(MessageConstant.TRANSLATOR_ALREADY, MessageConstant.TRANSLATOR_ALREADY_MSG);
		}
		
		Translator translator = translatorService.save(translatorRequest, userPrincipal);
		return new BaseDataResponse(translatorService.convertTranslatorResponse(translator));
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATOR)
	@ResponseBody
	public BaseDataMetaResponse listTranslator (@RequestParam(defaultValue = "1", name = "page") int page, 
			@RequestParam(defaultValue = "25", name = "paging") int paging){
		Page<Translator> pages = translatorService.findAllByIsDelete(page, paging);
		
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<TranslatorResponse> list = new ArrayList<TranslatorResponse>();
		
		if(pages.getContent().size() > 0) {
			for (Translator translator : pages.getContent()) {
				list.add(translatorService.convertTranslatorResponse(translator));
			}
		}
		
		return new BaseDataMetaResponse(list, pageInfo);
	}
	
	@PatchMapping(UrlConstant.URL_TRANSLATOR_ID)
	@ResponseBody
	public BaseDataResponse updateTranslator(@Valid @RequestBody TranslatorRequest translatorRequest, 
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		Translator translator = translatorService.update(translatorRequest, id, userPrincipal);
		return new BaseDataResponse(translatorService.convertTranslatorResponse(translator));
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATOR_ID)
	@ResponseBody
	public BaseDataResponse findTranslatorByIdAndIsDelete(@PathVariable UUID id){		
		Translator translator = translatorService.findByIdAndIsDelete(id);
		return new BaseDataResponse(translatorService.convertTranslatorResponse(translator));
	}
	
	@GetMapping(UrlConstant.URL_MY_TRANSLATOR)
	@ResponseBody
	public BaseDataResponse myTranslator(@CurrentUser UserPrincipal userPrincipal){		
		Translator translator = translatorService.myTranslator(userPrincipal);
		return new BaseDataResponse(translatorService.convertTranslatorResponse(translator));
	}
	
	@DeleteMapping(UrlConstant.URL_TRANSLATOR_ID)
	@ResponseBody
	public BaseDataResponse del(@PathVariable UUID id) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		translatorService.isDel(id, timestamp);
		BaseMessageResponse deleteResponse = new BaseMessageResponse(MessageConstant.TRANSLATOR_DELETE_SUCCESS, MessageConstant.TRANSLATOR_DELETE_SUCCESS_MSG);
		return new BaseDataResponse(deleteResponse);
	}
	
	@GetMapping(UrlConstant.URL_TRANSLATOR_UNDEL_ID)
	@ResponseBody
	public BaseDataResponse unDel(@PathVariable UUID id) {		
		Translator translator = translatorService.isDel(id, null);
		return new BaseDataResponse(translatorService.convertTranslatorResponse(translator));
	}
}
