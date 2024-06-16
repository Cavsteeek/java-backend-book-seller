package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.UnauthorizedUserException;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.model.Wishlist;
import com.cavsteek.bookseller.repository.BookRepository;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.repository.WishlistRepository;
import com.cavsteek.bookseller.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public Wishlist createWishlist(Long userId, Long bookId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (user.getRole().equals(Role.ADMIN)) {
            throw new UnauthorizedUserException("Unauthorized user");
        }

        Wishlist wishlist = Wishlist
                .builder()
                .book(book)
                .user(user)
                .build();

        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> showWishlist(){
        return wishlistRepository.findAll();
    }

    @Override
    public void deletefromWishlist(Long id){
        wishlistRepository.deleteById(id);
    }
    @Override
    public boolean existsInCart(String title, String author, Long userId){
        return wishlistRepository.existsByBook_TitleAndBook_AuthorAndUser_Id(title, author, userId);
    }
    // implement method to make sure a wishlist with same userId and bookId are not created twice
}

