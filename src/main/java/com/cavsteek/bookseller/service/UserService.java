package com.cavsteek.bookseller.service;


import com.cavsteek.bookseller.dto.ResetPasswordRequest;
import com.cavsteek.bookseller.dto.changePasswordRequest;
import com.cavsteek.bookseller.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

public interface UserService {

    //User saveUser(User user);

    //Optional<User> findByUsername(String username);

    UserDetailsService userDetailsService();

    /*Optional<User> findByEmail(String email);*/


    List<User> findUsersByRole();

    boolean usernameExists(String username);

    void deleteUser(Long id);

    User patchUser(Long id, User userPatch);


    /*User resetPassword(Long id, ResetPasswordRequest request);*/

    void changePassword(changePasswordRequest request, Principal connectedUser);

    String passwordResetOTP(String userEmail);

    void resetPassword(String userEmail,String otp, ResetPasswordRequest request);

    boolean verifyOTP(String email, String otp);
}
