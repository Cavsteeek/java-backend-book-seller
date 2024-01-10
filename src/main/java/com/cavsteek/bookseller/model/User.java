package com.cavsteek.bookseller.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password",nullable = false, length = 100)
    private String password;

    @Column(name = "firstName",nullable = false, length = 100)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "createTime", nullable = false)
    private LocalDateTime createTime;


}
