package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.model.Purchase;
import com.cavsteek.bookseller.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/purchases")
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("/create/{userId}/{bookId}") // For Users
    public ResponseEntity<?> createPurchase(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody PurchaseRequest purchaseRequest) {
        try {
            Purchase purchase = purchaseService.savePurchaseHistory(userId, bookId, purchaseRequest);
            return new ResponseEntity<>(purchase, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-purchases")
    public ResponseEntity<?> getAllPurchases(){
        try{
            return new ResponseEntity<>(purchaseService.getAllPurchases(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long orderId){
        try{
            purchaseService.deleteOrder(orderId);
            return ResponseEntity.ok("Deleted Successfully");
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}