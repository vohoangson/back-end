package com.japanwork.repository.message;

import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Conversation;
import com.japanwork.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
	public Page<Message> findByConversationAndDeletedAt(Pageable page, Conversation conversation, Timestamp deletedAt);
}
