package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.CustomResponse.UpdatedBookResponse;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        try {
            if(bookService.bookExists(book.getTitle(),book.getDescription(),book.getAuthor(),book.getPrice())){
                return ResponseEntity.badRequest().body("Book with these Details already exists");
            }
            bookService.saveBook(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId){
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.ok("Book deleted Successfully");
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

    @PatchMapping("/update-book/{bookId}")
    public ResponseEntity<?> updateBookById(@RequestBody Book book, @PathVariable("bookId") Long id){
        try{
            Book updatedBook = bookService.patchBook(id,book);
            return ResponseEntity.ok(new UpdatedBookResponse("Book Updated Successfully", updatedBook));
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
