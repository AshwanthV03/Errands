package com.Loop.Erands.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(name = "product_title", nullable = false)
    private String productTitle;

    @Column(name = "product_description", nullable = false,length = 1000)
    private String productDescription;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProductVariant> productVariants = new ArrayList<>();

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 255)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private User seller;

    // Method to add a variant and set the product
    public void addVariant(ProductVariant variant) {
        variant.setProduct(this); // Ensure bidirectional relationship
        this.productVariants.add(variant);
    }
    public void addCategories(List<Category> newCategories) {
        if (newCategories != null) {
            newCategories.stream()
                    .filter(Objects::nonNull) // Filter out any null values
                    .forEach(this.categories::add); // Add each category to the set
        }
    }

    // Optional: Overload the method to add a single category
    public void addCategory(Category category) {
        if (category != null) {
            this.categories.add(category);
        }
    }
}
