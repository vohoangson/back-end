package com.japanwork.repository.district;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.District;

public interface DistrictRepository extends JpaRepository<District, UUID>{
	public District findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<District> findAllByDeletedAt(Timestamp deletedAt);
	public List<District> findAllByCityIdAndDeletedAt(UUID id, Timestamp deletedAt);
}
