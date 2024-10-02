package com.Loop.Erands.DTO.UserDto;

import com.Loop.Erands.Models.Cart;
import com.Loop.Erands.Models.CartItem;
import com.Loop.Erands.Models.Order;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LoginResponseDto {
    private String userName;
    private UUID userId;
    private List<CartItem> userCart;
    private List<Order> userOrder;

    private String token;
}
