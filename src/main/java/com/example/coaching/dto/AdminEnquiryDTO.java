package com.example.coaching.dto;

public class AdminEnquiryDTO {
    private Long id;
    private String type; 
    private String title; 
    private String name;
    private String email;
    private String phone;
    private String message;
    private boolean clarified;
    private String status;

    // Default Constructor
    public AdminEnquiryDTO() {}

    // Constructor A: For Courses & Certifications (receives boolean)
    public AdminEnquiryDTO(Long id, String type, String title, String name, String email, String phone, String message, boolean clarified) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.clarified = clarified;
        this.status = clarified ? "CLARIFIED" : "PENDING";
    }

    // Constructor B: For Consulting (receives String status)
    public AdminEnquiryDTO(Long id, String type, String title, String name, String email, String phone, String message, String status) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
        this.status = status;
        this.clarified = "CONVERTED".equalsIgnoreCase(status) || "CLOSED".equalsIgnoreCase(status);
    }

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isClarified() { return clarified; }
    public void setClarified(boolean clarified) { this.clarified = clarified; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}