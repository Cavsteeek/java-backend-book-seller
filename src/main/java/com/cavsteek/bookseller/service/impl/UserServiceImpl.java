package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if (userPatch.getUsername() != null) {
                existingUser.setUsername(userPatch.getUsername());
            }
            if (userPatch.getEmail() != null) {
                existingUser.setEmail(userPatch.getEmail());
            }
            if (userPatch.getPassword() != null) {
                existingUser.setPassword(userPatch.getPassword());
            }
            if (userPatch.getFirstName() != null) {
                existingUser.setFirstName(userPatch.getFirstName());
            }
            if (userPatch.getLastName() != null) {
                existingUser.setLastName(userPatch.getLastName());
            }

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public User resetPassword(Long id, User passwordPatch){

    }


}
