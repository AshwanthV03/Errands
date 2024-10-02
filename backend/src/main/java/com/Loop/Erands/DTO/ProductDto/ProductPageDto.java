package com.Loop.Erands.DTO.ProductDto;

import com.Loop.Erands.Models.ProductImage;
import com.Loop.Erands.Models.ProductVariant;
import com.Loop.Erands.Models.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageDto {
    private String title;
    private String description;
    private UUID ProductID;
    private UUID variantId;
    private List<ProductImage> images;
    private float price;
    private float discount;
    private int stock;
    private float rating;
    private List<Review> reviews;
    private List<ProductVariant> productVariant;

}
