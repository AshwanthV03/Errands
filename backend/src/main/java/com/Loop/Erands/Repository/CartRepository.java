package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.Cart;
import com.Loop.Erands.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId")
    Optional<Cart> findCartByUserId(@Param("userId") UUID userId);

}
