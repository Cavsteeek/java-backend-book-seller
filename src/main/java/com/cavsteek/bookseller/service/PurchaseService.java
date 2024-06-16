package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.model.Purchase;

import java.util.List;

public interface PurchaseService {
  /*  Purchase savePurchaseHistory(PurchaseResponse purchaseRequest);*/

    PurchaseResponse savePurchase(Long userId, Long bookId, PurchaseRequest purchaseRequest);

    List<Purchase> getAllPurchases();

    void deleteOrder(Long id);

    boolean existsInCart(String title, String author, Long userId);
}
