package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.repository.BookRepository;
import com.cavsteek.bookseller.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book){
        book.setCreateTime(LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id)
    {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findAllBooks()
    {
        return bookRepository.findAll();
    }

    @Override
    public boolean bookExists(String title, String description, String author, Double price){
        return bookRepository.existsByTitleAndDescriptionAndAuthorAndPrice(title, description, author, price);
    }

    @Override
    public void updateBook(Book book, Long id)
    {
        bookRepository.save(book);
    }

}
