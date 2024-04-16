package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.CustomResponse.UpdatedBookResponse;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBook(@RequestParam("file") MultipartFile file,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      @RequestParam("author") String author,
                                      @RequestParam("price") Double price) {
        try {
            Book book = new Book();
            book.setTitle(title);
            book.setDescription(description);
            book.setAuthor(author);
            book.setPrice(price);
            book.setFile(file);

            if (bookService.bookExists(book.getTitle(), book.getDescription(), book.getAuthor(), book.getPrice())) {
                return ResponseEntity.badRequest().body("Book with these Details already exists");
            }
            bookService.saveBook(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.ok("Book deleted Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view-all")
    public ResponseEntity<?> getAllBooks() {
        try {
            return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update-book/{bookId}")
    public ResponseEntity<?> updateBookById(@RequestParam(value = "file", required = false) MultipartFile file,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "description", required = false) String description,
                                            @RequestParam(value = "author", required = false) String author,
                                            @RequestParam(value = "price", required = false) Double price,
                                            @PathVariable("bookId") Long id) {
        try {
            Book bookPatch = new Book();
            if (title != null) {
                bookPatch.setTitle(title);
            }
            if (description != null) {
                bookPatch.setDescription(description);
            }
            if (author != null) {
                bookPatch.setAuthor(author);
            }
            if (price != null) {
                bookPatch.setPrice(price);
            }
            if (file != null) {
                bookPatch.setFile(file);
            }

            Book updatedBook = bookService.patchBook(id, bookPatch);
            return ResponseEntity.ok(new UpdatedBookResponse("Book Updated Successfully", updatedBook));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
