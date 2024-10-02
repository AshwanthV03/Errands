package com.Loop.Erands.DTO.CartDto;

import com.Loop.Erands.Models.ProductImage;
import com.Loop.Erands.Models.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CartItemDto {
    private UUID cartItem;
    private UUID variantId;
    private UUID productId;
    private List<ProductImage> image ;
    private String title;
    private int count ;
    private int quantity;
    private float price;
    private Size size;
}
