package com.Loop.Erands.DTO.ProductDto;

import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductsDto {
    private UUID productId;
    private UUID variantId;
    private List<ProductImage> images;
    private String productTitle;
    private String productDescription;
    private Colour colour;
    private float price;
    private float discount;
    private Date date;
}
