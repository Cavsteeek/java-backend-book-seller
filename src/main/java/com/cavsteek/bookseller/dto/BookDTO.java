package com.cavsteek.bookseller.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private String author;
    private Double price;
}
