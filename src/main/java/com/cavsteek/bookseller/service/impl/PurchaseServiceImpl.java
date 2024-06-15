package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.UnauthorizedUserException;
import com.cavsteek.bookseller.dto.BookDTO;
import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.dto.UserDTO;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.Purchase;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.BookRepository;
import com.cavsteek.bookseller.repository.PurchaseRepository;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public PurchaseResponse savePurchaseHistory(Long userId, Long bookId, PurchaseRequest purchaseRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (user.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedUserException("Unauthorized user");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setBook(book);
        purchase.setQuantity(purchaseRequest.getQuantity());
        purchase.setPrice(book.getPrice() * purchase.getQuantity());
        purchase.setPurchaseTime(LocalDateTime.now());

        Purchase savedPurchase = purchaseRepository.save(purchase);

        BookDTO bookDTO = BookDTO
                .builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .genre(book.getGenre())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();

        UserDTO userDTO = UserDTO
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        return new PurchaseResponse(savedPurchase.getId(), savedPurchase.getQuantity(), savedPurchase.getPrice(), bookDTO, userDTO);
    }

    @Override
    public List<Purchase> getAllPurchases() {

        return purchaseRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id) {
        purchaseRepository.deleteById(id);
    }

    // Add patch the quantity of the book

}
