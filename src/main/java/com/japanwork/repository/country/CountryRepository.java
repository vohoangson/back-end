package com.japanwork.repository.country;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.japanwork.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID>{
	public List<Country> findAllByDeletedAt(Timestamp deletedAt);
}
