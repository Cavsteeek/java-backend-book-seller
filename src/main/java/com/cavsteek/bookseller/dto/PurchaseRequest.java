package com.cavsteek.bookseller.dto;

import lombok.Data;

@Data
public class PurchaseRequest {
    private Long userId;
    private Long bookId;
    private Double price;
}
