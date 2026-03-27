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

   
    public void sendVerificationEmail(String toEmail, String token) {

        try {
            String subject = "Verify Your MedixO Account";

            String baseUrl = "http://medixo-0k21.onrender.com";
            String link = baseUrl + "/verify?token=" + token;

            String content = "<html>"
                    + "<body style='font-family:Arial;'>"
                    + "<h2>Welcome to MedixO 👨‍⚕️</h2>"
                    + "<p>Please click the link below to verify your email:</p>"

                    // 🔥 IMPORTANT (direct link text)
                    + "<p><a href=\"" + link + "\">Click here to verify</a></p>"

                    + "<br>"
                    + "<p>If not working, copy this link:</p>"
                    + "<p>" + link + "</p>"

                    + "</body>"
                    + "</html>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML enable

            mailSender.send(message);

            System.out.println("EMAIL SENT SUCCESSFULLY ✅");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
