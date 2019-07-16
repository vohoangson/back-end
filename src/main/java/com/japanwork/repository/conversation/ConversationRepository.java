package com.japanwork.repository.conversation;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, BigInteger>{
	public Conversation findByUidAndDeletedAt(UUID uid, Timestamp deletedAt);
}
