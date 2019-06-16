package com.japanwork.repository.conversation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, UUID>{
	
}
