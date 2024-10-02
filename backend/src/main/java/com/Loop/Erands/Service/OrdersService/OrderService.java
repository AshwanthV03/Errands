package com.Loop.Erands.Service.OrdersService;

import com.Loop.Erands.DTO.OrderDto.NewOrderDto;
import com.Loop.Erands.DTO.OrderDto.SellerOrdersDto;
import com.Loop.Erands.DTO.SellerDto.SellerOrderHistoryDto;
import com.Loop.Erands.Models.Order;
import com.Loop.Erands.Models.OrderHistory;
import com.Loop.Erands.Models.ProductVariant;
import com.Loop.Erands.Models.User;
import com.Loop.Erands.ResponseObjects.SellerOrderResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;
public interface OrderService {
    /**
     * Places a new order based on the provided order details.
     *
     * @param newOrderDto Data Transfer Object containing order details.
     * @return A message indicating the result of the order placement.
     * @throws Exception if there is an error during order placement.
     */
    public String placeOrder(NewOrderDto newOrderDto) throws Exception;

    /**
     * Places multiple orders for a specific user and address.
     *
     * @param userId ID of the user placing the orders.
     * @param addressId ID of the address where the orders will be delivered.
     * @return A message indicating the result of the order placement.
     * @throws Exception if there is an error during order placement.
     */
    public String placeOrders(UUID userId, UUID addressId) throws Exception;

    /**
     * Cancels an existing order for a specific user.
     *
     * @param userId ID of the user requesting the cancellation.
     * @param orderId ID of the order to be canceled.
     * @return A message indicating the result of the cancellation.
     * @throws Exception if there is an error during the cancellation process.
     */
    public String cancelOrder(UUID userId, UUID orderId) throws Exception;

    /**
     * Finds and retrieves the details of an order by its ID.
     *
     * @param orderId ID of the order to be retrieved.
     * @return The Order object representing the order details.
     * @throws Exception if the order is not found or there is an error retrieving it.
     */
    public Order findOrderById(UUID orderId) throws Exception;

    /**
     * Retrieves all orders associated with a specific user.
     *
     * @param userId ID of the user whose orders are to be retrieved.
     * @return A list of Order objects representing the user's orders.
     * @throws Exception if there is an error retrieving the orders.
     */
    public List<Order> findAllOrderByUserId(UUID userId) throws Exception;

    /**
     * Retrieves all orders associated with a specific seller, with pagination support.
     *
     * @param sellerId ID of the seller whose orders are to be retrieved.
     * @param pageNumber The page number for pagination.
     * @param pageSize The number of orders per page.
     * @return Response object containing a list of seller orders and pagination info.
     */
    public SellerOrderResponse findAllOrderBySellerId(UUID sellerId, int pageNumber, int pageSize);

    /**
     * Handles order status updates and saves the order history.
     *
     * @param orderId ID of the order to be processed.
     * @param status The new status to be set for the order (e.g., "completed", "canceled").
     * @return A message indicating the order has been updated with the new status.
     * @throws Exception if the order is not found or if there is an error during processing.
     */
    public String handleOrder(UUID orderId, String status) throws Exception;

    /**
     * Calculates the total sales amount for a specific seller based on the order history.
     *
     * @param sellerId ID of the seller whose sales are to be calculated.
     * @param status The status of the orders to consider (e.g., "completed").
     * @param from The start date for filtering sales.
     * @param to The end date for filtering sales.
     * @return The total sales amount for the seller during the specified period.
     * @throws Exception if there is an error during the sales calculation.
     */
    public float salesBySeller(UUID sellerId, String status, Date from, Date to) throws Exception;

    /**
     * Filters the order history based on the specified criteria.
     *
     * @param status The status of the orders to filter (e.g., "completed", "pending").
     * @param userId ID of the user whose order history is to be retrieved.
     * @param from The start date for filtering orders.
     * @param to The end date for filtering orders.
     * @return A list of SellerOrderHistoryDto objects representing the filtered order history.
     * @throws Exception if there is an error during filtering.
     */
    public List<SellerOrderHistoryDto> filterOrderHistory(String status, UUID userId, Date from, Date to) throws Exception;
}
