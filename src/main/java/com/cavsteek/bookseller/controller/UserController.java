package com.cavsteek.bookseller.controller;


import com.cavsteek.bookseller.CustomResponse.UpdatedBookResponse;
import com.cavsteek.bookseller.CustomResponse.UpdatedUserResponse;
import com.cavsteek.bookseller.dto.JwtAuthenticationResponse;
import com.cavsteek.bookseller.dto.ResetPasswordRequest;
import com.cavsteek.bookseller.dto.changePasswordRequest;
import com.cavsteek.bookseller.model.Book;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.UserService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.findUsersByRole();
        return ResponseEntity.ok(userList);
    }
    @GetMapping
    public ResponseEntity<String> sayHello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok("Welcome " + username);
    }

    @PatchMapping("/edit-profile/{userId}")
    // implement if userId != token.userId then throw error
    public ResponseEntity<?> editUserProfileById(@RequestParam(value = "username", required = false) String username,
                                                 @RequestParam(value = "email", required = false) String email,
                                                 @RequestParam(value = "firstName", required = false) String firstName,
                                                 @RequestParam(value = "lastName", required = false) String lastName,
                                                 @RequestParam(value = "shippingAddress", required = false) String shippingAddress,
                                                 @RequestParam(value = "paymentMethod", required = false) String paymentMethod,
                                                 @PathVariable("userId") Long userId) {
        Long loggedInUser = getAuthenticatedUserId();
        if (userId.equals(loggedInUser)) {
            try {
                User userPatch = new User();
                if (username != null) {
                    userPatch.setUsername(username);
                }
                if (email != null) {
                    userPatch.setEmail(email);
                }
                if (firstName != null) {
                    userPatch.setFirstName(firstName);
                }
                if (lastName != null) {
                    userPatch.setLastName(lastName);
                }
                if(shippingAddress != null){
                    userPatch.setShippingAddress(shippingAddress);
                }
                if(paymentMethod != null){
                    userPatch.setPaymentMethod(paymentMethod);
                }

                User updatedUser = userService.patchUser(userId, userPatch);
                return ResponseEntity.ok(new UpdatedUserResponse("User Updated Successfully", updatedUser));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("You are not authorised to reset this password", HttpStatus.FORBIDDEN);
        }
    }

    /*    @PatchMapping("/password-reset/{userId}")
        // implement if userId != token.userId then throw error
        public ResponseEntity<?> passwordReset(@PathVariable("userId") Long userId, @RequestBody ResetPasswordRequest request) {
            Long loggedInUser = getAuthenticatedUserId();
            if (userId.equals(loggedInUser)) {
                try {
                    User newPassword = userService.resetPassword(userId, request);
                    return ResponseEntity.ok(new UpdatedUserResponse("Password Updated Successfully", newPassword));
                } catch (Exception e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("You are not authorised to reset this password", HttpStatus.FORBIDDEN);
            }
        }*/
    @PatchMapping("/password-reset")
    // implement if userId != token.userId then throw error
    public ResponseEntity<?> passwordChange(@RequestBody changePasswordRequest request, Principal connectedUser) {
        try {
            userService.changePassword(request, connectedUser);
            return new ResponseEntity<>("Password Updated Successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/request-reset")
    public ResponseEntity<?> requestPasswordResetOTP(@RequestParam String email) {
        String response = userService.passwordResetOTP(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTPP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = userService.verifyOTP(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP is valid. You can reset your password.");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(email, otp,request);
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getId();
    }


}
