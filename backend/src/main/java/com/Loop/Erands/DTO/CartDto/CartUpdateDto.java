package com.Loop.Erands.DTO.CartDto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartUpdateDto {
    private UUID CartItemId;
    private UUID userId;
    private int itemUpdatedCount;

}
