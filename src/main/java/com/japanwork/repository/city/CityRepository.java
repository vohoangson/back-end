package com.japanwork.repository.city;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.City;

public interface CityRepository extends JpaRepository<City, UUID>{
	public City findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<City> findAllByIsDelete(boolean isDelete);
}
