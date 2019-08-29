package com.japanwork.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.Country;
import com.japanwork.repository.country.CountryRepository;

@Service
public class CountryService {
	@Autowired
	private CountryRepository countryRepository;

	public List<Country> index() {
		List<Country> list = countryRepository.findAllByDeletedAt(null);
		return list;
	}
}
