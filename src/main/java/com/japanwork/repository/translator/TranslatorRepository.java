package com.japanwork.repository.translator;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Translator;
import com.japanwork.model.User;

public interface TranslatorRepository extends JpaRepository<Translator, UUID>{
	public Translator findByUser(User user);
	public Translator findByUserAndIsDelete(User user, boolean isDelete);
	public Translator findByIdAndIsDelete(UUID id, boolean isDelete);
	public Page<Translator> findAllByIsDelete(Pageable page, boolean isDelete);
}
