package com.Loop.Erands.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID CartItemID;
    @OneToOne
    @JoinColumn(name = "productVariantId")
    private ProductVariant productVariant;
    private int itemCount;
}
