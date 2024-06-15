package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.UnauthorizedUserException;
import com.cavsteek.bookseller.model.Purchase;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.repository.PurchaseHistoryRepository;
import com.cavsteek.bookseller.repository.PurchaseRepository;
import com.cavsteek.bookseller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    private final PurchaseHistoryRepository pHistoryRepo;
    private final PurchaseRepository purchaseRepository;

    @Override
    public PurchaseHistory savePurchaseHistory(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        if(purchase.getUser().getRole().equals(Role.ADMIN)){
            throw new UnauthorizedUserException("Unauthorized user");
        }

        PurchaseHistory purchaseHistory = PurchaseHistory
                .builder()
                .purchase(purchase)
                .build();

        return pHistoryRepo.save(purchaseHistory);
    }
}
