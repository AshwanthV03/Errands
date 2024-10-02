package com.Loop.Erands.DTO.ProductDto;

import com.Loop.Erands.Models.Category;
import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Data
@Getter
@Setter
public class UpdateProductDto {
    private String productId;
    private String productVariantId;
    private String productTitle;
    private String productDescription;
    private float price;
    private float discount;
    private Size size;
    private Colour colour;
    private int stockCount;
    private List<Category> categories;

}


//              size: size,
//              colour: 'BLACK',
//              stockCount: stockCount,
//
//