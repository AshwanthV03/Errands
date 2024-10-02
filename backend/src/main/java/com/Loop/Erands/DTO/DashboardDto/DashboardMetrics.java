package com.Loop.Erands.DTO.DashboardDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardMetrics {
    private float revenue;
    private int orders;
    private int productsCount;
    private int outOfStockCount;
}
