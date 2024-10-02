package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.CartItemID = :cartItemID")
    void deleteByCartItemID(@Param("cartItemID") UUID cartItemID);

}
