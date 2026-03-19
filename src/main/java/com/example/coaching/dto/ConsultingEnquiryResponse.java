package com.example.coaching.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
 * What we send back to the browser after a successful submission.
 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingEnquiryResponse {
 
    private Long   id;
    private String referenceNumber;      // e.g. "CE-2025-00042"
    private String firstName;
    private String serviceRequired;
    private String confirmationMessage;  // shown in the success modal
}