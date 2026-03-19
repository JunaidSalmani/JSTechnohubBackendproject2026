package com.example.coaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();

    @Autowired
    private JavaMailSender mailSender;

    public String generateOtp(String email) {
        //System.out.println("OtpService.generateOtp() " + email);
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        otpStore.put(email, otp);

        sendOtpEmail(email, otp); // <-- send mail here
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        return otpStore.containsKey(email) && otpStore.get(email).equals(otp);
    }

    public void removeOtp(String email) {
        otpStore.remove(email);
    }

    private void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + "\n\nThis OTP is valid for 5 minutes.");
            message.setFrom("your_email@gmail.com");
            mailSender.send(message);

            //System.out.println("OTP email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.out.println("Failed to send OTP email: " + e.getMessage());
        }
    }
    
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("your_email@gmail.com"); // replace with your sender email
            mailSender.send(message);

            //System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
