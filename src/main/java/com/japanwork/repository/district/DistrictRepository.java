package com.japanwork.repository.district;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.District;

public interface DistrictRepository extends JpaRepository<District, UUID>{
	public District findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<District> findAllByIsDelete(boolean isDelete);
}
