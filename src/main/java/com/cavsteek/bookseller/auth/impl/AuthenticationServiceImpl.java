package com.cavsteek.bookseller.auth.impl;

import com.cavsteek.bookseller.auth.AuthenticationService;
import com.cavsteek.bookseller.dto.JwtAuthenticationResponse;
import com.cavsteek.bookseller.dto.RefreshTokenRequest;
import com.cavsteek.bookseller.dto.SignInRequest;
import com.cavsteek.bookseller.dto.SignUpRequest;
import com.cavsteek.bookseller.jwt.JWTService;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;


    public User signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setFirstName(capitalize(signUpRequest.getFirstName()));
        user.setLastName(capitalize(signUpRequest.getLastName()));
        user.setEmail(signUpRequest.getEmail());
        user.setPaymentMethod(signUpRequest.getPaymentMethod());
        user.setShippingAddress(signUpRequest.getShippingAddress());
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(
                passwordEncoder.encode(signUpRequest.getPassword())
        );
        return userRepository.save(user);
    }

    private String capitalize(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUsername(),
                    signInRequest.getPassword()));
            var user = userRepository.findByUsername(signInRequest.getUsername())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Invalid Username or Password")
                    );
            var jwt = jwtService.generateToken(user);
            var username = jwtService.extractUsername(jwt);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setUsername(username);
            jwtAuthenticationResponse.setRole(user.getRole().toString());
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            jwtAuthenticationResponse.setUserId(user.getId());
            return jwtAuthenticationResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred during sign-in", e);
        }
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userName = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(userName).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
