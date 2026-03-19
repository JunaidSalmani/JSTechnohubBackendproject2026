package com.example.coaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.coaching.model.Certificate_Enquiry;
import com.example.coaching.model.Enquiry;
import com.example.coaching.repository.Certification_EnquiryRepository;
import com.example.coaching.repository.EnquiryRepository;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private Certification_EnquiryRepository certificate_enquiryRepository;

    public Certificate_Enquiry saveEnquiry(Certificate_Enquiry enquiry) {
        return certificate_enquiryRepository.save(enquiry);
    }
    
    public Enquiry saveEnquiry(Enquiry enquiry) {
        Enquiry savedEnquiry = enquiryRepository.save(enquiry);
        // Send a confirmation email after saving
        sendEnquiryConfirmationEmail(savedEnquiry);
        return savedEnquiry;
    }

    private void sendEnquiryConfirmationEmail(Enquiry enquiry) {
        try {
            // Email to the user
            SimpleMailMessage userMessage = new SimpleMailMessage();
            userMessage.setTo(enquiry.getEmail());
            userMessage.setSubject("Enquiry Received for " + enquiry.getCourseTitle());
            userMessage.setText(
                "Hello " + enquiry.getName() + ",\n\n" +
                "Thank you for your enquiry about our course: '" + enquiry.getCourseTitle() + "'.\n\n" +
                "We have received your message and will get back to you shortly.\n\n" +
                "Best regards,\n" +
                "The Coaching Team"
            );
            mailSender.send(userMessage);

            // Notification email to the admin
            SimpleMailMessage adminMessage = new SimpleMailMessage();
            adminMessage.setTo("your-admin-email@example.com"); // Set your admin email here
            adminMessage.setSubject("New Course Enquiry: " + enquiry.getCourseTitle());
            adminMessage.setText(
                "A new enquiry has been submitted.\n\n" +
                "Course: " + enquiry.getCourseTitle() + "\n" +
                "Name: " + enquiry.getName() + "\n" +
                "Email: " + enquiry.getEmail() + "\n" +
                "Phone: " + (enquiry.getPhoneNumber() != null ? enquiry.getPhoneNumber() : "N/A") + "\n" +
                "Message: " + enquiry.getMessage()
            );
            mailSender.send(adminMessage);

        } catch (Exception e) {
            // Log the exception. In a real app, you'd have more robust error handling.
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}