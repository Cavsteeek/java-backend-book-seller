package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.UnauthorizedUserException;
import com.cavsteek.bookseller.dto.BookDTO;
import com.cavsteek.bookseller.dto.PurchaseHistoryDTO;
import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.dto.UserDTO;
import com.cavsteek.bookseller.model.*;
import com.cavsteek.bookseller.repository.BookRepository;
import com.cavsteek.bookseller.repository.PurchaseHistoryRepository;
import com.cavsteek.bookseller.repository.PurchaseRepository;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    private final PurchaseHistoryRepository pHistoryRepo;
    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public PurchaseHistoryDTO savePurchaseHistory(Long userId, Long bookId, Long purchaseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new RuntimeException("Purchase not found"));
        ;

        if (purchase.getUser().getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedUserException("Unauthorized user");
        }

        PurchaseHistory purchaseHistory = PurchaseHistory
                .builder()
                .user(user)
                .book(book)
                .quantity(purchase.getQuantity())
                .price(purchase.getPrice())
                .purchaseTime(LocalDateTime.now())
                .build();

        PurchaseHistory savedPurchaseHistory = pHistoryRepo.save(purchaseHistory);

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

        return new PurchaseHistoryDTO(savedPurchaseHistory.getId(), savedPurchaseHistory.getQuantity(), savedPurchaseHistory.getPrice(), bookDTO, userDTO, savedPurchaseHistory.getPurchaseTime());
    }
}
