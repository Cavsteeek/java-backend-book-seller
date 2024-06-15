package com.cavsteek.bookseller.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class PurchaseHistoryDTO {
    private Long id;
    private PurchaseResponse purchase;
}
