package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.repository.projection.PurchaseItem;

import java.util.List;

public interface PurchaseHistoryService {
    PurchaseHistory savePurchaseHistory(Long userId,Long bookId);



//    List<PurchaseHistory> findPurchasedItemsOfUser();
}
