package com.example.coaching.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.coaching.model.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByApprovedTrue(); // only approved reviews
}
