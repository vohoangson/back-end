package com.jobs.japan_work.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialUserDetails;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.social.SocialUserDetailsImpl;
 
public class SecurityUtil {
 
    // Auto Login.
    public static void logInUser(Account user,String roleName) {
 
        SocialUserDetails userDetails = new SocialUserDetailsImpl(user, roleName);
 
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
 
}
