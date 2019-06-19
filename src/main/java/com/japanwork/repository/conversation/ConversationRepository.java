package com.japanwork.repository.conversation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, UUID>{
	public Conversation findByIdAndIsDelete(UUID id, boolean isDelete);
	public List<Conversation> findByTranslatorIdAndIsDelete(UUID id, boolean isDelete);
	public List<Conversation> findByCompanyIdAndIsDelete(UUID id, boolean isDelete);
	public List<Conversation> findByCandidateIdAndIsDelete(UUID id, boolean isDelete);
}
