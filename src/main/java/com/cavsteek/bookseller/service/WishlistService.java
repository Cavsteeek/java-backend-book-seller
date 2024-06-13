package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.model.Wishlist;

import java.util.List;

public interface WishlistService {
    Wishlist createWishlist(Long userId, Long bookId);
    List<Wishlist> showWishlist();

    void deletefromWishlist(Long id);
}
