package com.cavsteek.bookseller.CustomResponse;

import com.cavsteek.bookseller.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedBookResponse {
    private String message;
    private Book book;
}
