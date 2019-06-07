package com.japanwork.service;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japanwork.constant.EmailConstants;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.model.AuthProvider;
import com.japanwork.model.ForgetPassword;
import com.japanwork.model.User;
import com.japanwork.model.VerificationToken;
import com.japanwork.payload.request.ChangePasswordRequest;
import com.japanwork.payload.request.MailForgetPasswordRequest;
import com.japanwork.payload.request.ResetPasswordRequest;
import com.japanwork.payload.request.SignUpRequest;
import com.japanwork.payload.response.ApiResponse;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.payload.response.ConfirmRegistrationTokenResponse;
import com.japanwork.repository.forget_password.ForgetPasswordRepository;
import com.japanwork.repository.token.VerificationTokenRepository;
import com.japanwork.repository.user.UserRepository;
import com.japanwork.security.CurrentUser;
import com.japanwork.security.UserPrincipal;

@Service
public class UserService {
	
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;
    
    @Autowired
    private EmailSenderService emailSenderService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    
	public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime()
            - cal.getTime()
                .getTime()) <= 0) {
            return TOKEN_EXPIRED;
        }

        user.setIsEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }
	
	public BaseDataResponse getUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
        		.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
        BaseDataResponse response = new BaseDataResponse(user);
        return response;
    }
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public ResponseEntity<?> registerUser(SignUpRequest signUpRequest, HttpServletRequest request){
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
        
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_"+signUpRequest.getRole());
        user.setCreateDate(timestamp);
        user.setUpdateDate(timestamp);
        user.setDelete(false);
        User result = userRepository.save(user);
        
        final VerificationToken newToken = this.generateNewVerificationToken(user);
        
        String content = "To confirm your account, please click here : "
                +request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath())+UrlConstant.URL_CONFIRM_ACCOUNT+"?token="+newToken.getToken();
        this.sendEmail(user.getEmail(), "Complete Registration!", content);
        
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new BaseDataResponse(new ApiResponse(true, MessageConstant.REGISTER_SUCCESS)));
	}
	
	public ConfirmRegistrationTokenResponse resendRegistrationToken(String existingToken, HttpServletRequest request) {
		final VerificationToken newToken = this.generateNewVerificationToken(existingToken);
        final User user = this.getUser(newToken.getToken());
        String content = "To confirm your account, please click here : "
                +request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath())+UrlConstant.URL_CONFIRM_ACCOUNT+"?token="+newToken.getToken();
        this.sendEmail(user.getEmail(), "Complete Registration!", content);
        
        ConfirmRegistrationTokenResponse api = new ConfirmRegistrationTokenResponse("Please confirm your email!","");
        return api;
	}
	
	public User findById(UUID id) {
		return userRepository.findById(id).get();
	}
	
	public BaseDataResponse deleteUserByEmail(String email) {
		userRepository.delete(this.findUserByEmail(email));
		BaseMessageResponse message;
		if(this.findUserByEmail(email) == null) {
			message = new BaseMessageResponse("Delete user by email:" + email, "Success!");
		} else {
			message = new BaseMessageResponse("Delete user by email:" + email, "Fail!");
		}
		BaseDataResponse response = new BaseDataResponse(message);
		return response;
	}
	
	public BaseDataResponse changePassword(@CurrentUser UserPrincipal userPrincipal, ChangePasswordRequest changePasswordRequest) {
		boolean checkOldPassword = BCrypt.checkpw(changePasswordRequest.getOldPassword(), userPrincipal.getPassword());
		if(checkOldPassword) {
			User user = userRepository.findById(userPrincipal.getId())
	        		.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404));
			user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
			
			User result = userRepository.save(user);
			BaseDataResponse response;
			if(result == null) {
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.CHANGE_PASSWORD, MessageConstant.CHANGE_PASSWORD_FAIL);
				response = new BaseDataResponse(message);
		        return response;
			} else {
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.CHANGE_PASSWORD, MessageConstant.CHANGE_PASSWORD_SUCCESS);
				response = new BaseDataResponse(message);
		        return response;
			}
			
		} else {
			BaseMessageResponse message = new BaseMessageResponse(MessageConstant.CHANGE_PASSWORD, MessageConstant.CHANGE_PASSWORD_NOT_CORRECT);
			BaseDataResponse response = new BaseDataResponse(message);
	        return response;
		}
	}
	
	public BaseDataResponse forgetPassword(MailForgetPasswordRequest mailForgetPasswordRequest){
		User user = this.findUserByEmail(mailForgetPasswordRequest.getEmail());
		if(user == null) {
			BaseMessageResponse message = new BaseMessageResponse(MessageConstant.FORGET_PASSWORD, MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST);
			BaseDataResponse response = new BaseDataResponse(message);
	        return response;
		} else {
			String code = generateCode();
			
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			ForgetPassword forgetPassword = new ForgetPassword();
			forgetPassword.setCode(code);
			forgetPassword.setUser(user);
			forgetPassword.setCreate_date(timestamp);
			
			ForgetPassword result = forgetPasswordRepository.save(forgetPassword);
			if(result != null) {
				this.sendEmail(user.getEmail(), "Reset the password!", "Confirmation code is: " + code);
			}
			
			BaseMessageResponse message = new BaseMessageResponse(MessageConstant.FORGET_PASSWORD, MessageConstant.RESET_FORGET_PASSWORD_SUCCESS);
			BaseDataResponse response = new BaseDataResponse(message);
	        return response;
		}
	}
	
	public BaseDataResponse resetPassword(ResetPasswordRequest resetPasswordRequest){
		User user = this.findUserByEmail(resetPasswordRequest.getEmail());
		if(user == null) {
			BaseMessageResponse message = new BaseMessageResponse(MessageConstant.RESET_PASSWORD, MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST);
			BaseDataResponse response = new BaseDataResponse(message);
	        return response;
		} else {
			forgetPasswordRepository.delete(forgetPasswordRepository.findByUserId(user.getId()));
			
			user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));	
			User result = userRepository.save(user);
			
			BaseDataResponse response;
			if(result == null) {
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.RESET_PASSWORD, MessageConstant.RESET_PASSWORD_FAIL);
				response = new BaseDataResponse(message);
		        return response;
			} else {
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.RESET_PASSWORD, MessageConstant.RESET_PASSWORD_SUCCESS);
				response = new BaseDataResponse(message);
		        return response;
			}
		}
	}
	
	private User findUserByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
	
	private String generateCode() {	 
	    List<CharacterRule> rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1));

		PasswordGenerator generator = new PasswordGenerator();
		String code = generator.generatePassword(6, rules);
		return code;
	}
	
	private void sendEmail(String to, String subject, String content) {
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setFrom(EmailConstants.MY_EMAIL);
        mailMessage.setText(content);
        emailSenderService.sendEmail(mailMessage);
    }
	
	private VerificationToken generateNewVerificationToken(User user) {
        VerificationToken vToken = new VerificationToken(UUID.randomUUID().toString(), user);
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
	
	private VerificationToken generateNewVerificationToken(String token) {
		VerificationToken vToken = tokenRepository.findByToken(token);
        vToken.updateToken(UUID.randomUUID()
            .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
	
	private User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }
}
