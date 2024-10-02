package com.Loop.Erands.DTO.CartDto;

import lombok.Data;

import java.util.UUID;

@Data
public class CartDto {
    private UUID userId;
    private UUID productVariantId;
    private int productCount;

}
