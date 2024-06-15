package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.dto.PurchaseRequest;
import com.cavsteek.bookseller.model.Purchase;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/purchases")
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;
    private final UserRepository userRepository;

    @PostMapping("/create/{userId}/{bookId}") // For Users
    public ResponseEntity<?> createPurchase(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody PurchaseRequest purchaseRequest) {
        Long loggedInUser = getAuthenticatedUserId();
        if (userId.equals(loggedInUser)) {
            try {
                Purchase purchase = purchaseService.savePurchaseHistory(userId, bookId, purchaseRequest);
                return new ResponseEntity<>(purchase, HttpStatus.CREATED);

            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("You are not authorised to perform this action", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/all-purchases")
    public ResponseEntity<?> getAllPurchases() {
        try {
            return new ResponseEntity<>(purchaseService.getAllPurchases(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long orderId) {
        try {
            purchaseService.deleteOrder(orderId);
            return ResponseEntity.ok("Deleted Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getId();
    }

}