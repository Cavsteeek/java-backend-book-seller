package com.cavsteek.bookseller.CustomResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongPasswordException extends RuntimeException{
    public String message;
}
