package com.japanwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.User;
import com.japanwork.repository.user.UserRepository;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) throws ResourceNotFoundException{
    	User user = userRepository.findByUid(userPrincipal.getId());
    	if(user == null) {
    		throw new ResourceNotFoundException("User not found for this id ::"+ userPrincipal.getId());
    	}
        return user;
    }
}
