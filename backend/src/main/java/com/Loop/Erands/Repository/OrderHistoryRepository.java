package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, UUID> {
    @Query("SELECT oh FROM OrderHistory oh " +
            "JOIN oh.productVariant pv " +
            "JOIN pv.product p " +
            "WHERE p.seller.userId = :sellerId " +
            "AND (:status IS NULL OR oh.status = :status) " +
            "AND (:fromDate IS NULL OR oh.timeStamp >= :fromDate) " +
            "AND (:toDate IS NULL OR oh.timeStamp <= :toDate)")
    public List<OrderHistory> filterHistory(
            @Param("status") String status,
            @Param("sellerId") UUID sellerId,
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT oh FROM OrderHistory oh WHERE oh.productVariant.product.seller.userId = :sellerId")
    List<OrderHistory> findOrdersBySeller(@Param("sellerId") UUID sellerId);


}
//@Query("SELECT oh FROM OrderHistory oh " +
//        "WHERE oh.productVariant.product.seller.userId = :sellerId " +
//        "AND (oh.status IS NULL OR oh.status = :status) " +
//        "AND (:fromDate IS NULL OR oh.timeStamp >= :fromDate) " +
//        "AND (:toDate IS NULL OR oh.timeStamp <= :toDate) ")