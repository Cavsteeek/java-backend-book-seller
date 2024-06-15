package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.model.PurchaseHistory;

public interface PurchaseHistoryService {
    PurchaseHistory savePurchaseHistory(Long purchaseId);
}
