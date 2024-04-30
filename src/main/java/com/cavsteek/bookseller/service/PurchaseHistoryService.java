package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.model.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryService {
  /*  PurchaseHistory savePurchaseHistory(PurchaseResponse purchaseRequest);*/

    PurchaseHistory savePurchaseHistory(Long userId, Long bookId, PurchaseRequest purchaseRequest);

    List<PurchaseHistory> getAllPurchases();

}
