package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.dto.GetUserResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /*
    @Override
    public User saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setCreateTime(LocalDateTime.now());

        return userRepository.save(user);
    }
     */

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByUsername(email);
    }

    @Override
    public List<GetUserResponse> findUsersByRole(){
         List<User> userList = userRepository.findAllUsersByRole(Role.USER);
        return userList.stream()
                .map(user -> new GetUserResponse())
                .collect(Collectors.toList());
    }

    @Override
    public boolean usernameExists(String username){
        return userRepository.existsByUsername(username);
    }

}
