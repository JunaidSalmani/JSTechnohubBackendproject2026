package com.example.coaching.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.coaching.dto.AdminEnquiryDTO;
import com.example.coaching.model.Certificate_Enquiry;
import com.example.coaching.model.ConsultingEnquiry;
import com.example.coaching.model.Enquiry;
import com.example.coaching.repository.Certification_EnquiryRepository;
import com.example.coaching.repository.ConsultingEnquiryRepository;
import com.example.coaching.repository.EnquiryRepository;

@RestController
@RequestMapping("/api/admin/enquiries")
public class AdminEnquiryController {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private Certification_EnquiryRepository certificateEnquiryRepository;

    @Autowired
    private ConsultingEnquiryRepository consultingRepository;

    @GetMapping
    public List<AdminEnquiryDTO> getAllEnquiries() {
        List<AdminEnquiryDTO> allEnquiries = new ArrayList<>();

        // 1. Course enquiries
        for (Enquiry e : enquiryRepository.findAll()) {
            allEnquiries.add(new AdminEnquiryDTO(
                e.getId(), "course", e.getCourseTitle(), e.getName(),
                e.getEmail(), e.getPhoneNumber(), e.getMessage(), e.isClarified()
            ));
        }

        // 2. Certification enquiries (Updated to use c.isClarified())
        for (Certificate_Enquiry c : certificateEnquiryRepository.findAll()) {
            allEnquiries.add(new AdminEnquiryDTO(
                c.getId(), "certification", c.getCertificationName(), c.getName(),
                c.getEmail(), c.getPhone(), c.getMessage(), c.isClarified()
            ));
        }

        // 3. Consulting Enquiries
        for (ConsultingEnquiry con : consultingRepository.findAll()) {
            boolean isResolved = "RESOLVED".equalsIgnoreCase(con.getStatus());
            allEnquiries.add(new AdminEnquiryDTO(
                con.getId(), "consulting", con.getServiceRequired(),
                con.getFirstName() + " " + con.getLastName(),
                con.getEmail(), con.getPhone(), con.getMessage(), isResolved
            ));
        }

        return allEnquiries;
    }

   
    @PatchMapping("/{type}/{id}/toggle")
    public ResponseEntity<?> toggleEnquiryStatus(@PathVariable String type, @PathVariable Long id) {
        
        // Handle Course Toggle
        if ("course".equalsIgnoreCase(type)) {
            return enquiryRepository.findById(id)
                .map(e -> {
                    e.setClarified(!e.isClarified());
                    enquiryRepository.save(e);
                    return ResponseEntity.ok().body("Course enquiry updated");
                }).orElse(ResponseEntity.notFound().build());
        }

        // Handle Certification Toggle
        if ("certification".equalsIgnoreCase(type)) {
            return certificateEnquiryRepository.findById(id)
                .map(c -> {
                    c.setClarified(!c.isClarified());
                    certificateEnquiryRepository.save(c);
                    return ResponseEntity.ok().body("Certification enquiry updated");
                }).orElse(ResponseEntity.notFound().build());
        }

        // Handle Consulting Toggle (Changes status string)
        if ("consulting".equalsIgnoreCase(type)) {
            return consultingRepository.findById(id)
                .map(con -> {
                    String currentStatus = con.getStatus();
                    con.setStatus("RESOLVED".equalsIgnoreCase(currentStatus) ? "PENDING" : "RESOLVED");
                    consultingRepository.save(con);
                    return ResponseEntity.ok().body("Consulting status updated");
                }).orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.badRequest().body("Invalid enquiry type: " + type);
    }
}