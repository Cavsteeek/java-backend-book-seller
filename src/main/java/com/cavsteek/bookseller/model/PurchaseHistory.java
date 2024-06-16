package com.cavsteek.bookseller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_history")
public class PurchaseHistory {
    //map purchase to pHistory
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // give this the fields of purchase and remove createTime and add it here
    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;
}
