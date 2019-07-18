package com.japanwork.repository.user;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByEmail(String email);

    public Boolean existsByEmail(String email);
    
    public User findByIdAndDeletedAt(UUID id, Timestamp deletedAt);

}
