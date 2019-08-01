package com.japanwork.repository.conversation;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Conversation;
import com.japanwork.model.Translator;

public interface ConversationRepository extends JpaRepository<Conversation, UUID>{
	public Conversation findByIdAndDeletedAt(UUID id, Timestamp deletedAt);
	public Set<Conversation> findByCandidateAndDeletedAt(Candidate candidate, Timestamp deletedAt);
	public Set<Conversation> findByCompanyAndDeletedAt(Company company, Timestamp deletedAt);
	public Set<Conversation> findByTranslatorAndDeletedAt(Translator translator, Timestamp deletedAt);
}
