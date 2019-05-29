package com.japanwork.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japanwork.model.User;
import com.japanwork.model.VerificationToken;
import com.japanwork.repository.token.VerificationTokenRepository;
import com.japanwork.repository.user.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    
	public VerificationToken generateNewVerificationToken(User user) {
        VerificationToken vToken = new VerificationToken(UUID.randomUUID().toString(), user);
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
	
	public VerificationToken generateNewVerificationToken(String token) {
		VerificationToken vToken = tokenRepository.findByToken(token);
        vToken.updateToken(UUID.randomUUID()
            .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }
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
	
	public User getUser(final String verificationToken) {
        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
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
	
	public User findById(UUID id) {
		return userRepository.findById(id).get();
	}
}