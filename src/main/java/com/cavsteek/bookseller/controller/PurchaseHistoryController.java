package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseHistoryController {
    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping("/create/{userId}/{bookId}")
    public ResponseEntity<?> createPurchase(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody PurchaseRequest purchaseRequest) {
        try {
            PurchaseHistory purchaseHistory = purchaseHistoryService.savePurchaseHistory(userId, bookId, purchaseRequest);
            return new ResponseEntity<>(purchaseHistory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}