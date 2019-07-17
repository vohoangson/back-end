package com.japanwork.repository.translator;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Translator;
import com.japanwork.model.User;

public interface TranslatorRepository extends JpaRepository<Translator, BigInteger>{
	public Translator findByUser(User user);
	public Translator findByUserAndDeletedAt(User user, Timestamp deletedAt);
	public Translator findByUidAndDeletedAt(UUID uid, Timestamp deletedAt);
	public Translator findByUid (UUID uid);
	public Page<Translator> findAllByDeletedAt(Pageable page, Timestamp deletedAt);
}
