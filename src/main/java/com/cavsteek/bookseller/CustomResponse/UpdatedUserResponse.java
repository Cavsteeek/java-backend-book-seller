package com.cavsteek.bookseller.CustomResponse;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedUserResponse {
    private String message;
    private User user;
}
