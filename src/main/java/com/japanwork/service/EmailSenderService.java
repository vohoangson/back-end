package com.japanwork.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.japanwork.constant.EmailConstants;

@Service
public class EmailSenderService {
	private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
    
    public void sendEmail(String to, String subject, String content) {
    	MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mail, true);
			helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setFrom(EmailConstants.MY_EMAIL);
	        helper.setText(content, true);
	        javaMailSender.send(mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	
//    	SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(to);
//        mailMessage.setSubject(subject);
//        mailMessage.setFrom(EmailConstants.MY_EMAIL);
//        mailMessage.setText(content);
//        this.sendEmail(mailMessage);
    }
}
