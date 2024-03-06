package com.cavsteek.bookseller.service;

import com.cavsteek.bookseller.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {

    //User saveUser(User user);

    //Optional<User> findByUsername(String username);

    UserDetailsService userDetailsService();

    Optional<User> findByEmail(String email);

    boolean usernameExists(String username);

}
