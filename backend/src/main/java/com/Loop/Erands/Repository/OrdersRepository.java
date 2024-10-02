package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.Order;
import com.Loop.Erands.Models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.user.userId = :UserId")
    Optional<List<Order>> findByUserId(@Param("UserId") UUID UserId);

    @Query("SELECT o FROM Order o JOIN o.productVariant pv WHERE pv.product.seller.userId = :sellerId")
    Page<Order> findBySellerId(@Param("sellerId") UUID sellerId, Pageable page);

    // Method to delete an order by ID
    @Modifying
    @Transactional
    @Query("DELETE FROM Order o WHERE o.id = :orderId")
    void deleteById(@Param("orderId") UUID orderId);
}

