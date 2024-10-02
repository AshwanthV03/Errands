package com.Loop.Erands.ResponseObjects;

import com.Loop.Erands.DTO.SellerDto.SellerProductsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class SellerProductsResponse {
    private  List<SellerProductsDto> products;
    private int totalPages;
    private int pageNumber;
    private int totalElements;

    public SellerProductsResponse(List<SellerProductsDto> response,int totalPages, int pageNumber, int totalElements){
        this.products = response;
        this.totalPages = totalPages;
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
    }
}
