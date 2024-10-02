package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WishListRepository extends JpaRepository<WishList, UUID> {
}
