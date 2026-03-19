package com.example.coaching.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
 
/**
 * Inbound payload from ConsultingServicesPage.jsx EnquiryModal.
 *
 * Field names here MUST exactly match the JSON keys sent by the
 * frontend (see handleSubmit in EnquiryModal).
 *
 * Bean Validation (@Valid in controller) rejects bad data before
 * it ever reaches the service layer or database.
 */
@Data
public class ConsultingEnquiryRequest {
 
    // ── Step 1 ────────────────────────────────────────────────
 
    @NotBlank(message = "First name is required")
    @Size(max = 60, message = "First name is too long")
    private String firstName;
 
    @NotBlank(message = "Last name is required")
    @Size(max = 60, message = "Last name is too long")
    private String lastName;
 
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Size(max = 254, message = "Email is too long")
    private String email;
 
    @NotBlank(message = "Organisation name is required")
    @Size(max = 120, message = "Organisation name is too long")
    private String company;
 
    @Size(max = 100, message = "Job title is too long")
    private String jobTitle;               // optional
 
    // ── Step 2 ────────────────────────────────────────────────
 
    /**
     * react-phone-input-2 sends digits only without the "+".
     * Examples: "919876543210"  or  "14155552671"
     * We normalise to E.164 format (+...) in the service layer.
     */
    @NotBlank(message = "Phone number is required")
    private String phone;
 
    @NotBlank(message = "Please select a service")
    @Size(max = 100, message = "Service name is too long")
    private String serviceRequired;
 
    @Size(max = 30, message = "Positions range is too long")
    private String positionsRange;         // optional
 
    @Size(max = 150, message = "Location is too long")
    private String requirementLocation;    // optional
 
    @Size(max = 1000, message = "Message must be under 1000 characters")
    private String message;               // optional
 
    @Size(max = 60, message = "Heard-from value is too long")
    private String heardFrom;             // optional
 
    // ── Consent ───────────────────────────────────────────────
 
    @AssertTrue(message = "You must accept the Terms of Use and Privacy Policy")
    private boolean termsAccepted;        // must be true
 
    private boolean marketingOptedIn;     // optional — defaults to false
}