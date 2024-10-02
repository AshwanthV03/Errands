package com.Loop.Erands.Controller;

import com.Loop.Erands.DTO.ReviewDto.AddReviewDto;
import com.Loop.Erands.Models.Review;
import com.Loop.Erands.Service.ReviewService.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/addReview/{productId}")
    public ResponseEntity<String> addReview(
            @PathVariable("productId") UUID productId,
            @RequestBody AddReviewDto addReviewDto) {
        try {
            addReviewDto.setProductId(productId); // Set the productId in the DTO
            String response = reviewService.addReviewByProductId(addReviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") UUID reviewId) {
        try {
            String response = reviewService.deleteReviewById(reviewId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updateReview/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable("reviewId") UUID reviewId, @RequestBody AddReviewDto addReviewDto) {
        try {
            String response = reviewService.updateReviewById(reviewId,addReviewDto); // Assuming you implement this method
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> findReviewsByProductId(@PathVariable("productId") UUID productId) {
        try {
            List<Review> reviews = reviewService.findReviewsByProductId(productId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> findReviewsByUserId(@PathVariable("userId") UUID userId) {
        try {
            List<Review> reviews = reviewService.findReviewsByUserId(userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
