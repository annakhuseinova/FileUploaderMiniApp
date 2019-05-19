package com.annakhuseinova.FileUploaderApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    SimpleMailMessage simpleMailMessage;


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void sendMailMessage(String mailMessage, String subject, String to){
        simpleMailMessage = new SimpleMailMessage();
        javaMailSender.setHost("smtp.mailtrap.io");
        javaMailSender.setPort(2525);
        javaMailSender.setUsername("47e70da794a219");
        javaMailSender.setPassword("5400da7dc4dc82");
        simpleMailMessage.setFrom("smtp.mailtrap.io");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(mailMessage);
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }
}
