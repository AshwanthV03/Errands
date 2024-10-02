package com.Loop.Erands.DTO.ProductDto;

import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductVariantDto {
    UUID productId;
    Colour colour;
    List<String> images;
    Size size;
    int stockCount;
    float price;
    float discount;
}
