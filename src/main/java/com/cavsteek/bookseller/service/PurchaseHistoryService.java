package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.dto.PurchaseHistoryDTO;
import com.cavsteek.bookseller.model.PurchaseHistory;

public interface PurchaseHistoryService {
    PurchaseHistoryDTO savePurchaseHistory(Long userId, Long bookId, Long purchaseId);
}
