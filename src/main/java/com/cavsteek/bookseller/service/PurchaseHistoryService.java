package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.repository.UserRepository;
import java.util.List;

public interface PurchaseHistoryService {
    PurchaseHistory savePurchaseHistory(PurchaseRequest purchaseRequest);

    PurchaseHistory savePurchaseHistoryy(Long userId, Long bookId);


//    List<PurchaseHistory> findPurchasedItemsOfUser();
}
