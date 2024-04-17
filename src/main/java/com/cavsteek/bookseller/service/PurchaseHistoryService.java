package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.model.PurchaseHistory;

public interface PurchaseHistoryService {
  /*  PurchaseHistory savePurchaseHistory(PurchaseResponse purchaseRequest);*/

    PurchaseHistory savePurchaseHistoryy(Long userId, Long bookId);


//    List<PurchaseHistory> findPurchasedItemsOfUser();
}
