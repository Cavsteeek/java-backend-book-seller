package com.cavsteek.bookseller.repository;


import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>
{
    User findByRole(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAllUsersByRole(Role role);

    boolean existsByUsername(String username);


}
