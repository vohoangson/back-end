package com.japanwork.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.EmailConstants;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.controller.ResponseDataAPI;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.AuthProvider;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Country;
import com.japanwork.model.ForgetPassword;
import com.japanwork.model.Translator;
import com.japanwork.model.User;
import com.japanwork.model.VerificationToken;
import com.japanwork.payload.request.ChangePasswordRequest;
import com.japanwork.payload.request.MailForgetPasswordRequest;
import com.japanwork.payload.request.ResetPasswordRequest;
import com.japanwork.payload.request.SignUpRequest;
import com.japanwork.payload.response.ConfirmRegistrationTokenResponse;
import com.japanwork.payload.response.ProfileResponse;
import com.japanwork.repository.forget_password.ForgetPasswordRepository;
import com.japanwork.repository.token.VerificationTokenRepository;
import com.japanwork.repository.user.UserRepository;
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

	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public ResponseEntity<?> registerUser(SignUpRequest signUpRequest, HttpServletRequest request) throws ServerError{
		try {
	        User user = new User();
	        user.setName(signUpRequest.getName());
	        user.setEmail(signUpRequest.getEmail());
	        user.setPassword(signUpRequest.getPassword());
	        user.setProvider(AuthProvider.local);
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setRole("ROLE_"+signUpRequest.getRole());
	        user.setProviderId(null);
	        user.setCountry(new Country(signUpRequest.getCountryId()));
	        user.setCreatedAt(CommonFunction.getCurrentDateTime());
	        user.setUpdatedAt(null);
	        user.setDeletedAt(null);
	        User result = userRepository.save(user);

	        final VerificationToken newToken = this.generateNewVerificationToken(user);

	        String content = "To confirm your account, please click here : "
	                +request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath())+UrlConstant.URL_CONFIRM_ACCOUNT+"?token="+newToken.getToken();
	        this.sendEmail(user.getEmail(), "Complete Registration!", content);


	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/user/me")
	                .buildAndExpand(result.getId()).toUri();

	        return ResponseEntity.created(location)
	                .body(new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, "", ""));
		} catch (Exception e) {
			throw new ServerError(MessageConstant.REGISTER_FAIL);
		}
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

	public User findByIdAndIsDelete(UUID id) {
		return userRepository.findByIdAndDeletedAt(id, null);
	}

	public List<ProfileResponse> getProfile(Set<UUID> ids) {
		Set<User> users = userRepository.findByIdInAndDeletedAt(ids, null);
    	List<ProfileResponse> listProfile = new ArrayList<ProfileResponse>();
    	for (User user : users) {
    		ProfileResponse profileResponse = new ProfileResponse();
    		profileResponse.setUserId(user.getId());
    		String role = user.getRole();
    		if(user.getCompany() != null) {
        		Company company = user.getCompany();
        		profileResponse.setPropertyId(company.getId());
        		profileResponse.setName(company.getName());
        		profileResponse.setRole(role.replaceAll("ROLE_", ""));
        		profileResponse.setAvatar(company.getLogoUrl());
        	}
    		
    		if(user.getCandidate() != null) {
        		Candidate candidate = user.getCandidate();
        		profileResponse.setPropertyId(candidate.getId());
        		profileResponse.setName(candidate.getFullName());
        		profileResponse.setRole(role.replaceAll("ROLE_", ""));
        		profileResponse.setAvatar(candidate.getAvatar());
        	}
    		
    		if(user.getTranslator() != null) {
        		Translator translator = user.getTranslator();
        		profileResponse.setPropertyId(translator.getId());
        		profileResponse.setName(translator.getName());
        		profileResponse.setRole(role.replaceAll("ROLE_", ""));
        		profileResponse.setAvatar(translator.getAvatar());
        	}
    		listProfile.add(profileResponse);
		}
    	return listProfile;
	}
	
	public void changePropertyId(UUID userId, UUID propertyId) throws ServerError{
		try {
			User user = userRepository.findByIdAndDeletedAt(userId, null);
			user.setPropertyId(propertyId);
			userRepository.save(user);
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CHANGE_PROPERTY_ID_FAIL);
		}
	}

	public ResponseDataAPI changePassword(UserPrincipal userPrincipal,
			ChangePasswordRequest changePasswordRequest) throws ServerError, BadRequestException{
		try {
			boolean checkOldPassword = BCrypt.checkpw(changePasswordRequest.getOldPassword(), userPrincipal.getPassword());
			if(checkOldPassword) {
				User user = userRepository.findByIdAndDeletedAt(userPrincipal.getId(), null);
				user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));

				userRepository.save(user);
				return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, "", "");
			} else {
				throw new BadRequestException(MessageConstant.CHANGE_PASSWORD_NOT_CORRECT);
			}
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CHANGE_PASSWORD_FAIL);
		}
	}

	public ResponseDataAPI forgetPassword(MailForgetPasswordRequest mailForgetPasswordRequest)
		throws BadRequestException, ServerError{
		try {
			User user = this.findUserByEmail(mailForgetPasswordRequest.getEmail());
			if(user == null) {
				throw new BadRequestException(MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST);
			} else {
				ForgetPassword fp = forgetPasswordRepository.findByUserId(user.getId());
				if(fp != null) {
					forgetPasswordRepository.delete(fp);
				}

				String code = CommonFunction.generateCode(6);

				ForgetPassword forgetPassword = new ForgetPassword();
				forgetPassword.setCode(code);
				forgetPassword.setUser(user);
				forgetPassword.setCreatedAt(CommonFunction.getCurrentDateTime());

				ForgetPassword result = forgetPasswordRepository.save(forgetPassword);
				if(result != null) {
					this.sendEmail(user.getEmail(), "Reset the password!", "Confirmation code is: " + code);
				}

				return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, "", "");
			}
		} catch(BadRequestException e) {
			throw e;
		} catch(Exception e) {
			throw new ServerError(MessageConstant.RESET_PASSWORD_FAIL);
		}
	}

	public ResponseDataAPI resetPassword(ResetPasswordRequest resetPasswordRequest)
			throws BadRequestException, ServerError{
		try {
			User user = this.findUserByEmail(resetPasswordRequest.getEmail());
			if(user == null) {
				throw new BadRequestException(MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST);
			} else {
				ForgetPassword forgetPassword = forgetPasswordRepository.findByUserIdAndCode(user.getId(),
						resetPasswordRequest.getCode());
				if(forgetPassword == null) {
					throw new BadRequestException(MessageConstant.RESET_FORGET_PASSWORD_INCORRECT_CODE);
				}
				forgetPasswordRepository.delete(forgetPasswordRepository.findByUserId(user.getId()));

				user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
				userRepository.save(user);

				return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, "", "");
			}
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.RESET_PASSWORD_FAIL);
		}
	}

	private User findUserByEmail(String email) {
		return userRepository.findByEmail(email).get();
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
