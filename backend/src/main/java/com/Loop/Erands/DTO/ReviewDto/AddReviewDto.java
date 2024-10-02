package com.Loop.Erands.DTO.ReviewDto;

import com.Loop.Erands.Models.Product;
import com.Loop.Erands.Models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddReviewDto {
    private String reviewTitle;
    private String reviewDescription;
    private float rating;
    private UUID productId;
    private UUID userID;
}
