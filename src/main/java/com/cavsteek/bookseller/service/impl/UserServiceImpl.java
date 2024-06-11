package com.cavsteek.bookseller.service.impl;

import com.cavsteek.bookseller.CustomResponse.WrongPasswordException;
import com.cavsteek.bookseller.dto.ResetPasswordRequest;
import com.cavsteek.bookseller.dto.changePasswordRequest;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import com.cavsteek.bookseller.repository.UserRepository;
import com.cavsteek.bookseller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

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
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));


    }

   /* @Override
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
    }*/

    @Override
    public void changePassword(changePasswordRequest request, Principal connectedUser) {
        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        // Check if the current Password is correct
        if (!passwordEncoder().matches(request.getCurrentPassword(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        // Check if the Passwords match
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        // update the password
        user.setPassword(passwordEncoder().encode(request.getNewPassword()));

        // save the n
        userRepository.save(user);
    }

    @Override
    public String passwordResetOTP(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String otp = generateOTP();
            user.setOTP(otp);  // Save the OTP
            user.setOtpExpiry(LocalDateTime.now().plus(Duration.ofHours(1)));
            userRepository.save(user);
            sendOTPEmail(userEmail, otp);
            return "OTP sent to email.";
        } else {
            return "Email not found.";
        }
    }

    @Override
    public void resetPassword(String userEmail,String otp ,ResetPasswordRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (verifyOTP(userEmail, otp)) {
                user.setPassword(passwordEncoder().encode(request.getNewPassword()));
                user.setOTP(null);  // Clear the OTP after successful reset
                user.setOtpExpiry(null);
                userRepository.save(user);
            }else{
                throw new RuntimeException("Cannot reset Password");
            }
        }else {
            throw new RuntimeException("Cannot reset password");
        }

    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getOtpExpiry() != null && LocalDateTime.now().isAfter(user.getOtpExpiry())) {
                // OTP has expired
                return false;
            }
            return otp.equals(user.getOTP());


        }
        return false;
    }

    public void sendOTPEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP for password reset is: " + otp);
        mailSender.send(message);
    }


}
