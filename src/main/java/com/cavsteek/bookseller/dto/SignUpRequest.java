package com.cavsteek.bookseller.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String shippingAddress;
    private String paymentMethod;
    private String username;
    private String password;
}
