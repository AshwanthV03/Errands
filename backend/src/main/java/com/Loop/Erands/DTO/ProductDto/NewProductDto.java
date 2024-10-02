package com.Loop.Erands.DTO.ProductDto;

import com.Loop.Erands.Models.Category;
import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.Size;
import lombok.*;


import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewProductDto {
    private UUID sellerId;
    private String productTitle;
    private String productDescription;
    private float price;
    private float discount;
    private Size size;
    Colour colour;
    private  int stockCount;
    private List<Category> categories;
    private List<String> imageUrls;
}
