package com.Loop.Erands.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderHistoryId;
    private UUID OrdersId;
    private String status;
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "variantId")
    private ProductVariant productVariant;
    @ManyToOne
    private Address orderAddress;
    private int count;
    private float orderAmount;
    private Date orderDate;

    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @PrePersist
    private void onCreate() {
        timeStamp = new Date();
    }

    public OrderHistory(UUID ordersId, String status, User user, ProductVariant productVariant, Address orderAddress, int count, float orderAmount, Date orderDate) {
        OrdersId = ordersId;
        this.status = status;
        this.user = user;
        this.productVariant = productVariant;
        this.orderAddress = orderAddress;
        this.count = count;
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
    }
}
