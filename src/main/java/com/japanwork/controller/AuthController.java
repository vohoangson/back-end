package com.japanwork.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.payload.request.ChangePasswordRequest;
import com.japanwork.payload.request.LoginRequest;
import com.japanwork.payload.request.MailForgetPasswordRequest;
import com.japanwork.payload.request.ResetPasswordRequest;
import com.japanwork.payload.request.SignUpRequest;
import com.japanwork.payload.response.AuthResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.ConfirmRegistrationTokenResponse;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.TokenProvider;
import com.japanwork.security.UserPrincipal;
import com.japanwork.service.UserService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private UserService userService;

    @PostMapping(value = UrlConstant.URL_LOGIN)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.createToken(authentication);
            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok(new BaseDataResponse(authResponse));
        } catch(BadCredentialsException e){
            return ResponseEntity.badRequest().body(new BaseMessageResponse(MessageConstant.INVALID_INPUT, 
            		MessageConstant.LOGIN_FAIL));
        }
    }

    @PostMapping(value = UrlConstant.URL_REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.INVALID_INPUT, 
            		MessageConstant.EMAIL_ALREADY);
            return ResponseEntity.badRequest().body(baseMessageResponse);
        }
        return userService.registerUser(signUpRequest, request);
    }
    
    @GetMapping(value = UrlConstant.URL_CONFIRM_ACCOUNT)
    public RedirectView confirmRegistration(@RequestParam("token") final String token) throws ResourceNotFoundException{
    	final String result = userService.validateVerificationToken(token);
    	if (result.equals("valid")) {
            return new RedirectView("http://datvo.io/login");
        } else if(result.equals("expired")) {
            throw new ResourceNotFoundException("Confirm Register Fail! Confirm expired registration.");
        } else {
            throw new ResourceNotFoundException("Confirm Register Fail! Registration confirmation does not exist.");
        }    	
    }
    
    @GetMapping(value = UrlConstant.URL_RESEND_REGISTRATION_TOKEN)
    public ConfirmRegistrationTokenResponse resendRegistrationToken(@RequestParam("token") final String existingToken, 
    		HttpServletRequest request) {
        return userService.resendRegistrationToken(existingToken, request);
    }
    
    @GetMapping(value = UrlConstant.URL_OAUTH2_LOGIN)
    public ResponseEntity<?> oauth2LoginRedirect(@RequestParam("token") String token, @RequestParam("error") String error) {
    	if(!token.isEmpty()) {
    		AuthResponse authResponse = new AuthResponse(token);        	
            return ResponseEntity.ok(new BaseDataResponse(authResponse));
    	}else{
    		BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.INVALID_INPUT, error);
    		return ResponseEntity.badRequest().body(baseMessageResponse);
    	}
    	        
    }
    
    @GetMapping(value = UrlConstant.URL_DELETE_ACCOUNT)
    public BaseDataResponse deleteUserByEmail(@RequestParam("email") String email) {
    	return userService.deleteUserByEmail(email);    
    }
    
    @GetMapping(value = UrlConstant.URL_USER)
    public BaseDataResponse getUser(@CurrentUser UserPrincipal userPrincipal) {
    	return userService.getUser(userPrincipal);    
    }
    
    @PostMapping(value = UrlConstant.URL_USER_CHANGE_PASSWORD)
    public BaseDataResponse changePassword(@CurrentUser UserPrincipal userPrincipal, 
    		@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws Exception{
    	return userService.changePassword(userPrincipal, changePasswordRequest);    
    }
    
    @PostMapping(value = UrlConstant.URL_USER_FORGET_PASSWORD)
    public BaseDataResponse forgetPassword(@Valid @RequestBody MailForgetPasswordRequest mailForgetPasswordRequest) {
    	return userService.forgetPassword(mailForgetPasswordRequest);    
    }
    
    @PostMapping(value = UrlConstant.URL_USER_RESET_PASSWORD)
    public BaseDataResponse resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) 
    		throws Exception{
    	return userService.resetPassword(resetPasswordRequest);    
    }
}
