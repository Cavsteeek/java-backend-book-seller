package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByBook_TitleAndBook_AuthorAndUser_Id(String title, String author, Long userId);
}
