package com.cavsteek.bookseller.dto;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.User;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {
    private Long id;
    private BookDTO book;
    private Double price;
    private Integer quantity;
    private UserDTO user;

}
