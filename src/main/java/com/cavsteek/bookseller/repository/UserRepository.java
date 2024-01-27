package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>
{
    //long countByRole(Role role);

    //boolean existsByUsernameAndRole(String username, Role role);
    User findByRole(Role role);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);


}
