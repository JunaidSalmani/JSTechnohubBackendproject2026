package com.example.coaching.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * Maps to table: consulting_enquiries
 * Stores every B2B enquiry from ConsultingServicesPage.jsx
 */

@Entity
@Table(name="consulting_enquiries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class ConsultingEnquiry {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    // ── Step 1 : Organisation Details ────────────────────────
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;
 
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;
 
    @Column(name = "email", nullable = false, length = 254)
    private String email;
 
    @Column(name = "company", nullable = false, length = 120)
    private String company;
 
    @Column(name = "job_title", length = 100)
    private String jobTitle;               // optional
 
    // ── Step 2 : Requirement & Contact ───────────────────────
    /**
     * react-phone-input-2 sends digits only, e.g. "919876543210"
     * We add "+" prefix before saving → "+919876543210"
     */
    @Column(name = "phone", nullable = false, length = 25)
    private String phone;
 
    @Column(name = "service_required", nullable = false, length = 100)
    private String serviceRequired;        // matches SERVICES[].title exactly
 
    @Column(name = "positions_range", length = 30)
    private String positionsRange;         // "1 – 10" / "10 – 50" etc.  optional
 
    @Column(name = "requirement_location", length = 150)
    private String requirementLocation;    // optional
 
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;               // optional, max 1000 chars
 
    @Column(name = "heard_from", length = 60)
    private String heardFrom;             // optional
 
    // ── Consent ──────────────────────────────────────────────
    @Column(name = "terms_accepted", nullable = false)
    private boolean termsAccepted;        
 
    @Column(name = "marketing_opted_in", nullable = false)
    private boolean marketingOptedIn;     // optional
 
    // ── Status (internal CRM) ─────────────────────────────────
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private String status = "NEW";        // NEW | CONTACTED | IN_PROGRESS | CONVERTED | CLOSED
 
    // ── Audit ─────────────────────────────────────────────────
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
