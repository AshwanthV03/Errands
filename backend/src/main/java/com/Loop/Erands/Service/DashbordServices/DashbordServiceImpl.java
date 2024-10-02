package com.Loop.Erands.Service.DashbordServices;

import com.Loop.Erands.DTO.DashboardDto.DashboardMetrics;
import com.Loop.Erands.ResponseObjects.SellerOrderResponse;
import com.Loop.Erands.Service.OrdersService.OrderService;
import com.Loop.Erands.Service.ProductServices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class DashbordServiceImpl implements DashbordService {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Override
    public DashboardMetrics getMetrics(UUID sellerId, Date from, Date to) throws Exception {
        float revenue = orderService.salesBySeller(sellerId,"completed",from, to );
        int productsCount = productService.findProductVariantsBySellerId(sellerId,0,10).getTotalElements();
        SellerOrderResponse sr = orderService.findAllOrderBySellerId(sellerId,0,100);
        int ordersCount = sr.getTotalOrders();
        int outOfStock = productService.FindProductsByStock(0).size();

        return new DashboardMetrics(revenue,ordersCount,productsCount,outOfStock);
    }
}
