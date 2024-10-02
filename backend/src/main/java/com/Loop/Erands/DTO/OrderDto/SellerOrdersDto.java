package com.Loop.Erands.DTO.OrderDto;

import com.Loop.Erands.Models.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellerOrdersDto {
    private UUID orderId;
    private UUID variantId;
    private UUID productId;
    private String ProductTitle;
    private int quantity;
    private float amount;
    private float billedAmount;
    private String image;
    private Address address;

}
