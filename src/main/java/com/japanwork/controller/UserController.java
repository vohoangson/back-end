package com.japanwork.controller;

import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.User;
import com.japanwork.repository.UserRepository;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    //@PreAuthorize("hasRole('USER')")
    public String getCurrentUser(Principal principal) {
		/*
		 * return userRepository.findById(userPrincipal.getId()) .orElseThrow(() -> new
		 * ResourceNotFoundException("User", "id", userPrincipal.getId()));
		 */
    	
    	return principal.getName();
    }
}
