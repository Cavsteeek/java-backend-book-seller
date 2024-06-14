package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.UnauthorizedUserException;
import com.cavsteek.bookseller.dto.PurchaseRequest;
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
    public Purchase savePurchaseHistory(Long userId, Long bookId, PurchaseRequest purchaseRequest) {
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

        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id){
        purchaseRepository.deleteById(id);
    }

}
