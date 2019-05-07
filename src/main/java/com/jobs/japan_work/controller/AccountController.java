package com.jobs.japan_work.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.model.form.AccountForm;
import com.jobs.japan_work.service.AccountService;
import com.jobs.japan_work.utils.SecurityUtil;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
  
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
	
	@GetMapping("/login")
	public String login() {
		
		return "loginPage";
	}
	
	@RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
    public String signInPage() {
        return "redirect:/login";
    }
	
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
    public String signupPage(WebRequest request, Model model) {
  
        ProviderSignInUtils providerSignInUtils //
                = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
  
        // Retrieve social networking information.
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        
        AccountForm accountForm = null;
        //
        if (connection != null) {
        	accountForm = new AccountForm(connection);
        } else {
        	accountForm = new AccountForm();
        }
        model.addAttribute("myForm", accountForm);
        return "signupPage";
    }
	
	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
    public String signupSave(WebRequest request, //
            Model model, //
            @ModelAttribute("myForm") AccountForm accountForm,
            final RedirectAttributes redirectAttributes) {
        Account registered = null;
  
        try {
            registered = accountService.registerNewAccount(accountForm, "ROLE_USER");
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorMessage", "Error " + ex.getMessage());
            return "signupPage";
        }
  
        if (accountForm.getSignInProvider() != null) {
            ProviderSignInUtils providerSignInUtils //
                    = new ProviderSignInUtils(connectionFactoryLocator, usersConnectionRepository);
  
            // (Spring Social API):
            // If user login by social networking.
            // This method saves social networking information to the UserConnection table.
            providerSignInUtils.doPostSignUp(registered.getUserName(), request);
        }
  
        // After registration is complete, automatic login.
        SecurityUtil.logInUser(registered, registered.getRole());
  
        return "redirect:/home";
    }
	
	
	@GetMapping("/home")
	@ResponseBody
	public String home() {
		return "Home";
	}
	
	@GetMapping("/home/candidate")
	@ResponseBody
	public String candidate() {
		return "candidate";
	}
	
	@GetMapping("/home/admin")
	@ResponseBody
	public String admin() {
		return "admin13";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@RequestBody Account account) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
		account.setCreate_date(timestamp);
		account.setUpdate_date(timestamp);
		account.setEnabled(1);
		Account acc = accountService.registerNewAccount(account);
		if(acc != null) {
			return "redirect:/login";
		} else {
			return "redirect:/login";
		}
		
	}
}
