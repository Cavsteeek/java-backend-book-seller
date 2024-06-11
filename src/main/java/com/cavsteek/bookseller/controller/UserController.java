package com.cavsteek.bookseller.controller;


import com.cavsteek.bookseller.CustomResponse.UpdatedBookResponse;
import com.cavsteek.bookseller.CustomResponse.UpdatedUserResponse;
import com.cavsteek.bookseller.dto.JwtAuthenticationResponse;
import com.cavsteek.bookseller.dto.ResetPasswordRequest;
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

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://cavsteek-s.vercel.app", "http://localhost:8081"})
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok("Welcome " + username);
    }

    @PatchMapping("/edit-profile/{userId}")
    // implement if userId != token.userId then throw error
    public ResponseEntity<?> editUserProfileById(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "email", required = false) String email, @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName, @PathVariable("userId") Long userId) {
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

                User updatedUser = userService.patchUser(userId, userPatch);
                return ResponseEntity.ok(new UpdatedUserResponse("User Updated Successfully", updatedUser));
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("You are not authorised to reset this password", HttpStatus.FORBIDDEN);
        }
    }

    @PatchMapping("/password-reset/{userId}")
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
    }

    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getId();
    }
}
