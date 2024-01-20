package com.cavsteek.bookseller.auth;

import com.cavsteek.bookseller.dto.JwtAuthenticationResponse;
import com.cavsteek.bookseller.dto.RefreshTokenRequest;
import com.cavsteek.bookseller.dto.SignInRequest;
import com.cavsteek.bookseller.dto.SignUpRequest;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;

public interface AuthenticationService {
    User signUp (SignUpRequest signUpRequest);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
