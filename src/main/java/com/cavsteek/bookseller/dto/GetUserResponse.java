package com.cavsteek.bookseller.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class GetUserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
