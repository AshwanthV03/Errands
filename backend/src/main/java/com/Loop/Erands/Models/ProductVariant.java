package com.Loop.Erands.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID variantId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "p_id", nullable = false)
    @JsonBackReference // Use JsonManagedReference in Product entity for the corresponding field
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ProductImage> images = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Colour colour;
    private int stockCount;

    @NotNull
    private float price;

    @Column(columnDefinition = "FLOAT DEFAULT 0.0")
    private float discount;
    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @PrePersist
    private void onCreate() {
        timeStamp = new Date();
    }


    // Adds images to the product variant and sets the reference back to the variant
    public void addImages(List<ProductImage> imageUrls) {
        imageUrls.stream()
                .filter(Objects::nonNull)
                .forEach(image -> {
                    image.setProductVariant(this);
                    this.images.add(image);
                });
    }

    // Decrements stock count, ensuring it does not go below zero
    public void decrementStockCount(int count) {
        if (this.stockCount > 0) {
            this.stockCount-=count;
        }
    }
}
