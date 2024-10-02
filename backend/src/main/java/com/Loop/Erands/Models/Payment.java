package com.Loop.Erands.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;
    @ManyToOne
    @JoinColumn(name = "payments")
    private User user ;
}
