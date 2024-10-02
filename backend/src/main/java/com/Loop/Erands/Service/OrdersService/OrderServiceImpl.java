package com.Loop.Erands.Service.OrdersService;

import com.Loop.Erands.DTO.CartDto.CartItemDto;
import com.Loop.Erands.DTO.OrderDto.NewOrderDto;
import com.Loop.Erands.DTO.OrderDto.SellerOrdersDto;
import com.Loop.Erands.DTO.SellerDto.SellerOrderHistoryDto;
import com.Loop.Erands.Models.*;
import com.Loop.Erands.Repository.AddressRepository;
import com.Loop.Erands.Repository.OrderHistoryRepository;
import com.Loop.Erands.Repository.OrdersRepository;
import com.Loop.Erands.Repository.ProductVariantRepository;
import com.Loop.Erands.ResponseObjects.SellerOrderResponse;
import com.Loop.Erands.Service.Cart.CartService;
import com.Loop.Erands.Service.ProductServices.ProductService;
import com.Loop.Erands.Service.UserService.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ProductVariantRepository productVariantRepository;
    @Autowired
    CartService cartService;
    @Autowired
    OrderHistoryRepository orderHistoryRepository;


    @Override
    @Transactional
    public String placeOrder(NewOrderDto newOrderDto) throws Exception {
        // Find the user based on the provided user ID
        User user = userService.findUserById(newOrderDto.getUserId());
        // Find the product variant based on the provided variant ID
        ProductVariant productVariant = productService.findProductVariantById(newOrderDto.getVariantId());
        // Find the address based on the provided address ID
        Address address = addressRepository.findById(newOrderDto.getAddressId())
                .orElseThrow(() -> new Exception("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setProductVariant(productVariant);
        order.setOrderAddress(address);
        order.setCount(newOrderDto.getCount());
        order.setOrderAmount(newOrderDto.getBillingAmount());

        // Find the address based on the provided address ID
        if (productVariant.getStockCount() >= newOrderDto.getCount()) {
            productVariant.decrementStockCount(newOrderDto.getCount());
        } else {
            throw new Exception("Insufficient stock, reduce quantity or try later");
        }

        try{
            // Save the updated product variant and new order
            productVariantRepository.save(productVariant);
            ordersRepository.save(order);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

        return "Order placed";
    }

    @Override
    @Transactional
    public String placeOrders(UUID userId, UUID addressId) throws Exception {
        // Retrieve cart items for the user
        List<CartItemDto> cartItems = cartService.getCart(userId);
        // Find the address for the orders
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new Exception("Address not found"));
        User user = userService.findUserById(userId);

        List<ProductVariant> updatedProductVariants = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        // Loop through cart items to create individual orders
        for (CartItemDto cartItem : cartItems) {
            ProductVariant productVariant = productService.findProductVariantById(cartItem.getVariantId());

            // Check stock availability
            if (productVariant.getStockCount() < cartItem.getCount()) {
                throw new Exception("Insufficient stock for product variant: " + productVariant.getVariantId());
            }

            // Update stock count for the product variant
            productVariant.decrementStockCount(cartItem.getCount());
            updatedProductVariants.add(productVariant);

            Order order = new Order();
            order.setUser(user);
            order.setProductVariant(productVariant);
            order.setOrderAddress(address);
            order.setCount(cartItem.getCount());
            order.setOrderAmount(cartItem.getPrice());

            orders.add(order);
        }
        // Save all updated product variants and orders
        productVariantRepository.saveAll(updatedProductVariants);
        ordersRepository.saveAll(orders);
        // Clear the user's cart after placing the orders
        cartService.clearCart(user.getCarts().getCartId());

        return "Orders placed";
    }

    @Override
    public String cancelOrder(UUID userId, UUID orderId) throws Exception {
        // Find the user based on the provided user ID
        User user = userService.findUserById(userId);
        // Retrieve the order based on the provided order ID
        Optional<Order> order = ordersRepository.findById(orderId);

        if(!order.isPresent()) throw new Exception("Order does not exist");
        if(order.get().getUser().getUserId() != userId) throw new Exception("You cannot delete this order");

        try{
            // Delete the order from the repository
            ordersRepository.deleteById(orderId);
        }catch (Exception e){
            throw new Exception(e);
        }
        return "Order deleted successfully";
    }

    @Override
    public Order findOrderById(UUID orderId) throws Exception {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order does not exist"));
    }

    @Override
    public List<Order> findAllOrderByUserId(UUID userId) throws Exception {
        return ordersRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("User has no orders"));
    }

    @Override
    public SellerOrderResponse findAllOrderBySellerId(UUID sellerId, int pageNumber, int pageSize) {

        // Pagination
        Pageable page = PageRequest.of(pageNumber,pageSize);
        // Find Orders associated with SellerId
        Page<Order> fetchedOrders = ordersRepository.findBySellerId(sellerId,page);

        // Map fetched orders to SellerOrdersDto
        List<SellerOrdersDto> sellerOrders = fetchedOrders.stream()
                .map(o -> new SellerOrdersDto(
                        o.getOrderId(),
                        o.getProductVariant().getVariantId(),
                        o.getProductVariant().getProduct().getProductId(),
                        o.getProductVariant().getProduct().getProductTitle(),
                        o.getCount(),
                        o.getProductVariant().getPrice(),
                        o.getOrderAmount(),
                        o.getProductVariant().getImages().size() > 0
                                ? o.getProductVariant().getImages().stream().toList().get(0).getImageUrl()
                                : "",
                        o.getOrderAddress()
                ))
                .collect(Collectors.toList());

        return new SellerOrderResponse(sellerOrders, fetchedOrders.getNumber(), fetchedOrders.getTotalPages(),fetchedOrders.getNumberOfElements());
    }

    @Override
    @Transactional
    public String handleOrder(UUID orderId, String status) throws Exception {
        Optional<Order> fetchedOrder = ordersRepository.findById(orderId);
        if (!fetchedOrder.isPresent()) throw new Exception("Order not found");

        OrderHistory orderHistory = new OrderHistory(
                fetchedOrder.get().getOrderId(),
                status,
                fetchedOrder.get().getUser(),
                fetchedOrder.get().getProductVariant(),
                fetchedOrder.get().getOrderAddress(),
                fetchedOrder.get().getCount(),
                fetchedOrder.get().getOrderAmount(),
                fetchedOrder.get().getTimeStamp()
        );
        try {
            orderHistoryRepository.save(orderHistory);
            ordersRepository.deleteById(orderId);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return "order " + status + "ed";
    }

    @Override
    public List<SellerOrderHistoryDto> filterOrderHistory(String status, UUID userId, Date from, Date to) throws Exception {
        List<OrderHistory> fetchedOrderHistory = orderHistoryRepository.filterHistory(status, userId, from, to);

        if (fetchedOrderHistory.isEmpty()) {
            return Collections.emptyList();
        }

        return fetchedOrderHistory.stream()
                .map(oh -> new SellerOrderHistoryDto(oh.getProductVariant().getProduct().getProductTitle(), oh))
                .collect(Collectors.toList());
    }

    @Override
    public float salesBySeller(UUID sellerId, String status, Date from, Date to) throws Exception {
        List<SellerOrderHistoryDto> sellerOrderHistory = filterOrderHistory(status, sellerId, from, to);

        return (float) sellerOrderHistory.stream()
                .filter(oh -> "completed".equals(oh.getOrderHistory().getStatus()))
                .mapToDouble(oh -> oh.getOrderHistory().getOrderAmount())
                .sum();
    }




}
