package com.example.coaching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coaching.model.Certificate_Enquiry;
import com.example.coaching.model.Enquiry;
import com.example.coaching.service.EnquiryService;

@RestController
@RequestMapping("/api/enquiries")
//@CrossOrigin(origins = "http://localhost:3000")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

   
    @PostMapping
    public Enquiry createEnquiry(@RequestBody Enquiry enquiry) {
        return enquiryService.saveEnquiry(enquiry);
    } 
    
    @PostMapping("/certification")
    public ResponseEntity<Certificate_Enquiry> createCertificate_Enquiry(@RequestBody Certificate_Enquiry enquiry) {
        Certificate_Enquiry saved = enquiryService.saveEnquiry(enquiry);
        return ResponseEntity.ok(saved);
    }
}