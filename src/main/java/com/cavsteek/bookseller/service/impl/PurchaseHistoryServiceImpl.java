package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.BookRepository;
import com.cavsteek.bookseller.repository.PurchaseHistoryRepository;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public PurchaseHistory savePurchaseHistory(Long userId, Long bookId)
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setUser(user);
        purchaseHistory.setBook(book);
        purchaseHistory.setPrice(book.getPrice());
        purchaseHistory.setPurchaseTime(LocalDateTime.now());
        return purchaseHistoryRepository.save(purchaseHistory);
    }




}
