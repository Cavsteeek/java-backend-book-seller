package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>
{
    boolean existsByTitleAndDescriptionAndAuthorAndPrice(String title, String description, String author, Double price);
}
