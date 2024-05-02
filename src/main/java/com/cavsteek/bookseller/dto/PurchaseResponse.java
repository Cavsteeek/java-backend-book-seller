package com.cavsteek.bookseller.dto;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.User;
import lombok.Data;

@Data
public class PurchaseResponse {
    private Book bookTitle;
    private Double price;
    private Integer quantity;
    private User user;

}
