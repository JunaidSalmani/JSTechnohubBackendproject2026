package com.example.coaching.service;

import com.example.coaching.dto.ConsultingEnquiryRequest;
import com.example.coaching.dto.ConsultingEnquiryResponse;
import com.example.coaching.model.ConsultingEnquiry;
import com.example.coaching.repository.ConsultingEnquiryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
import java.util.List;
 
/**
 * Business logic for consulting enquiry submissions.
 *
 * Steps:
 *  1. Duplicate check   — same email + service within 24 hours → reject
 *  2. Phone normalise   — add "+" prefix if missing
 *  3. Persist to MySQL
 *  4. Return safe response DTO
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultingEnquiryService {
 
    private final ConsultingEnquiryRepository repository;
 
    private static final int DUPLICATE_WINDOW_HOURS = 24;
 
    // ── Submit ────────────────────────────────────────────────
 
    @Transactional
    public ConsultingEnquiryResponse submit(ConsultingEnquiryRequest req) {
 
        String email = req.getEmail().toLowerCase().trim();
 
        // 1. Duplicate guard
        LocalDateTime windowStart = LocalDateTime.now().minusHours(DUPLICATE_WINDOW_HOURS);
        boolean duplicate = repository.existsByEmailAndServiceRequiredAndCreatedAtAfter(
            email,
            req.getServiceRequired(),
            windowStart
        );
        if (duplicate) {
            throw new RuntimeException(
                "An enquiry for '" + req.getServiceRequired() +
                "' was already received from this email in the last 24 hours. " +
                "Our team will contact you shortly."
            );
        }
 
        // 2. Normalise phone — react-phone-input-2 sends digits only e.g. "919876543210"
        String phone = req.getPhone().trim();
        if (!phone.startsWith("+")) {
            phone = "+" + phone;
        }
 
        // 3. Build entity
        ConsultingEnquiry entity = ConsultingEnquiry.builder()
            .firstName(req.getFirstName().trim())
            .lastName(req.getLastName().trim())
            .email(email)
            .company(req.getCompany().trim())
            .jobTitle(req.getJobTitle() != null ? req.getJobTitle().trim() : null)
            .phone(phone)
            .serviceRequired(req.getServiceRequired())
            .positionsRange(req.getPositionsRange())
            .requirementLocation(req.getRequirementLocation())
            .message(req.getMessage())
            .heardFrom(req.getHeardFrom())
            .termsAccepted(req.isTermsAccepted())
            .marketingOptedIn(req.isMarketingOptedIn())
            .build();
 
        // 4. Save to MySQL
        ConsultingEnquiry saved = repository.save(entity);
 
        // 5. Build reference number using DB-generated ID
        String ref = "CE-" + LocalDateTime.now().getYear() + "-" +
                     String.format("%05d", saved.getId());
 
        log.info("Consulting enquiry saved — id={}, ref={}, service={}, email={}",
            saved.getId(), ref, saved.getServiceRequired(), saved.getEmail());
 
        // 6. Return safe response
        return ConsultingEnquiryResponse.builder()
            .id(saved.getId())
            .referenceNumber(ref)
            .firstName(saved.getFirstName())
            .serviceRequired(saved.getServiceRequired())
            .confirmationMessage(
                "Thank you, " + saved.getFirstName() + "! " +
                "Your enquiry for '" + saved.getServiceRequired() + "' has been received. " +
                "A senior account manager from " + saved.getCompany() +
                " will contact you within one business day. Reference: " + ref
            )
            .build();
    }
 
    // ── Admin helpers ─────────────────────────────────────────
 
    @Transactional(readOnly = true)
    public List<ConsultingEnquiry> getAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
 
    @Transactional(readOnly = true)
    public List<ConsultingEnquiry> getByStatus(String status) {
        return repository.findByStatusOrderByCreatedAtDesc(status.toUpperCase());
    }
}