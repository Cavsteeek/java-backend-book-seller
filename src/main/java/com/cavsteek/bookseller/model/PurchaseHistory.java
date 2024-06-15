package com.cavsteek.bookseller.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "purchase_history")
public class PurchaseHistory {
    //map purchase to pHistory
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;
}
