package com.japanwork.repository.user;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.User;

public interface UserRepository extends JpaRepository<User, BigInteger> {

    public Optional<User> findByEmail(String email);

    public Boolean existsByEmail(String email);
    
    public User findByUidAndDeletedAt(UUID uid, Timestamp isDelete);
    
    public User findByUid(UUID uid);

}
