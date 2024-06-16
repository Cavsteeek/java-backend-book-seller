package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.dto.PurchaseHistoryDTO;
import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.repository.PurchaseHistoryRepository;
import com.cavsteek.bookseller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/purchase-history")
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
@RequiredArgsConstructor
public class PurchaseHistoryController {
    private final PurchaseHistoryService purchaseHistoryService;
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    @PostMapping("/create/{userId}/{bookId}/{purchaseId}")
    public ResponseEntity<?> createPurchaseHistory(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId, @PathVariable("purchaseId") Long purchaseId) {
        try {
            PurchaseHistoryDTO purchaseHistory = purchaseHistoryService.savePurchaseHistory(userId, bookId, purchaseId);
            return new ResponseEntity<>(purchaseHistory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPurchaseHistory() {
        try {
            return new ResponseEntity<>(purchaseHistoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
