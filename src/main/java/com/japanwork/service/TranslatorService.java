package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.City;
import com.japanwork.model.District;
import com.japanwork.model.Translator;
import com.japanwork.payload.request.TranslatorRequest;
import com.japanwork.payload.response.TranslatorResponse;
import com.japanwork.repository.translator.TranslatorRepository;
import com.japanwork.security.UserPrincipal;

@Service
public class TranslatorService {
	@Autowired
	private TranslatorRepository translatorRepository;

	@Autowired
	private UserService userService;

	public Translator create(TranslatorRequest translatorRequest, UserPrincipal userPrincipal) {
	    Timestamp timestamp = CommonFunction.getCurrentDateTime();

		Translator translator = new Translator();
		translator.setUser(userService.findById(userPrincipal.getId()));
		translator.setName(translatorRequest.getName());
		translator.setDateOfBirth(translatorRequest.getDateOfBirth());
		translator.setGender(translatorRequest.getGender());
		translator.setCity(new City(translatorRequest.getCityId()));
		translator.setDistrict(new District(translatorRequest.getDistrictId()));
		translator.setAddress(translatorRequest.getAddress());
		translator.setAvatar(translatorRequest.getAvatar());
		translator.setIntroduction(translatorRequest.getIntroduction());
		translator.setJapaneseLevel(translatorRequest.getJapaneseLevel());
		translator.setCreatedAt(timestamp);
		translator.setUpdatedAt(timestamp);

		Translator result = translatorRepository.save(translator);
		userService.changePropertyId(userPrincipal.getId(), result.getId());
		return result;
	}

	public Translator update(TranslatorRequest translatorRequest, Translator translator, UserPrincipal userPrincipal)
			throws ForbiddenException{
		if(!translator.getUser().getId().equals(userPrincipal.getId())) {
			throw new ForbiddenException(MessageConstant.FORBIDDEN_ERROR);
		}
		translator.setName(translatorRequest.getName());
		translator.setDateOfBirth(translatorRequest.getDateOfBirth());
		translator.setGender(translatorRequest.getGender());
		translator.setCity(new City(translatorRequest.getCityId()));
		translator.setDistrict(new District(translatorRequest.getDistrictId()));
		translator.setAddress(translatorRequest.getAddress());
		translator.setAvatar(translatorRequest.getAvatar());
		translator.setIntroduction(translatorRequest.getIntroduction());
		translator.setJapaneseLevel(translatorRequest.getJapaneseLevel());
		translator.setUpdatedAt(CommonFunction.getCurrentDateTime());

		Translator result = translatorRepository.save(translator);
		return result;
	}

	public Page<Translator> index(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Translator> pages = translatorRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
	}

	public Page<Translator> translatorsByIds(Set<UUID> ids, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Translator> pages = translatorRepository.findAllByIdInAndDeletedAt(PageRequest.of(page-1, paging), ids,null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.PAGE_NOT_FOUND);
		}
	}

	public Translator isDel(UUID id, Timestamp deletedAt){
		try {
		Translator translator = translatorRepository.findById(id).get();
		translator.setDeletedAt(deletedAt);
		translatorRepository.save(translator);

		Translator result = translatorRepository.findByIdAndDeletedAt(id, null);
		return result;
		} catch (Exception e) {
			if(deletedAt != null) {
				throw new ServerError(MessageConstant.TRANSLATOR_DELETE_FAIL);
			} else {
				throw new ServerError(MessageConstant.TRANSLATOR_UNDELETE_FAIL);
			}

		}
	}

	public TranslatorResponse convertTranslatorResponse(Translator translator) {
		TranslatorResponse translatorResponse = new TranslatorResponse(
				translator.getId(),
				translator.getName(),
				translator.getGender(),
				translator.getDateOfBirth(),
				translator.getCity().getId(),
				translator.getDistrict().getId(),
				translator.getAddress(),
				translator.getIntroduction(),
				translator.getAvatar(),
				translator.getJapaneseLevel());
		return translatorResponse;
	}
}
