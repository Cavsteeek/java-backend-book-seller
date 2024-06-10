package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.dto.ResetPasswordRequest;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public List<User> findUsersByRole() {
        return userRepository.findAllUsersByRole(Role.USER);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User patchUser(Long id, User userPatch) {
        return userRepository.findById(id)
                .map(existingUser -> {
                        if (userPatch.getUsername() != null) {
                            existingUser.setUsername(userPatch.getUsername());
                        }
                        if (userPatch.getEmail() != null) {
                            existingUser.setEmail(userPatch.getEmail());
                        }
                        if (userPatch.getFirstName() != null) {
                            existingUser.setFirstName(userPatch.getFirstName());
                        }
                        if (userPatch.getLastName() != null) {
                            existingUser.setLastName(userPatch.getLastName());
                        }
                        return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: "+id));




    }

    @Override
    public User resetPassword(Long id, ResetPasswordRequest request) {
        // Retrieve user by ID
        return userRepository.findById(id)
                .map(user -> {
                    // Set the new password
                    user.setPassword(passwordEncoder().encode(request.getPassword()));

                    // Save the updated user entity
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: "+id));
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
