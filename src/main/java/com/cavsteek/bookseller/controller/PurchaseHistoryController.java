package com.cavsteek.bookseller.controller;

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

    @PostMapping("/create")
    public ResponseEntity<?> createPurchase(@RequestBody) {
        try {
            PurchaseHistory purchaseHistory = purchaseHistoryService.savePurchaseHistoryy(user, book);
            return new ResponseEntity<>(purchaseHistory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//    @GetMapping("/view")
//    public ResponseEntity<?> getAllPurchasesOfUser(@AuthenticationPrincipal User user){
//        return ResponseEntity.ok(purchaseHistoryService.findPurchasedItemsOfUser());
//    }
}
