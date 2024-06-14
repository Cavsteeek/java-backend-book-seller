package com.cavsteek.bookseller.config;


import com.cavsteek.bookseller.jwt.JWTService;
import com.cavsteek.bookseller.jwt.impl.JWTServiceImpl;
import com.cavsteek.bookseller.model.Role;
import com.cavsteek.bookseller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .cors(c -> c.configurationSource(request -> {
//                    CorsConfiguration config = new CorsConfiguration();
//                    config.setAllowedOrigins(Arrays.asList("https://cavsteek-s.vercel.app", "http://localhost:8081"));
//                    config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
//                    config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
//                    return config;
//                }))
                .authorizeHttpRequests(request -> request
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/book/**").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyAuthority(Role.USER.name())
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/user/**").hasAnyAuthority(Role.USER.name())
                                .requestMatchers("/api/v1/user/wishlist/**").hasAnyAuthority(Role.USER.name())
                                .requestMatchers("/api/v1/book/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers("/api/v1/admin/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/book/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers("/api/v1/purchases/create/**").hasAnyAuthority(Role.USER.name())
//                        .requestMatchers(HttpMethod.GET,"/api/v1/purchase-history/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                /*.oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/v1/auth/login")
                        .defaultSuccessUrl("/api/v1/auth/loginSuccess")
                        .userInfoEndpoint(userInfo ->  userInfo
                                .oidcUserService(this.oidcUserService()))
                )*/
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.userDetailsService().loadUserByUsername(username);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}