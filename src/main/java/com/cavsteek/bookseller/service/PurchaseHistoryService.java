package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.dto.PurchaseResponse;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.model.User;

public interface PurchaseHistoryService {
  /*  PurchaseHistory savePurchaseHistory(PurchaseResponse purchaseRequest);*/

    PurchaseHistory savePurchaseHistoryy(User user, Book book);


//    List<PurchaseHistory> findPurchasedItemsOfUser();
}
