package com.cavsteek.bookseller.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private String author;
    private Double price;
}
