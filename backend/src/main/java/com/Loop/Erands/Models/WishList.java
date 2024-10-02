package com.Loop.Erands.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID wishListId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToMany
    @JoinTable(name = "wishlist_Variant")
    private List<ProductVariant> productVariants = new ArrayList<>();
}
