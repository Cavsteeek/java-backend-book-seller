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

import java.util.List;

@RestController
@RequestMapping("api/v1/purchases")
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
@RequiredArgsConstructor
public class PurchaseHistoryController {
    private final PurchaseHistoryService purchaseHistoryService;

    @PostMapping("/create/{userId}/{bookId}") // For Users
    public ResponseEntity<?> createPurchase(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody PurchaseRequest purchaseRequest) {
        try {
            PurchaseHistory purchaseHistory = purchaseHistoryService.savePurchaseHistory(userId, bookId, purchaseRequest);
            return new ResponseEntity<>(purchaseHistory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-purchases")
    public ResponseEntity<?> getAllPurchases(){
        try{
            return new ResponseEntity<>(purchaseHistoryService.getAllPurchases(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long orderId){
        try{
            purchaseHistoryService.deleteOrder(orderId);
            return ResponseEntity.ok("Deleted Successfully");
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}