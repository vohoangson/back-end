package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.City;
import com.japanwork.model.District;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
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
	
	public boolean checkTranslatorByUser(User user){
		Translator translator = translatorRepository.findByUser(user);
		if(translator == null) {
			return false;
		}
		return true;
	}
	
	public Translator save(TranslatorRequest translatorRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
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
		translator.setDeletedAt(null);
		
		Translator result = translatorRepository.save(translator);		
		userService.changePropertyId(userPrincipal.getId(), result.getId());
		return result;
	}
	
	public Translator update(TranslatorRequest translatorRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Translator translator = new Translator();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_TRANSLATOR")) {
			translator = translatorRepository.findByIdAndDeletedAt(id, null);
			if(translator == null) {
				throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
			}
			if(!translator.getUser().getId().equals(userPrincipal.getId())) {
				throw new ForbiddenException(MessageConstant.ERROR_403_MSG);
			}
		} else {
			translator = translatorRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
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
		translator.setUpdatedAt(timestamp);
		
		Translator result = translatorRepository.save(translator);		
		return result;
	}
	
	public Translator myTranslator(UserPrincipal userPrincipal) throws ResourceNotFoundException{
		Translator translator = translatorRepository.findByUserAndDeletedAt(userService.findById(userPrincipal.getId()), null);
		if(translator == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		return translator;
	}
	
	public Translator findByIdAndIsDelete(UUID id) 
			throws ResourceNotFoundException{
		Translator translator = translatorRepository.findByIdAndDeletedAt(id, null);
		if(translator == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}	
		return translator;
	}
	
	public Page<Translator> findAllByIsDelete(int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Translator> pages = translatorRepository.findAllByDeletedAt(PageRequest.of(page-1, paging), null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Page<Translator> translatorsByIds(Set<UUID> ids, int page, int paging) throws ResourceNotFoundException{
		try {
			Page<Translator> pages = translatorRepository.findAllByIdInAndDeletedAt(PageRequest.of(page-1, paging), ids,null);
			return pages;
		} catch (IllegalArgumentException e) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
	}
	
	public Translator isDel(UUID id, Timestamp deletedAt) throws ResourceNotFoundException{
		try {
		Translator translator = translatorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
		translator.setDeletedAt(deletedAt);
		translatorRepository.save(translator);
		
		Translator result = translatorRepository.findByIdAndDeletedAt(id, null);
		return result;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			if(deletedAt != null) {
				throw new ServerError(MessageConstant.TRANSLATOR_DELETE_FAIL);
			} else {
				throw new ServerError(MessageConstant.TRANSLATOR_UN_DELETE_FAIL);
			}
			
		}
	}
	
	public Translator findTranslatorByUser(User user){
		Translator translator = translatorRepository.findByUser(user);
		return translator;
	}
	
	public Translator findTranslatorByIdAndIsDelete(UUID id, boolean isDel){
		Translator translator = translatorRepository.findByIdAndDeletedAt(id, null);
		return translator;
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
