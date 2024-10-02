package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    @Query("SELECT r FROM Review r JOIN FETCH r.product p JOIN FETCH r.user u WHERE p.productId = :productId")
    List<Review> findByProductId(@Param("productId") UUID productId);

    @Query("SELECT r FROM Review r JOIN FETCH r.user u WHERE u.userId = :userId")
    List<Review> findByUserId(@Param("userId") UUID userId);
}
