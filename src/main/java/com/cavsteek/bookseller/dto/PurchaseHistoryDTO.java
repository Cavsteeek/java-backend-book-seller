package com.cavsteek.bookseller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class PurchaseHistoryDTO {
    private Long id;
    private PurchaseResponse purchase;
}
