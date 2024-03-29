package com.japanwork.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.japanwork.constant.UrlConstant;
import com.japanwork.security.CustomUserDetailsService;
import com.japanwork.security.RestAuthenticationEntryPoint;
import com.japanwork.security.TokenAuthenticationFilter;
import com.japanwork.security.oauth2.CustomOAuth2UserService;
import com.japanwork.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.japanwork.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.japanwork.security.oauth2.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Autowired
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .accessDeniedPage("/403")
                    .and()
                .authorizeRequests()
                    .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                    .antMatchers(UrlConstant.URL_LOGIN,
                		UrlConstant.URL_OAUTH2_LOGIN,	
                		UrlConstant.URL_REGISTER,
                		UrlConstant.URL_CONFIRM_ACCOUNT,
                		UrlConstant.URL_RESEND_REGISTRATION_TOKEN,
                		UrlConstant.URL_AMW_UPLOAD_FILE,
                		UrlConstant.URL_AMW_DELETE_FILE,
                		UrlConstant.URL_NOTIFICATIONS_ENDPOINT)
                        .permitAll()
                    .antMatchers(HttpMethod.GET, 
                		UrlConstant.URL_COMPANY, 
                		UrlConstant.URL_COMPANY_ID,
                		UrlConstant.URL_JOB, 
                		UrlConstant.URL_JOB_ID,
                		UrlConstant.URL_CANDIDATE, 
                		UrlConstant.URL_CANDIDATE_ID,
                		UrlConstant.URL_BUSINESS,
                		UrlConstant.URL_COUNTRY,
                		UrlConstant.URL_CITY,
                		UrlConstant.URL_DISTRICT,
                		UrlConstant.URL_LEVEL,
                		UrlConstant.URL_CONTRACT,
                		UrlConstant.URL_LANGUAGUE_TYPE,
                		UrlConstant.URL_CURRENCYUNIT,
                		UrlConstant.URL_USER)
                    	.permitAll()
                    .antMatchers(HttpMethod.POST,
                		UrlConstant.URL_COMPANY,
                		UrlConstant.URL_JOB)
                    	.hasAnyRole("COMPANY","ADMIN")
                    .antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_COMPANY_ID,
                		UrlConstant.URL_JOB_ID)
                    	.hasAnyRole("COMPANY","ADMIN")
                	.antMatchers(HttpMethod.POST,
                		UrlConstant.URL_CANDIDATE_PERSONAL,
                		UrlConstant.URL_CANDIDATE_ID_EXPERIENCE)
                    	.hasAnyRole("CANDIDATE","ADMIN")
                    .antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_CANDIDATE_ID_PERSONAL,
                		UrlConstant.URL_CANDIDATE_ID_WISH)
                    	.hasAnyRole("CANDIDATE","ADMIN")
                	.antMatchers(HttpMethod.DELETE,
                    		UrlConstant.URL_JOB_ID
                    		).hasAnyRole("ADMIN","COMPANY")
                	.antMatchers(HttpMethod.POST,
                    		UrlConstant.URL_COMPANY_TS)
                        	.hasAnyRole("TRANSLATOR","ADMIN")
                        .antMatchers(HttpMethod.PATCH,
                    		UrlConstant.URL_COMPANY_TS_ID)
                        	.hasAnyRole("TRANSLATOR","ADMIN")
                    .antMatchers(HttpMethod.DELETE,
                		UrlConstant.URL_COMPANY_ID,
                		UrlConstant.URL_CANDIDATE_ID
                		).hasRole("ADMIN")
                    .antMatchers(
                		UrlConstant.URL_COMPANY_UNDEL_ID,
                		UrlConstant.URL_JOB_UNDEL,
                		UrlConstant.URL_CANDIDATE_UNDEL
                		).hasRole("ADMIN")
                    .antMatchers( HttpMethod.POST,
                		UrlConstant.URL_BUSINESS,
                		UrlConstant.URL_COUNTRY,
                		UrlConstant.URL_CITY,
                		UrlConstant.URL_CITIES,
                		UrlConstant.URL_DISTRICT,
                		UrlConstant.URL_DISTRICTS,
                		UrlConstant.URL_LEVEL,
                		UrlConstant.URL_CONTRACT,
                		UrlConstant.URL_LANGUAGUE_TYPE,
                		UrlConstant.URL_CURRENCYUNIT
                		).hasRole("ADMIN")
                    .antMatchers( HttpMethod.PATCH,
                    		UrlConstant.URL_BUSINESS_ID,
                    		UrlConstant.URL_CITY_ID,
                    		UrlConstant.URL_DISTRICT_ID,
                    		UrlConstant.URL_LEVEL_ID,
                    		UrlConstant.URL_CONTRACT_ID,
                    		UrlConstant.URL_LANGUAGUE_TYPE_ID,
                    		UrlConstant.URL_CURRENCYUNIT_ID
                    		).hasRole("ADMIN")
                    .antMatchers( HttpMethod.DELETE,
                    		UrlConstant.URL_BUSINESS_ID,
                    		UrlConstant.URL_CITY_ID,
                    		UrlConstant.URL_DISTRICT_ID,
                    		UrlConstant.URL_LEVEL_ID,
                    		UrlConstant.URL_CONTRACT_ID,
                    		UrlConstant.URL_LANGUAGUE_TYPE,
                    		UrlConstant.URL_CURRENCYUNIT_ID
                    		).hasRole("ADMIN")
                    .anyRequest()
                        .permitAll()
                    .and()
                .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/login")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                        .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}