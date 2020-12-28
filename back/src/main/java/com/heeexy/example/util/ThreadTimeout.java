package com.heeexy.example.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class ThreadTimeout extends Thread {
    private SimpleMailMessage simpleMailMessage;
    private JavaMailSender javaMailSender;

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void run() {
        javaMailSender.send(simpleMailMessage);

    }
}