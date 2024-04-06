package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.model.Book;

import java.util.List;

public interface BookService {

    void saveBook(Book book);

    void deleteBook(Long id);

    List<Book> findAllBooks();

    boolean bookExists(String title, String description, String author, Double price);


    Book patchBook(Long id, Book bookPatch);
}
