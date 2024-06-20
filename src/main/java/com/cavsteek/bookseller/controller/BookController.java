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

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
@RequiredArgsConstructor
public class BookController {
    // Injecting the BookService to handle business logic
    private final BookService bookService;

    // Endpoint to save a new book with multipart form data (including a file upload)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveBook(@RequestParam("file") MultipartFile file,
                                      @RequestParam("title") String title,
                                      @RequestParam("description") String description,
                                      @RequestParam("genre") String genre,
                                      @RequestParam("author") String author,
                                      @RequestParam("price") Double price) {
        try {
            // Create a new book and set its properties
            Book book = new Book();
            book.setTitle(title);
            book.setDescription(description);
            book.setGenre(genre);
            book.setAuthor(author);
            book.setPrice(price);
            book.setFile(file);

            // Check if a book with similar details already exists
            if (bookService.bookExists(book.getTitle(), book.getDescription(), book.getAuthor(), book.getPrice())) {
                return ResponseEntity.badRequest().body("Book with these Details already exists");
            }

            // Save the new book and return a created status
            bookService.saveBook(book);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // Return an internal server error status if an exception occurs
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete a book by its ID
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId) {
        try {
            // Delete the book and return a success message
            bookService.deleteBook(bookId);
            return ResponseEntity.ok("Book deleted Successfully");
        } catch (Exception e) {
            // Return an internal server error status if an exception occurs
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get details of a specific book by its ID
    @GetMapping("book-details/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable("bookId") Long bookId) {
        try {
            // Retrieve book details and return them with an OK status
            Book bookDetails = bookService.getBookDetails(bookId);
            return new ResponseEntity<>(bookDetails, ResponseEntity.ok("Successful").getStatusCode());
        } catch (Exception e) {
            // Return an internal server error status if an exception occurs
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get a list of all books
    @GetMapping("/view-all")
    public ResponseEntity<?> getAllBooks() {
        try {
            // Retrieve all books and return them with an OK status
            return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
        } catch (Exception e) {
            // Return an internal server error status if an exception occurs
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update a book's details using PATCH (partial update)
    @PatchMapping("/update-book/{bookId}")
    public ResponseEntity<?> updateBookById(@RequestParam(value = "file", required = false) MultipartFile file,
                                            @RequestParam(value = "title", required = false) String title,
                                            @RequestParam(value = "description", required = false) String description,
                                            @RequestParam(value = "genre", required = false) String genre,
                                            @RequestParam(value = "author", required = false) String author,
                                            @RequestParam(value = "price", required = false) Double price,
                                            @PathVariable("bookId") Long id) {
        try {
            // Create a book object to hold updates
            Book bookPatch = new Book();
            if (title != null) {
                bookPatch.setTitle(title);
            }
            if (description != null) {
                bookPatch.setDescription(description);
            }
            if (genre != null) {
                bookPatch.setGenre(genre);
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

            // Update the book and return the updated details
            Book updatedBook = bookService.patchBook(id, bookPatch);
            return ResponseEntity.ok(new UpdatedBookResponse("Book Updated Successfully", updatedBook));
        } catch (Exception e) {
            // Return an internal server error status if an exception occurs
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
