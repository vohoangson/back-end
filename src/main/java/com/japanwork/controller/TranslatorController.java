package com.japanwork.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.PageInfo;
import com.japanwork.model.Translator;
import com.japanwork.payload.request.TranslatorRequest;
import com.japanwork.payload.response.TranslatorResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.TranslatorService;
import com.japanwork.support.CommonSupport;

@Controller
public class TranslatorController {
	@Autowired
	private TranslatorService translatorService;

	@Autowired
    private CommonSupport commonSupport;

	@PostMapping(UrlConstant.URL_TRANSLATORS)
	@ResponseBody
	public ResponseDataAPI create(@Valid @RequestBody TranslatorRequest translatorRequest,
			@CurrentUser UserPrincipal userPrincipal) throws BadRequestException{
		Translator translator = translatorService.create(translatorRequest, userPrincipal);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				translatorService.convertTranslatorResponse(translator),
				""
        );
	}

	@GetMapping(UrlConstant.URL_TRANSLATORS)
	@ResponseBody
	public ResponseDataAPI index(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging){
		Page<Translator> pages = translatorService.index(page, paging);

		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<TranslatorResponse> list = new ArrayList<TranslatorResponse>();

		if(pages.getContent().size() > 0) {
			for (Translator translator : pages.getContent()) {
				list.add(translatorService.convertTranslatorResponse(translator));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	@GetMapping(UrlConstant.URL_TRANSLATOR_IDS)
	@ResponseBody
	public ResponseDataAPI listTranslatorByIds(@RequestParam(defaultValue = "1", name = "page") int page,
			@RequestParam(defaultValue = "25", name = "paging") int paging,
			@RequestParam(name = "ids") Set<UUID> ids) {

		Page<Translator> pages = translatorService.translatorsByIds(ids, page, paging);
		PageInfo pageInfo = new PageInfo(page, pages.getTotalPages(), pages.getTotalElements());
		List<TranslatorResponse> list = new ArrayList<TranslatorResponse>();

		if(pages.getContent().size() > 0) {
			for (Translator translator : pages.getContent()) {
				list.add(translatorService.convertTranslatorResponse(translator));
			}
		}

		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				list,
				pageInfo
        );
	}

	@PatchMapping(UrlConstant.URL_TRANSLATOR)
	@ResponseBody
	public ResponseDataAPI updateTranslator(@Valid @RequestBody TranslatorRequest translatorRequest,
			@PathVariable UUID id, @CurrentUser UserPrincipal userPrincipal){
		Translator translator = commonSupport.loadTranslatorById(id);
		translator = translatorService.update(translatorRequest, translator, userPrincipal);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				translatorService.convertTranslatorResponse(translator),
				""
        );
	}

	@GetMapping(UrlConstant.URL_TRANSLATOR)
	@ResponseBody
	public ResponseDataAPI findTranslatorByIdAndIsDelete(@PathVariable UUID id){
		Translator translator = commonSupport.loadTranslatorById(id);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				translatorService.convertTranslatorResponse(translator),
				""
        );
	}

	@GetMapping(UrlConstant.URL_MY_TRANSLATOR)
	@ResponseBody
	public ResponseDataAPI myTranslator(@CurrentUser UserPrincipal userPrincipal){
		Translator translator = commonSupport.loadTranslatorByUser(userPrincipal.getId());
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				translatorService.convertTranslatorResponse(translator),
				""
        );
	}

	@DeleteMapping(UrlConstant.URL_TRANSLATOR)
	@ResponseBody
	public ResponseDataAPI del(@PathVariable UUID id) {
		translatorService.isDel(id, CommonFunction.getCurrentDateTime());
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				"",
				""
        );
	}

	@GetMapping(UrlConstant.URL_TRANSLATORS_UNDEL)
	@ResponseBody
	public ResponseDataAPI unDel(@PathVariable UUID id) {
		Translator translator = translatorService.isDel(id, null);
		return new ResponseDataAPI(
				CommonConstant.ResponseDataAPIStatus.SUCCESS,
				translatorService.convertTranslatorResponse(translator),
				""
        );
	}
}
