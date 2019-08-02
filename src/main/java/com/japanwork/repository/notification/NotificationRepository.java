package com.japanwork.repository.notification;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.japanwork.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID>{
	public Page<Notification> findByObjectableIdAndDeletedAt(Pageable page, UUID id, Timestamp deletedAt);
	public Page<Notification> findByReceiverIdAndDeletedAt(Pageable page, UUID id, Timestamp deletedAt);
	public int countByReceiverIdAndIsReadAndDeletedAt(UUID id, boolean isRead, Timestamp deletedAt);
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Notification n SET n.isRead = :isRead WHERE n.id in :ids AND n.deletedAt is null")
	public void updateIsReadByIdInAndDeletedAt(@Param("isRead") boolean isRead, @Param("ids") Set<Long> ids);
	
	
	@Modifying(clearAutomatically = true)
    @Query("UPDATE Notification n SET n.isRead = :isRead WHERE n.receiverId = :receiverId AND n.deletedAt is null")
	public void updateIsReadByReceiverIdAndDeletedAt(@Param("isRead") boolean isRead, @Param("receiverId") UUID receiverId);
	
}
