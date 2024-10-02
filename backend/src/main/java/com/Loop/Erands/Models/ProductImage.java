package com.Loop.Erands.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductImage {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID productImageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variant_Id")
    @JsonBackReference
    private ProductVariant productVariant;
    private String imageUrl;

    public ProductImage(ProductVariant productVariant, String imageUrl){
        this.imageUrl = imageUrl;
        this.productVariant = productVariant;
    }
}
