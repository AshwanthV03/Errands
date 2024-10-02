package com.Loop.Erands.Controller;

import com.Loop.Erands.DTO.DashboardDto.DashboardMetrics;
import com.Loop.Erands.DTO.OrderDto.SellerOrdersDto;
import com.Loop.Erands.DTO.ProductDto.NewProductDto;
import com.Loop.Erands.DTO.ProductDto.ProductVariantDto;
import com.Loop.Erands.DTO.ProductDto.UpdateProductDto;
import com.Loop.Erands.DTO.SellerDto.HandleOrderDto;
import com.Loop.Erands.DTO.SellerDto.SellerOrderHistoryDto;
import com.Loop.Erands.Models.OrderHistory;
import com.Loop.Erands.ResponseObjects.SellerOrderResponse;
import com.Loop.Erands.ResponseObjects.SellerProductsResponse;
import com.Loop.Erands.Service.DashbordServices.DashbordService;
import com.Loop.Erands.Service.OrdersService.OrderService;
import com.Loop.Erands.Service.ProductServices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    OrderService orderService;
    @Autowired
    DashbordService dashbordService;
    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody NewProductDto newProductDto) throws Exception {
        String response = productService.createNewProduct(newProductDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/createProducts")
    public ResponseEntity<String> createProducts(@RequestBody List<NewProductDto> newProductDtos) throws Exception {
        String response = productService.createNewProducts(newProductDtos);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/addVariant")
    public ResponseEntity<String> addProductVariant(@RequestBody ProductVariantDto productVariantDto) throws Exception {
        String response = productService.addProductVariant(productVariantDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProductById(@PathVariable UUID productId, @RequestBody UpdateProductDto updateProductDto) throws Exception {
        String response = productService.updateProductById(productId, updateProductDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID productId) throws Exception {
        String response = productService.deleteByProductId(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/deleteVariant/{variantId}")
    public ResponseEntity<String> deleteVariantId(@PathVariable UUID variantId) throws Exception{
        String response;
        try{
            response = productService.deleteByVariantId(variantId);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/getProducts/{sellerId}")
    public ResponseEntity<SellerProductsResponse> getProductBySellerId(
            @PathVariable UUID sellerId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size",defaultValue = "10", required = false) int size
    ) throws Exception {
        SellerProductsResponse fetchedProducts = productService.findProductVariantsBySellerId(sellerId,page,size);
        return new ResponseEntity<>(fetchedProducts, HttpStatus.OK);
    }

    @GetMapping("/metrics/{sellerId}")
    public ResponseEntity<?> getMMetrics(
            @PathVariable("sellerId") UUID sellerId,
            @RequestParam(value = "from",required = false) Date from,
            @RequestParam(value = "to", required = false) Date to
    ) throws Exception {
        DashboardMetrics response;
        try{
            response = dashbordService.getMetrics(sellerId,from,to);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    @GetMapping("/orders/{sellerId}")
    public ResponseEntity<?> getOrders(
            @PathVariable("sellerId")UUID sellerId,
            @RequestParam(value = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(value = "size", defaultValue = "100", required = false) int size
    ){
        SellerOrderResponse response;
        try{
            response = orderService.findAllOrderBySellerId(sellerId,page,size);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/handleOrder/{orderId}")
    public ResponseEntity<?> handleOrder(@PathVariable("orderId") UUID orderrId, @RequestBody HandleOrderDto status){
        String response;
        try{
            response = orderService.handleOrder(orderrId,status.getStatus());
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/orderHistory/{sellerId}")
    public ResponseEntity<?> getOrderHistory(
            @PathVariable("sellerId") UUID sellerId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "from", required = false)Date from,
            @RequestParam(value = "to", required = false) Date to
    ){
        List<SellerOrderHistoryDto> response;
        try {
            response = orderService.filterOrderHistory(status,sellerId,from,to);
        }catch (Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
