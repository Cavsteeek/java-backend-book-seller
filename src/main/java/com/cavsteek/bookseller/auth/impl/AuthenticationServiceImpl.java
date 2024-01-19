package com.cavsteek.bookseller.auth.impl;

import com.cavsteek.bookseller.auth.AuthenticationService;
import com.cavsteek.bookseller.jwt.JWTService;
import com.cavsteek.bookseller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;


}
