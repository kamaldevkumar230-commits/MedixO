package com.medixo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

   
    public void sendVerificationCode(String toEmail, String code) {

        try {
            String subject = "MedixO Email Verification Code";

            String content = "<html>"
                    + "<body style='font-family:Arial;'>"
                    + "<h2>MedixO Verification 🔐</h2>"
                    + "<p>Your verification code is:</p>"
                    + "<h1 style='color:blue;'>" + code + "</h1>"
                    + "<p>This code will expire in 5 minutes.</p>"
                    + "</body>"
                    + "</html>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);

            System.out.println("OTP SENT ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
