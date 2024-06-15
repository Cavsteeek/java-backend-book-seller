package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.UnauthorizedUserException;
import com.cavsteek.bookseller.dto.BookDTO;
import com.cavsteek.bookseller.dto.PurchaseHistoryDTO;
import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.dto.UserDTO;
import com.cavsteek.bookseller.model.Purchase;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.repository.PurchaseHistoryRepository;
import com.cavsteek.bookseller.repository.PurchaseRepository;
import com.cavsteek.bookseller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    private final PurchaseHistoryRepository pHistoryRepo;
    private final PurchaseRepository purchaseRepository;

    @Override
    public PurchaseHistoryDTO savePurchaseHistory(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        if (purchase.getUser().getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedUserException("Unauthorized user");
        }

        PurchaseHistory purchaseHistory = PurchaseHistory
                .builder()
                .purchase(purchase)
                .build();

        PurchaseHistory savedPurchaseHistory = pHistoryRepo.save(purchaseHistory);

        BookDTO bookDTO = BookDTO
                .builder()
                .id(purchase.getBook().getId())
                .title(purchase.getBook().getTitle())
                .description(purchase.getBook().getDescription())
                .genre(purchase.getBook().getGenre())
                .author(purchase.getBook().getAuthor())
                .price(purchase.getBook().getPrice())
                .build();

        UserDTO userDTO = UserDTO
                .builder()
                .id(purchase.getUser().getId())
                .firstName(purchase.getUser().getFirstName())
                .lastName(purchase.getUser().getLastName())
                .email(purchase.getUser().getEmail())
                .username(purchase.getUser().getUsername())
                .build();

        PurchaseResponse purchaseResponse = PurchaseResponse
                .builder()
                .id(purchase.getId())
                .quantity(purchase.getQuantity())
                .price(purchase.getPrice())
                .book(bookDTO)
                .user(userDTO)
                .build();

        return new PurchaseHistoryDTO(savedPurchaseHistory.getId(), purchaseResponse);
    }
}
