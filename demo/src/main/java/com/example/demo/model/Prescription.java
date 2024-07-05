package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientName;

    @ManyToOne
    private Contact medicine;

    private String doctorName;
    private String expirationDate;

    // Getters and Setters
}
