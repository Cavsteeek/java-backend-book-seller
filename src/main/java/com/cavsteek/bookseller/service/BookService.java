package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.model.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BookService {

    ResponseEntity<Map<String, String>> saveBook(Book book);

    void deleteBook(Long id);

    List<Book> findAllBooks();

    boolean bookExists(String title, String description, String author, Double price);


    Book patchBook(Long id, Book bookPatch);
}
