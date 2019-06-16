package com.japanwork.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.constant.MessageConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ForbiddenException;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.payload.request.TranslatorRequest;
import com.japanwork.payload.response.BaseDataResponse;
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
	
	public BaseDataResponse save(TranslatorRequest translatorRequest, UserPrincipal userPrincipal) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Translator translator = new Translator();
		translator.setUser(userService.findById(userPrincipal.getId()));
		translator.setName(translatorRequest.getName());
		translator.setDateOfBirth(translatorRequest.getDateOfBirth());
		translator.setGender(translatorRequest.getGender());
		translator.setCity(translatorRequest.getCity());
		translator.setDistrict(translatorRequest.getDistrict());
		translator.setAddress(translatorRequest.getAddress());
		translator.setAvatar(translatorRequest.getAvatar());
		translator.setIntroduction(translatorRequest.getIntroduction());
		translator.setJapaneseLevel(translatorRequest.getJapaneseLevel());
		translator.setCreateDate(timestamp);
		translator.setUpdateDate(timestamp);
		translator.setDelete(false);
		
		Translator result = translatorRepository.save(translator);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse update(TranslatorRequest translatorRequest, UUID id, UserPrincipal userPrincipal) 
			throws ResourceNotFoundException{
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		Translator translator = new Translator();
		
		if(userService.findById(userPrincipal.getId()).getRole().equals("ROLE_TRANSLATOR")) {
			translator = translatorRepository.findByIdAndIsDelete(id, false);
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
		translator.setCity(translatorRequest.getCity());
		translator.setDistrict(translatorRequest.getDistrict());
		translator.setAddress(translatorRequest.getAddress());
		translator.setAvatar(translatorRequest.getAvatar());
		translator.setIntroduction(translatorRequest.getIntroduction());
		translator.setJapaneseLevel(translatorRequest.getJapaneseLevel());
		translator.setUpdateDate(timestamp);
		
		Translator result = translatorRepository.save(translator);
		BaseDataResponse response = new BaseDataResponse(result);		
		return response;
	}
	
	public BaseDataResponse findByIdAndIsDelete(UUID id) 
			throws ResourceNotFoundException{
		Translator translator = translatorRepository.findByIdAndIsDelete(id, false);
		if(translator == null) {
			throw new ResourceNotFoundException(MessageConstant.ERROR_404_MSG);
		}
		
		BaseDataResponse response = new BaseDataResponse(translator);	
		return response;
	}
	
	public BaseDataResponse findAllByIsDelete(){
		List<Translator> translators = translatorRepository.findAllByIsDelete(false);

		BaseDataResponse response = new BaseDataResponse(translators);	
		return response;
	}
	
	public BaseDataResponse isDel(UUID id, boolean isDel) throws ResourceNotFoundException{
		Translator translator = translatorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
		translator.setDelete(isDel);
		translatorRepository.save(translator);
		
		Translator result = translatorRepository.findByIdAndIsDelete(id, false);
		return new BaseDataResponse(result);
	}
	
	public Translator findTranslatorByUser(User user){
		Translator translator = translatorRepository.findByUser(user);
		return translator;
	}
}
