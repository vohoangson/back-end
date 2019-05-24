package com.japanwork.repository.currency_unit;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.CurrencyUnit;

public interface CurrencyUnitRepository extends JpaRepository<CurrencyUnit, UUID>{
	public CurrencyUnit findByIdAndIsDelete(UUID id, boolean isDelete);
}
