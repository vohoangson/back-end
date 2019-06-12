package com.japanwork.repository.translator;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Translator;
import com.japanwork.model.User;

public interface TranslatorRepository extends JpaRepository<Translator, UUID>{
	public Translator findByUser(User user); 
	public Translator findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Translator> findAllByIsDelete(boolean isDelete);
}
