package com.mcraft.site.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailSender {

    @Value("${spring.mail.username}")
    private String username;
    
    @Autowired
    private JavaMailSender jms;
    
    public void send(String emailTo, String subject, String message) {
     	
    	MimeMessage msg = jms.createMimeMessage();
     	try {
    	MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setFrom(username);
	    	helper.setTo(emailTo);
	    	msg.setSubject(subject, "UTF-8");
	    	msg.setContent(message, "text/html; charset=UTF-8");
	    	jms.send(msg);
    	} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}