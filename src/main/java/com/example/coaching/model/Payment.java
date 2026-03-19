// package com.example.coaching.model;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;
// import lombok.Builder;

// @Entity
// @Builder
// public class Payment {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String razorpayOrderId;
//     private String razorpayPaymentId;
//     private String razorpaySignature;
//     private Double amount;
    
//     @ManyToOne
//     private User user;

// 	public Long getId() {
// 		return id;
// 	}

// 	public void setId(Long id) {
// 		this.id = id;
// 	}

// 	public String getRazorpayOrderId() {
// 		return razorpayOrderId;
// 	}

// 	public void setRazorpayOrderId(String razorpayOrderId) {
// 		this.razorpayOrderId = razorpayOrderId;
// 	}

// 	public String getRazorpayPaymentId() {
// 		return razorpayPaymentId;
// 	}

// 	public void setRazorpayPaymentId(String razorpayPaymentId) {
// 		this.razorpayPaymentId = razorpayPaymentId;
// 	}

// 	public String getRazorpaySignature() {
// 		return razorpaySignature;
// 	}

// 	public void setRazorpaySignature(String razorpaySignature) {
// 		this.razorpaySignature = razorpaySignature;
// 	}

// 	public Double getAmount() {
// 		return amount;
// 	}

// 	public void setAmount(Double amount) {
// 		this.amount = amount;
// 	}

// 	public User getUser() {
// 		return user;
// 	}

// 	public void setUser(User user) {
// 		this.user = user;
// 	}
// }
