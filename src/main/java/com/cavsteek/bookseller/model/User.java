package com.cavsteek.bookseller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password",nullable = false, length = 100)
    private String password;

    @Column(name = "firstName",nullable = false, length = 100)
    private String firstName;

    @Column(name = "passwordResetOTP",nullable = true )
    private String OTP;

    @Column(name = "otp_expiry", nullable = true)
    private LocalDateTime otpExpiry;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "lastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "shipping_address", nullable = true, length = 500)
    private String shippingAddress;

    @Column(name = "payment_method", nullable = true , length = 20)
    private String paymentMethod;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Purchase> purchase;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PurchaseHistory> purchaseHistories;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
