package com.japanwork.repository.level;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Level;

public interface LevelRepository extends JpaRepository<Level, UUID> {
	public Level findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public List<Level> findAllByDeletedAt(Timestamp deletedAt);
}
