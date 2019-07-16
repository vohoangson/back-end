package com.japanwork.repository.level;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Level;

public interface LevelRepository extends JpaRepository<Level, BigInteger> {
	public Level findByUidAndDeletedAt(UUID uid, Timestamp deletedAt);
	public List<Level> findAllByDeletedAt(Timestamp deletedAt);
}
