package com.example.coaching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.coaching.model.Review;
import com.example.coaching.repository.ReviewRepository;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // 1. User submits review
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    // 2. Public: Get only approved reviews
    @GetMapping("/public")
    public List<Review> getApprovedReviews() {
        return reviewRepository.findByApprovedTrue();
    }

    // 3. Admin: Get all reviews
    @GetMapping("/admin")
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // 4. Admin: Toggle review approval
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Review> toggleApproval(@PathVariable Long id) {
        return reviewRepository.findById(id)
            .map(review -> {
                review.setApproved(!review.isApproved());
                Review updated = reviewRepository.save(review);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
