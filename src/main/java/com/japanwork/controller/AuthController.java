package com.japanwork.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.User;
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
	private RabbitAdmin rabbitAdmin;

	@Autowired 
	private ConnectionFactory connectionFactory;
	
	
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
            return ResponseEntity.badRequest().body(new BaseMessageResponse(MessageConstant.LOGIN_FAIL, 
            		MessageConstant.LOGIN_FAIL_MSG));
        }
    }

    @PostMapping(value = UrlConstant.URL_REGISTER)
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.EMAIL_ALREADY, 
            		MessageConstant.EMAIL_ALREADY_MSG);
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
    		BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.LOGIN_OAUTH2_FAIL, error);
    		return ResponseEntity.badRequest().body(baseMessageResponse);
    	}
    	        
    }
    
//    @GetMapping(value = UrlConstant.URL_OAUTH2_LOGIN)
//    public RedirectView oauth2LoginRedirect(@RequestParam("token") String token, @RequestParam("error") String error) {
//    	if(!token.isEmpty()) {
//	  		return new RedirectView("http://datvo.io/login?access_token="+token+"&token_type=Bearer&expires_in=3600");
//	  	}else{
//	  		return new RedirectView("http://datvo.io/login?error="+error);
//	  	}
//	}
    
    @DeleteMapping(value = UrlConstant.URL_USER)
    public BaseMessageResponse deleteUserByEmail(@RequestParam("email") String email) {
    	return userService.deleteUserByEmail(email);    
    }
    
    @GetMapping(value = UrlConstant.URL_USER)
    public BaseDataResponse getUser(@CurrentUser UserPrincipal userPrincipal) {    	
    	User user = userService.getUser(userPrincipal);
    	user.setRole(user.getRole().replaceAll("ROLE_", ""));
    	return new BaseDataResponse(userService.converUserResponse(user));
    }
    
    @PostMapping(value = UrlConstant.URL_USER_CHANGE_PASSWORD)
    public BaseMessageResponse changePassword(@CurrentUser UserPrincipal userPrincipal, 
    		@Valid @RequestBody ChangePasswordRequest changePasswordRequest){
    	return userService.changePassword(userPrincipal, changePasswordRequest);    
    }
    
    @PostMapping(value = UrlConstant.URL_USER_FORGET_PASSWORD)
    public BaseMessageResponse forgetPassword(@Valid @RequestBody MailForgetPasswordRequest mailForgetPasswordRequest) {
    	return userService.forgetPassword(mailForgetPasswordRequest);    
    }
    
    @PostMapping(value = UrlConstant.URL_USER_RESET_PASSWORD)
    public BaseMessageResponse resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
    	return userService.resetPassword(resetPasswordRequest);    
    }
    
    @GetMapping(value = UrlConstant.URL_NOTIFICATIONS_ENDPOINT)
    public BaseDataResponse websocket(@CurrentUser UserPrincipal userPrincipal) {
    	rabbitAdmin = new RabbitAdmin(connectionFactory);
    	String exchangeName = "notifications/"+userPrincipal.getId();
    	String queueName = CommonFunction.generateCode(8);
        rabbitAdmin.declareExchange(new DirectExchange(exchangeName, false, true));
        rabbitAdmin.declareQueue(new Queue(queueName, false, false, true));
        rabbitAdmin.declareBinding(
            new Binding(
        		queueName,
                Binding.DestinationType.QUEUE,
                exchangeName,
                ""+userPrincipal.getId(),
                null));
        return new BaseDataResponse(queueName);
    }
}
