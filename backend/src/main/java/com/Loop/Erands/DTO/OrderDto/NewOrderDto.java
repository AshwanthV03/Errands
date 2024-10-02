package com.Loop.Erands.DTO.OrderDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class NewOrderDto {
    private UUID userId;
    private UUID variantId;
    private UUID addressId;
    private int count;
    private float billingAmount;
}
