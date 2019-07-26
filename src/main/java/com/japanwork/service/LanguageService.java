package com.japanwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.Language;
import com.japanwork.repository.language.LanguageRepository;

@Service
public class LanguageService {
	@Autowired
	private LanguageRepository languageRepository;
	
	public List<Language> findAllByIsDelete() {
		List<Language> list = languageRepository.findAllByDeletedAt(null);
		return list;
	}
}
