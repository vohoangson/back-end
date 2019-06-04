package com.japanwork.repository.country;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Country;

public interface CountryRepository extends JpaRepository<Country, UUID>{
	public Country findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Country> findAllByIsDelete(boolean isDelete);
}
