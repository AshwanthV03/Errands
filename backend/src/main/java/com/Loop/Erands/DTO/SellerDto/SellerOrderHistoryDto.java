package com.Loop.Erands.DTO.SellerDto;

import com.Loop.Erands.Models.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerOrderHistoryDto {
    private String ProductTitle;
    private OrderHistory orderHistory;
}
