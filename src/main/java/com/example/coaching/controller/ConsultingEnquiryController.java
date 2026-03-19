package com.example.coaching.controller;


import com.example.coaching.dto.ConsultingEnquiryRequest;
import com.example.coaching.dto.ConsultingEnquiryResponse;
import com.example.coaching.model.ConsultingEnquiry;
import com.example.coaching.service.ConsultingEnquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.Map;
 
/**
 * REST controller for consulting enquiries.
 *
 * Base URL  : /api/consulting/enquiries
 *
 * Endpoints:
 *  POST   /api/consulting/enquiries          ← frontend form submit
 *  GET    /api/consulting/enquiries          ← admin: all enquiries
 *  GET    /api/consulting/enquiries/status/{status}  ← admin: filter by status
 */
@RestController
@RequestMapping("/api/consulting/enquiries")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://coachingwebsite-production.up.railway.app"
})
public class ConsultingEnquiryController {
 
    private final ConsultingEnquiryService service;
 
    /**
     * POST /api/consulting/enquiries
     *
     * Called by api.js → submitConsultingEnquiry()
     * @Valid triggers Bean Validation on ConsultingEnquiryRequest
     * Returns 201 CREATED on success, 400 on validation failure
     */
    @PostMapping
    public ResponseEntity<?> submit(
            @Valid @RequestBody ConsultingEnquiryRequest request) {
 
        log.info("POST /consulting/enquiries — email={}, service={}",
            request.getEmail(), request.getServiceRequired());
 
        try {
            ConsultingEnquiryResponse response = service.submit(request);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
 
        } catch (RuntimeException ex) {
            // Duplicate submission or business rule violation
            log.warn("Enquiry rejected: {}", ex.getMessage());
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
        }
    }
 
}
