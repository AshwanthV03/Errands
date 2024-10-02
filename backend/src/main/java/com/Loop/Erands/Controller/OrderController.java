package com.Loop.Erands.Controller;

import com.Loop.Erands.DTO.OrderDto.NewOrderDto;
import com.Loop.Erands.DTO.SellerDto.SellerOrderHistoryDto;
import com.Loop.Erands.Models.Order;
import com.Loop.Erands.Models.OrderHistory;
import com.Loop.Erands.Service.OrdersService.OrderService;
import com.Loop.Erands.Service.OrdersService.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<String>placeOrder(@RequestBody NewOrderDto newOrderDto) throws Exception {
        String response;
            try{
                 response = orderService.placeOrder(newOrderDto);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/placeOrders/{userId}/{addressId}")
    public ResponseEntity<String> placeOrders(@PathVariable("userId") UUID userId, @PathVariable("addressId") UUID addressId){
        String response;

        try{
            response = orderService.placeOrders(userId,addressId);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
         return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> cancelOrder(@PathVariable("userId")UUID userId, @PathVariable("productId") UUID productId) throws Exception {
        String response;
        try{
            response = orderService.cancelOrder(userId,productId);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<?> findOrderById(@PathVariable("orderId") UUID orderId) throws Exception {
        Order fetchedOrders;
        try {
            fetchedOrders = orderService.findOrderById(orderId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedOrders,HttpStatus.OK);
    }
    @GetMapping("/getAllOrders/{userId}")
    public ResponseEntity<?> getUsersAllOrders(@PathVariable("userId") UUID userId) throws Exception {
        List<Order> fetchedOrders;
        try {
           fetchedOrders = orderService.findAllOrderByUserId(userId);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(fetchedOrders,HttpStatus.OK);
    }

    @GetMapping("/orders/history/{userId}")
    public ResponseEntity<?> getOrderHistory(
            @PathVariable("userID")UUID userID,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "from", required = false) Date from,
            @RequestParam(value = "to",required = false) Date to
            ){
        try{
            List<SellerOrderHistoryDto> response = orderService.filterOrderHistory(status,userID,from,to);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }
    }
}
