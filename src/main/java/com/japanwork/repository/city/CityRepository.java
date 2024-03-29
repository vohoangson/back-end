package com.japanwork.repository.city;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.City;

public interface CityRepository extends JpaRepository<City, BigInteger>{
	public City findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<City> findAllByDeletedAt(Timestamp isDelete);
	public List<City> findAllByCountryCodeAndDeletedAt(String code, Timestamp isDelete);
}
