package com.japanwork.security;


import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.User;
import com.japanwork.repository.user.UserRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(UUID id) throws ResourceNotFoundException{
        User user = userRepository.findByUid(id);
        if(user == null) {
            throw new ResourceNotFoundException("User not found with id :: " + id);
        }

        return UserPrincipal.create(user);
    }
}