package com.cavsteek.bookseller.CustomResponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedUserException extends RuntimeException {
    private String message;
}
