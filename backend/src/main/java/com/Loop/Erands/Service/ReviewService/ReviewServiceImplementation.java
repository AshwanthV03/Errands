package com.Loop.Erands.Service.ReviewService;

import com.Loop.Erands.DTO.ReviewDto.AddReviewDto;
import com.Loop.Erands.Models.Product;
import com.Loop.Erands.Models.Review;
import com.Loop.Erands.Models.User;
import com.Loop.Erands.Repository.ReviewRepository;
import com.Loop.Erands.Service.ProductServices.ProductService;
import com.Loop.Erands.Service.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImplementation implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Override
    public String addReviewByProductId(AddReviewDto addReviewDto) throws Exception {
        // Fetch the user and product based on the IDs provided in the DTO
        User fetchedUser = userService.findUserById(addReviewDto.getUserID());
        Product product = productService.findProductById(addReviewDto.getProductId());


        // Create a new Review object and set its properties from the DTO
        Review review = new Review();
        review.setReviewTitle(addReviewDto.getReviewTitle());
        review.setReviewDescription(addReviewDto.getReviewDescription());
        review.setRating(addReviewDto.getRating());
        review.setProduct(product);
        review.setUser(fetchedUser);

        // Save the review to the repository and handle potential exceptions
        try {
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new Exception("Failed to save review: " + e.getMessage());
        }

        // Return a success message
        return "Review Added";
    }

    @Override
    public String deleteReviewById(UUID reviewId) throws Exception {
        // Fetch the review by its ID
        Review fetchedReview = reviewRepository.findById(reviewId).orElseThrow(()-> new Exception("Review not found for ID: " + reviewId));
        try {
            reviewRepository.deleteById(reviewId);
        } catch (Exception e) {
            throw new Exception("Failed to delete review: " + e.getMessage());
        }
        return "Review Deleted";
    }

    @Override
    public String updateReviewById(UUID reviewId, AddReviewDto addReviewDto) throws Exception {
        // Fetch the existing review from the repository
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new Exception("Review not found for ID: " + reviewId));

        // Update the review fields with the new values from AddReviewDto
        existingReview.setReviewTitle(addReviewDto.getReviewTitle());
        existingReview.setReviewDescription(addReviewDto.getReviewDescription());
        existingReview.setRating(addReviewDto.getRating());

        // Save the updated review back to the repository
        reviewRepository.save(existingReview);

        return "Review updated successfully";
    }


    @Override
    public List<Review> findReviewsByProductId(UUID productId) throws Exception {
        List<Review> fetchedReviews = reviewRepository.findByProductId(productId);
        if (fetchedReviews.isEmpty()) throw new Exception("No reviews found for this product");
        return fetchedReviews;
    }

    @Override
    public List<Review> findReviewsByUserId(UUID userId) throws Exception {
        List<Review> fetchedReviews = reviewRepository.findByUserId(userId);
        if (fetchedReviews.isEmpty()) throw new Exception("No reviews found for this user");
        return fetchedReviews;
    }
}
