package com.cavsteek.bookseller.dto;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.User;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PurchaseResponse {
    private Long id;
    private Integer quantity;
    private Double price;
    private BookDTO book;
    private UserDTO user;

}
