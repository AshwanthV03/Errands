package com.Loop.Erands.Service.ReviewService;

import com.Loop.Erands.DTO.ReviewDto.AddReviewDto;
import com.Loop.Erands.Models.Review;

import java.util.List;
import java.util.UUID;

/**
 * Interface for managing reviews related to products.
 * This service provides methods to add, delete, update, and retrieve reviews.
 */
public interface ReviewService {

    /**
     * Adds a review for a specific product.
     *
     * @param addReviewDto The DTO containing the details of the review to be added.
     * @return A success message indicating the review was added.
     * @throws Exception if an error occurs while adding the review.
     */
    public String addReviewByProductId(AddReviewDto addReviewDto) throws Exception;

    /**
     * Deletes a review by its unique ID.
     *
     * @param reviewId The UUID of the review to be deleted.
     * @return A success message indicating the review was deleted.
     * @throws Exception if the review is not found or an error occurs while deleting it.
     */
    public String deleteReviewById(UUID reviewId) throws Exception;

    /**
     * Updates an existing review by its unique ID.
     *
     * @param reviewId The UUID of the review to be updated.
     * @return A message indicating the result of the update operation.
     */
    public String updateReviewById(UUID reviewId,AddReviewDto addReviewDto) throws Exception;

    /**
     * Retrieves a list of reviews for a specific product.
     *
     * @param productId The UUID of the product for which reviews are being fetched.
     * @return A list of reviews associated with the specified product.
     * @throws Exception if no reviews are found for the product or an error occurs during retrieval.
     */
    public List<Review> findReviewsByProductId(UUID productId) throws Exception;

    /**
     * Retrieves a list of reviews made by a specific user.
     *
     * @param userId The UUID of the user for whom reviews are being fetched.
     * @return A list of reviews associated with the specified user.
     * @throws Exception if no reviews are found for the user or an error occurs during retrieval.
     */
    public List<Review> findReviewsByUserId(UUID userId) throws Exception;
}
