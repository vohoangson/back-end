package com.japanwork.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.model.User;
import com.japanwork.payload.request.ChangePasswordRequest;
import com.japanwork.payload.request.MailForgetPasswordRequest;
import com.japanwork.payload.request.ResetPasswordRequest;
import com.japanwork.payload.request.SignUpRequest;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.UserService;
import com.japanwork.support.CommonSupport;

@RestController
public class UserController {
    @Autowired
    private CommonSupport commonSupport;

    @Autowired
    private UserService userService;
    
    @GetMapping("/user/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return commonSupport.loadUserById(userPrincipal.getId());
    }

    @PostMapping(value = UrlConstant.URL_REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        return userService.registerUser(signUpRequest, request);
    }

    @GetMapping(value = UrlConstant.URL_USER)
    public ResponseDataAPI getUser(@CurrentUser UserPrincipal userPrincipal) {
    	User user = commonSupport.loadUserById(userPrincipal.getId());
    	user.setRole(user.getRole().replaceAll("ROLE_", ""));
    	return new ResponseDataAPI(
    	        CommonConstant.ResponseDataAPIStatus.SUCCESS,
                user,
                ""
        );
    }

    @GetMapping(value = UrlConstant.URL_PROFILE)
    public ResponseDataAPI getProfile(@PathVariable UUID id) {
    	User user = commonSupport.loadUserById(id);
    
    	return new ResponseDataAPI(
    	        CommonConstant.ResponseDataAPIStatus.SUCCESS,
    	        userService.getProfile(user),
                ""
        );
    }
    
    @PostMapping(value = UrlConstant.URL_USER_CHANGE_PASSWORD)
    public ResponseDataAPI changePassword(@CurrentUser UserPrincipal userPrincipal,
    		@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
    	return userService.changePassword(userPrincipal, changePasswordRequest);
    }

    @PostMapping(value = UrlConstant.URL_USER_FORGET_PASSWORD)
    public ResponseDataAPI forgetPassword(@Valid @RequestBody MailForgetPasswordRequest mailForgetPasswordRequest) {
    	return userService.forgetPassword(mailForgetPasswordRequest);
    }

    @PostMapping(value = UrlConstant.URL_USER_RESET_PASSWORD)
    public ResponseDataAPI resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    	return userService.resetPassword(resetPasswordRequest);
    }
}
