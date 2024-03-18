package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        try {
            if(bookService.bookExists(book.getTitle(),book.getDescription(),book.getAuthor(),book.getPrice())){
                return ResponseEntity.badRequest().body("Book with these Details already exists");
            }
            return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId){
        try {
            bookService.deleteBook(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view-all")
    public ResponseEntity<?> getAllBooks(){
        try {
            return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
