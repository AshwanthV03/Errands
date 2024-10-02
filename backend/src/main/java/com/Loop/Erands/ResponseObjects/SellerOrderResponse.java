package com.Loop.Erands.ResponseObjects;

import com.Loop.Erands.DTO.OrderDto.SellerOrdersDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerOrderResponse {
    private List<SellerOrdersDto> orders;
    private int pageNumber;
    private int totalPages;
    private int TotalOrders;
}
