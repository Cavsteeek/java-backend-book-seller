package com.cavsteek.bookseller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class JwtAuthenticationResponse {
    private String token;
    private String role;
    private String username;
    private String refreshToken;
}
