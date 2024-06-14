package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.model.Wishlist;
import com.cavsteek.bookseller.repository.WishlistRepository;
import com.cavsteek.bookseller.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/wishlist")
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;

    @PostMapping("/create/{userId}/{bookId}")
    public ResponseEntity<?> createWishlist(@PathVariable("userId") Long userId, @PathVariable("bookId") Long bookId) {
        try {
            Wishlist wishlist = wishlistService.createWishlist(userId, bookId);
            return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-wishlist")
    public ResponseEntity<?> getWishlist() {
        try {
            return new ResponseEntity<>(wishlistService.showWishlist(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-wish/{id}")
    public ResponseEntity<?> deleteWishlist(@PathVariable("id") Long id){
            try{
                //either of the method works

//                wishlistService.deletefromWishlist(id);
                wishlistRepository.deleteById(id);
                return ResponseEntity.ok("Deleted Successfully");
            }catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

}
