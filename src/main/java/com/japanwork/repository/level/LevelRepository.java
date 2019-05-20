package com.japanwork.repository.level;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Level;

public interface LevelRepository extends JpaRepository<Level, UUID> {
	public Level findByIdAndIsDelete(UUID id, boolean isDelete);
}
