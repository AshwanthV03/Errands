package com.Loop.Erands.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID CartId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    @ToString.Exclude
    private  User user;

    public void addCartItem(CartItem cartItem){
        this.cartItems.add(cartItem);
    }

    public void clearCart(){
        this.cartItems.clear();
    }

}
