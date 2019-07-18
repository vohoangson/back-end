package com.japanwork.repository.city;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.City;

public interface CityRepository extends JpaRepository<City, UUID>{
	public City findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<City> findAllByDeletedAt(Timestamp deletedAt);
	public List<City> findAllByCountryCodeAndDeletedAt(String code, Timestamp deletedAt);
}
