package com.cavsteek.bookseller.dto;

import lombok.Data;

@Data
public class PurchaseResponse {
    private Long bookTitle;
    private Double price;
    private Integer quantity;
}
