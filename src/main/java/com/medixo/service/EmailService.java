package com.medixo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    public void sendVerificationCode(String toEmail, String code) {

        try {
            String url = "https://api.brevo.com/v3/smtp/email";

            String json = "{"
                    + "\"sender\":{\"email\":\"medixoteam@gmail.com\"},"
                    + "\"to\":[{\"email\":\"" + toEmail + "\"}],"
                    + "\"subject\":\"MedixO Verification Code\","
                    + "\"htmlContent\":\"<h2>Your OTP is:</h2><h1>" + code + "</h1>\""
                    + "}";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("api-key", apiKey);
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            System.out.println("EMAIL RESPONSE CODE: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendApprovalEmail(String toEmail, String name) {
    	System.out.println("EMAIL METHOD CALLED");

        try {
            String url = "https://api.brevo.com/v3/smtp/email";

            String json = "{"
                    + "\"sender\":{\"email\":\"medixoteam@gmail.com\"},"
                    + "\"to\":[{\"email\":\"" + toEmail + "\"}],"
                    + "\"subject\":\"Doctor Approved ✅\","
                    + "\"htmlContent\":\"<h2>Congratulations " + name + " 🎉</h2>"
                    + "<p>Your account has been <b>approved</b>.</p>"
                    + "<p>You can now login to MedixO.</p>"
                    + "<br><a href='http://localhost:8080/login'>Login Now</a>\""
                    + "}";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("api-key", apiKey);
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            System.out.println("APPROVAL EMAIL RESPONSE: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}