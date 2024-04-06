package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CloudinaryConfig.CloudinaryService;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.repository.BookRepository;
import com.cavsteek.bookseller.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CloudinaryService cloudinaryService;
//    private Cloudinary cloudinary;


    @Override
    public void saveBook(Book book){
        try {
        String imageUrl = cloudinaryService.uploadFile(book.getFile(), "book_seller");
        book.setImageUrl(imageUrl);
        book.setCreateTime(LocalDateTime.now());
        bookRepository.save(book);
//        return ResponseEntity.ok().body(Map.of("url", book.getImageUrl()));
        } catch (Exception e) {
            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException("Failed to save book: " + e.getMessage());
        }
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
    public Book patchBook(Long id, Book bookPatch) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            if (bookPatch.getTitle() != null) {
                existingBook.setTitle(bookPatch.getTitle());
            }
            if (bookPatch.getDescription() != null) {
                existingBook.setDescription(bookPatch.getDescription());
            }
            if (bookPatch.getAuthor() != null) {
                existingBook.setAuthor(bookPatch.getAuthor());
            }
            if (bookPatch.getPrice() != null) {
                existingBook.setPrice(bookPatch.getPrice());
            }
            return bookRepository.save(existingBook);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }


   /* @Override
    public Book patchBook(Long id, Book bookPatch) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));


        if (bookPatch.getTitle() != null) {
            book.setTitle(bookPatch.getTitle());
        }
        if (bookPatch.getDescription() != null) {
            book.setDescription(bookPatch.getDescription());
        }
        if (bookPatch.getAuthor() != null) {
            book.setAuthor(bookPatch.getAuthor());
        }
        if (bookPatch.getPrice() != null) {
            book.setPrice(bookPatch.getPrice());
        }

        Book patchedBook = bookRepository.save(book);
        return mapToBook(patchedBook);
    }*/


/*    private Book mapToBook (Book book){
        Book book_ = new Book();
        book_.setTitle(book.getTitle());
        book_.setDescription(book.getDescription());
        book_.setAuthor(book.getAuthor());
        book_.setPrice(book.getPrice());

        return book_;
    }*/

}
