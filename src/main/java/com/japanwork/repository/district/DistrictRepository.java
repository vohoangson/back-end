package com.japanwork.repository.district;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.District;

public interface DistrictRepository extends JpaRepository<District, BigInteger>{
	public District findByUidAndDeletedAt(UUID id, Timestamp isDelete);
	public List<District> findAllByDeletedAt(Timestamp isDelete);
	public List<District> findAllByCityUidAndDeletedAt(UUID id, Timestamp isDelete);
}
