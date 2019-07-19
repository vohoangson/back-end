package com.japanwork.service;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.EmailConstants;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.BadRequestException;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.exception.ServerError;
import com.japanwork.model.AuthProvider;
import com.japanwork.model.Candidate;
import com.japanwork.model.Company;
import com.japanwork.model.Country;
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
import com.japanwork.payload.response.UserResponse;
import com.japanwork.repository.academy.AcademyRepository;
import com.japanwork.repository.candidate.CandidateRepository;
import com.japanwork.repository.company.CompanyRepository;
import com.japanwork.repository.experience.ExperienceRepository;
import com.japanwork.repository.forget_password.ForgetPasswordRepository;
import com.japanwork.repository.job.JobRepository;
import com.japanwork.repository.language_certificate.LanguageCertificateRepository;
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
    private CompanyRepository companyRepository;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private AcademyRepository academyRepository;
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    @Autowired
    private LanguageCertificateRepository languageCertificateRepository;
    
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
	
	public User getUser(UserPrincipal userPrincipal) throws ResourceNotFoundException{
        User user = userRepository.findById(userPrincipal.getId())
        		.orElseThrow(() -> new ResourceNotFoundException(MessageConstant.ERROR_404_MSG));
        return user;
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
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
	        
	        User user = new User();
	        user.setName(signUpRequest.getName());
	        user.setEmail(signUpRequest.getEmail());
	        user.setPassword(signUpRequest.getPassword());
	        user.setProvider(AuthProvider.local);
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setRole("ROLE_"+signUpRequest.getRole());
	        user.setProviderId(null);
	        user.setCountry(new Country(signUpRequest.getCountryId()));
	        user.setCreatedAt(timestamp);
	        user.setUpdatedAt(timestamp);
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
	                .body(new BaseDataResponse(new ApiResponse(true, MessageConstant.REGISTER_SUCCESS)));
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
	
	@Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRES_NEW)
	public BaseMessageResponse deleteUserByEmail(String email)  throws ServerError{
		try {
			User user = this.findUserByEmail(email);
			if(user != null) {
				Company company = companyRepository.findByUser(user);
				if(company != null){
					
					jobRepository.deleteAll(jobRepository.findAllByCompany(company));
					companyRepository.delete(company);
				} 
				
				Candidate candidate = candidateRepository.findByUser(user);
				if(candidate != null) {
					academyRepository.deleteAll(academyRepository.findByCandidateId(candidate.getId()));
					experienceRepository.deleteAll(experienceRepository.findByCandidateId(candidate.getId()));
					languageCertificateRepository.deleteAll(languageCertificateRepository.findByCandidateId(candidate.getId()));
					candidateRepository.delete(candidate);
				}
				
				tokenRepository.delete(tokenRepository.findByUserId(user.getId()));
				userRepository.delete(user);
			}
			
			BaseMessageResponse response = new BaseMessageResponse(MessageConstant.DELETE_USER_BY_EMAIL, MessageConstant.DELETE_USER_BY_EMAIL_SUCCESS);
			return response;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.DELETE_USER_BY_EMAIL_FAIL);
		}
	}
	
	public void changePropertyId(UUID userId, UUID propertyId) throws ServerError{
		try {
			User user = userRepository.findByIdAndDeletedAt(userId, null);
			user.setPropertyId(propertyId);
			userRepository.save(user);
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CHANGE_PROPERTY_ID_FAIL_MSG);
		}
	}
	
	public BaseMessageResponse changePassword(UserPrincipal userPrincipal, 
			ChangePasswordRequest changePasswordRequest) throws ServerError, BadRequestException{
		try {
			boolean checkOldPassword = BCrypt.checkpw(changePasswordRequest.getOldPassword(), userPrincipal.getPassword());
			if(checkOldPassword) {
				User user = userRepository.findByIdAndDeletedAt(userPrincipal.getId(), null);
				user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
				
				userRepository.save(user);
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.CHANGE_PASSWORD, MessageConstant.CHANGE_PASSWORD_SUCCESS);
		        return message;
			} else {
				throw new BadRequestException(MessageConstant.CHANGE_PASSWORD_NOT_CORRECT, MessageConstant.CHANGE_PASSWORD_NOT_CORRECT_MSG);
			}
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.CHANGE_PASSWORD_FAIL_MSG);
		}
	}
	
	public BaseMessageResponse forgetPassword(MailForgetPasswordRequest mailForgetPasswordRequest) 
		throws BadRequestException, ServerError{
		try {
			User user = this.findUserByEmail(mailForgetPasswordRequest.getEmail());
			if(user == null) {
				throw new BadRequestException(MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST, MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST_MSG);
			} else {
				ForgetPassword fp = forgetPasswordRepository.findByUserId(user.getId());
				if(fp != null) {
					forgetPasswordRepository.delete(fp);
				}
				
				String code = CommonFunction.generateCode(6);
				
				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());
				
				ForgetPassword forgetPassword = new ForgetPassword();
				forgetPassword.setCode(code);
				forgetPassword.setUser(user);
				forgetPassword.setCreatedAt(timestamp);
				
				ForgetPassword result = forgetPasswordRepository.save(forgetPassword);
				if(result != null) {
					this.sendEmail(user.getEmail(), "Reset the password!", "Confirmation code is: " + code);
				}
				
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.FORGET_PASSWORD, MessageConstant.RESET_FORGET_PASSWORD_SUCCESS);
		        return message;
			}
		} catch(BadRequestException e) {
			throw e;
		} catch(Exception e) {
			throw new ServerError(MessageConstant.RESET_PASSWORD_FAIL_MSG);
		}
	}
	
	public BaseMessageResponse resetPassword(ResetPasswordRequest resetPasswordRequest) 
			throws BadRequestException, ServerError{
		try {
			User user = this.findUserByEmail(resetPasswordRequest.getEmail());
			if(user == null) {
				throw new BadRequestException(MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST, 
						MessageConstant.RESET_FORGET_PASSWORD_EMAIL_NOT_EXIST_MSG);
			} else {
				ForgetPassword forgetPassword = forgetPasswordRepository.findByUserIdAndCode(user.getId(), 
						resetPasswordRequest.getCode());
				if(forgetPassword == null) {
					throw new BadRequestException(MessageConstant.RESET_FORGET_PASSWORD_INCORRECT_CODE, 
							MessageConstant.RESET_FORGET_PASSWORD_INCORRECT_CODE_MSG);
				}
				forgetPasswordRepository.delete(forgetPasswordRepository.findByUserId(user.getId()));
				
				user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));	
				userRepository.save(user);
				
				BaseMessageResponse message = new BaseMessageResponse(MessageConstant.RESET_PASSWORD, MessageConstant.RESET_PASSWORD_SUCCESS);
		        return message;
			}
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw new ServerError(MessageConstant.RESET_PASSWORD_FAIL_MSG);
		}
	}
	
	public UserResponse converUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setName(user.getName());
		userResponse.setEmail(user.getEmail());
		userResponse.setRole(user.getRole());
		userResponse.setLanguageCode(user.getCountry().getLanguage().getCode());
		userResponse.setPropertyId(user.getPropertyId());
		
		return userResponse;
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
