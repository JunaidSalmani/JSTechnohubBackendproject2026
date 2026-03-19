package com.example.coaching.repository;

import  com.example.coaching.model.ConsultingEnquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository — all CRUD auto-generated.
 */
@Repository
public interface ConsultingEnquiryRepository
        extends JpaRepository<ConsultingEnquiry, Long> {

    /**
     * Duplicate guard.
     * Checks if the same email already submitted an enquiry for the
     * same service within the last N hours (prevents spam / double submission).
     */
    boolean existsByEmailAndServiceRequiredAndCreatedAtAfter(
        String email,
        String serviceRequired,
        LocalDateTime after
    );

    /**
     * Admin: all enquiries newest first.
     */
    List<ConsultingEnquiry> findAllByOrderByCreatedAtDesc();

    /**
     * Admin: filter by status.
     */
    List<ConsultingEnquiry> findByStatusOrderByCreatedAtDesc(String status);
}