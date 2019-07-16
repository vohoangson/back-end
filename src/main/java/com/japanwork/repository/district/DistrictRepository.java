package com.japanwork.repository.district;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.District;

public interface DistrictRepository extends JpaRepository<District, BigInteger>{
	public District findByUidAndDeletedAt(UUID uid, Timestamp deletedAt);
	public List<District> findAllByDeletedAt(Timestamp deletedAt);
	public List<District> findAllByCityIdAndIsDelete(UUID uid, Timestamp deletedAt);
}
