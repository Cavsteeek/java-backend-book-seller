package com.cavsteek.bookseller.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "genre", nullable = true)
    private String genre;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    @Transient
    private MultipartFile file;

    @Column(name = "create_time",  nullable = false)
    private LocalDateTime createTime;
}
