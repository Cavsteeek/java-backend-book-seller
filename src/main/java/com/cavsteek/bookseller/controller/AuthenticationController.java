package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.auth.AuthenticationService;
import com.cavsteek.bookseller.dto.JwtAuthenticationResponse;
import com.cavsteek.bookseller.dto.RefreshTokenRequest;
import com.cavsteek.bookseller.dto.SignInRequest;
import com.cavsteek.bookseller.dto.SignUpRequest;
import com.cavsteek.bookseller.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            if (userService.usernameExists(signUpRequest.getUsername())) {
                return ResponseEntity.badRequest().body("User with this Username already exists");
            }
            return ResponseEntity.ok(service.signUp(signUpRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        JwtAuthenticationResponse user = service.signIn(signInRequest);
        return ResponseEntity.ok(user);
    }

  /*  @GetMapping("/oauth2-login")
    public String oauth2Login(HttpServletRequest request) {
        return "redirect:/oauth2/authorization/github";
    }

    @GetMapping("/oauth2-success")
    public ResponseEntity<String> oauth2LoginSuccess(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            return ResponseEntity.ok("OAuth2 Login Successful: " + authentication.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth2 Login Failed");
        }
    }*/

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(service.refreshToken(refreshTokenRequest));
    }

    // Implement Logout
}
