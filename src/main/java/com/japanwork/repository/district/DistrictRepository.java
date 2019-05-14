package com.japanwork.repository.district;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.District;

public interface DistrictRepository extends JpaRepository<District, UUID>{

}
