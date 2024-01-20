package com.cavsteek.bookseller.controller;

import com.cavsteek.bookseller.auth.AuthenticationService;
import com.cavsteek.bookseller.dto.JwtAuthenticationResponse;
import com.cavsteek.bookseller.dto.SignInRequest;
import com.cavsteek.bookseller.dto.SignUpRequest;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        if(userService.usernameExists(signUpRequest.getUsername())){
            return ResponseEntity.badRequest().body("User with this Username already exists");
        }
        return ResponseEntity.ok(service.signUp(signUpRequest));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        JwtAuthenticationResponse user = service.signIn(signInRequest);
        return ResponseEntity.ok(user);
    }
}
