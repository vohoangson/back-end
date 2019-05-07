package com.jobs.japan_work.controller;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jobs.japan_work.model.Account;
import com.jobs.japan_work.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/login")
	@ResponseBody
	public String login() {
		return "Login";
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
		return "admin";
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
		Account acc = accountService.save(account);
		if(acc != null) {
			return "redirect:/login";
		} else {
			return "redirect:/login";
		}
		
	}
}
