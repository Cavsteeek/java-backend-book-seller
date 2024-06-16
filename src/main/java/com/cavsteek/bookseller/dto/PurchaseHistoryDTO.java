package com.cavsteek.bookseller.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PurchaseHistoryDTO {
    private Long id;
    private Integer quantity;
    private Double price;
    private BookDTO book;
    private UserDTO user;
    private LocalDateTime purchaseTime;
}
