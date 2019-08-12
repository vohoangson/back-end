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
                		UrlConstant.URL_AMW,
                		UrlConstant.URL_NOTIFICATIONS_ENDPOINT)
                        .permitAll()
                    .antMatchers(HttpMethod.GET, 
                		UrlConstant.URL_COMPANIES, 
                		UrlConstant.URL_COMPANY,
                		UrlConstant.URL_JOBS, 
                		UrlConstant.URL_JOB,
                		UrlConstant.URL_CANDIDATES, 
                		UrlConstant.URL_CANDIDATE,
                		UrlConstant.URL_BUSINESS,
                		UrlConstant.URL_COUNTRIES,
                		UrlConstant.URL_CITIES,
                		UrlConstant.URL_DISTRICT,
                		UrlConstant.URL_LEVELS,
                		UrlConstant.URL_CONTRACT,
                		UrlConstant.URL_LANGUAGUE_TYPE,
                		UrlConstant.URL_USER,
                		UrlConstant.URL_REQUEST_TRANSLATIONS,
                		UrlConstant.URL_REQUEST_TRANSLATION)
                    	.permitAll()
                	.antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_CANCEL)
                		.permitAll()
            		.antMatchers(HttpMethod.POST,
                		UrlConstant.URL_REQUEST_TRANSLATIONS)
                    	.hasAnyRole("COMPANY","CANDIDATE","ADMIN")	
                	.antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_ACCEPT_APPLY,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_ACCEPT_FINISHED,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_REFUSE_FINISHED,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_REJECT)
                    	.hasAnyRole("COMPANY","CANDIDATE","ADMIN")	
                    .antMatchers(HttpMethod.POST,
                		UrlConstant.URL_COMPANIES,
                		UrlConstant.URL_JOBS)
                    	.hasAnyRole("COMPANY","ADMIN")
                    .antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_COMPANY,
                		UrlConstant.URL_JOB)
                    	.hasAnyRole("COMPANY","ADMIN")
                	.antMatchers(HttpMethod.POST,
                		UrlConstant.URL_CANDIDATE_PERSONALS,
                		UrlConstant.URL_CANDIDATE_EXPERIENCE)
                    	.hasAnyRole("CANDIDATE","ADMIN")
                    .antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_CANDIDATE_PERSONAL,
                		UrlConstant.URL_CANDIDATE_WISH)
                    	.hasAnyRole("CANDIDATE","ADMIN")
                	.antMatchers(HttpMethod.DELETE,
                		UrlConstant.URL_JOB
                		).hasAnyRole("ADMIN","COMPANY")
                    .antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_TRANSLATOR_JOIN,
                		UrlConstant.URL_REQUEST_TRANSLATIONS_CONFIRM_FINISHED)
                    	.hasAnyRole("TRANSLATOR","ADMIN")
                    .antMatchers(HttpMethod.DELETE,
                		UrlConstant.URL_COMPANY,
                		UrlConstant.URL_CANDIDATE
                		).hasRole("ADMIN")
                    .antMatchers(HttpMethod.PATCH,
                		UrlConstant.URL_COMPANY_UNDELETE,
                		UrlConstant.URL_JOB,
                		UrlConstant.URL_CANDIDATE
                		).hasRole("ADMIN")
                    .antMatchers( HttpMethod.POST,
                		UrlConstant.URL_BUSINESS,
                		UrlConstant.URL_COUNTRIES,
                		UrlConstant.URL_CITIES,
                		UrlConstant.URL_CITIES_BATCH,
                		UrlConstant.URL_DISTRICT,
                		UrlConstant.URL_DISTRICTS,
                		UrlConstant.URL_LEVELS,
                		UrlConstant.URL_CONTRACTS,
                		UrlConstant.URL_LANGUAGUE_TYPE
                		).hasRole("ADMIN")
                    .antMatchers( HttpMethod.PATCH,
                		UrlConstant.URL_BUSINESS,
                		UrlConstant.URL_CITY,
                		UrlConstant.URL_DISTRICT,
                		UrlConstant.URL_LEVEL,
                		UrlConstant.URL_CONTRACT,
                		UrlConstant.URL_LANGUAGUE_TYPE
                		).hasRole("ADMIN")
                    .antMatchers( HttpMethod.DELETE,
                		UrlConstant.URL_BUSINESS,
                		UrlConstant.URL_CITY,
                		UrlConstant.URL_DISTRICT,
                		UrlConstant.URL_LEVEL,
                		UrlConstant.URL_CONTRACT,
                		UrlConstant.URL_LANGUAGUE_TYPE
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