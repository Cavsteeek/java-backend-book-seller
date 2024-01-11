package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>
{

}
