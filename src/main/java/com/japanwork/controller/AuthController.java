package com.japanwork.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japanwork.constant.EmailConstants;
import com.japanwork.exception.BadRequestException;
import com.japanwork.model.AuthProvider;
import com.japanwork.model.User;
import com.japanwork.model.VerificationToken;
import com.japanwork.payload.ApiResponse;
import com.japanwork.payload.AuthResponse;
import com.japanwork.payload.ConfirmRegistrationTokenResponse;
import com.japanwork.payload.LoginRequest;
import com.japanwork.payload.SignUpRequest;
import com.japanwork.repository.UserRepository;
import com.japanwork.security.TokenProvider;
import com.japanwork.service.EmailSenderService;
import com.japanwork.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private EmailSenderService emailSenderService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	
//    	User user = userService.findUserByEmail(loginRequest.getEmail());
//    	if(user == null || !user.getEmailVerified()) {
//    		throw new BadRequestException("Account has not been verified.");
//    	}
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(signUpRequest.getRole());
        User result = userRepository.save(user);
        
        final VerificationToken newToken = userService.generateNewVerificationToken(user);
        this.sendEmail(user, newToken.getToken());
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }
    
    @GetMapping("/confirm-account")
    @ResponseBody
    public ConfirmRegistrationTokenResponse confirmRegistration(@RequestParam("token") final String token) {
    	final String result = userService.validateVerificationToken(token);
    	if (result.equals("valid")) {
    		ConfirmRegistrationTokenResponse api = new ConfirmRegistrationTokenResponse("Confirm Register Success!","");
            return api;
        } else if(result.equals("expired")) {
        	ConfirmRegistrationTokenResponse api = new ConfirmRegistrationTokenResponse("Confirm Register Fail! Confirm expired registration.", token);
            return api;
        } else {
        	ConfirmRegistrationTokenResponse api = new ConfirmRegistrationTokenResponse("Confirm Register Fail! Registration confirmation does not exist.", token);
            return api;
        }
    	
    }
    
    @RequestMapping(value = "/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public ConfirmRegistrationTokenResponse resendRegistrationToken(@RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUser(newToken.getToken());
        
        this.sendEmail(user, newToken.getToken());
        
        ConfirmRegistrationTokenResponse api = new ConfirmRegistrationTokenResponse("Please confirm your email!","");
        return api;
    }
    
    private void sendEmail(User user, String token) {    	
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(EmailConstants.MY_EMAIL);
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8080/auth/confirm-account?token="+token);
        emailSenderService.sendEmail(mailMessage);
    }
    
}
