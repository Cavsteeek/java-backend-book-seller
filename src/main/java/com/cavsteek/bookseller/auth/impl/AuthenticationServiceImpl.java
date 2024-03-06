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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User signUp(SignUpRequest signUpRequest){
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());
        user.setPassword(
                passwordEncoder.encode(signUpRequest.getPassword())
        );
      return userRepository.save(user);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
        String usernameOrEmail = signInRequest.getUsername();
        boolean isEmail = isValidEmail(usernameOrEmail);

        User user_;
        if (isEmail) {
            user_ = userRepository.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Email or Password"));
        } else {
            user_ = userRepository.findByUsername(usernameOrEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Username or Password"));
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()));
        var user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid Username or Password")
                );
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userName = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(userName).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
