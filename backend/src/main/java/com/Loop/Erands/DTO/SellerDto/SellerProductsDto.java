package com.Loop.Erands.DTO.SellerDto;

import com.Loop.Erands.Models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@NoArgsConstructor
@Data
public class SellerProductsDto {
    private UUID productId;
    private String productTitle;
    private String productDescription;

    // ProductVariant attributes
    private UUID variantId;
    private Size size;
    private Colour colour;
    private int stockCount;
    private float price;
    private float discount;
    private List<ProductImage> productImages;
    private Set<Category> categories;


    // Constructor to initialize from a Product and a ProductVariant
    public SellerProductsDto(Product product, ProductVariant variant) {
        this.productId = product.getProductId();
        this.productTitle = product.getProductTitle();
        this.productDescription = product.getProductDescription();
        this.variantId = variant.getVariantId();
        this.size = variant.getSize();
        this.colour = variant.getColour();
        this.stockCount = variant.getStockCount();
        this.price = variant.getPrice();
        this.discount = variant.getDiscount();
        this.categories = product.getCategories();
    }
    // Constructor for JPQL
    public SellerProductsDto(UUID productId, String productTitle, String productDescription,
                             UUID variantId, Size size, int stockCount, float price, float discount) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.variantId = variantId;
        this.size = size;
        this.stockCount = stockCount;
        this.price = price;
        this.discount = discount;
    }
}
