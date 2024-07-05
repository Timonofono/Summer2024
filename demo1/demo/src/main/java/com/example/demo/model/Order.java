package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contact medicine;

    private int quantity;
    private String status; // PAID, PENDING, etc.

    // Getters and Setters
}
