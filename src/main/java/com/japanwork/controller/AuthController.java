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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.japanwork.common.CommonFunction;
import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.MessageConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.exception.ResourceNotFoundException;
import com.japanwork.payload.request.LoginRequest;
import com.japanwork.payload.response.AuthResponse;
import com.japanwork.payload.response.ConfirmRegistrationTokenResponse;
import com.japanwork.payload.response.ErrorResponse;
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
            return ResponseEntity.ok(new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, authResponse, null, null));
        } catch(BadCredentialsException e){
        	ErrorResponse error = CommonFunction.getExceptionError(MessageConstant.LOGIN_FAIL, "errors.yml");
            return ResponseEntity.badRequest().body(new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.FAILURE, null, null, error));
        }
    }

    @GetMapping(value = UrlConstant.URL_CONFIRM_ACCOUNT)
    public RedirectView confirmRegistration(@RequestParam("token") final String token) throws ResourceNotFoundException{
    	final String result = userService.validateVerificationToken(token);
    	if (result.equals("valid")) {
            return new RedirectView("http://datvo.io/login");
        } else if(result.equals("expired")) {
            throw new ResourceNotFoundException(MessageConstant.CONFIRM_REGISTER_EXPIRED);
        } else {
            throw new ResourceNotFoundException(MessageConstant.CONFIRM_REGISTER_INVALID);
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
            return ResponseEntity.ok(new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, authResponse, null, null));
    	}else{
    		ErrorResponse err = CommonFunction.getExceptionError(MessageConstant.LOGIN_OAUTH2_FAIL, "errors.yml");
    		return ResponseEntity.badRequest().body(new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.FAILURE, null, null, err));
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

    @GetMapping(value = UrlConstant.URL_NOTIFICATIONS_ENDPOINT)
    public ResponseDataAPI websocket(@CurrentUser UserPrincipal userPrincipal) {
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
        return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, queueName, null, null);
    }
}
