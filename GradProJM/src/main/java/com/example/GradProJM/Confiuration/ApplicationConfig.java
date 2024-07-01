package com.example.GradProJM.Confiuration;

import com.example.GradProJM.Repos.userRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    // Repository to manage user data.
    private final userRepository repository;

    // UserDetailsService is an interface used to retrieve user-related data.
    @Bean
    public UserDetailsService userDetailsService() {
        //load user-specific data by username.
        return username -> repository.findByuserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // An AuthenticationProvider is responsible for processing a specific type of Authentication request.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Set UserDetailsService for the authentication provider.
        authProvider.setUserDetailsService(userDetailsService());
        // Set password encoder, to hash the passwords.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // An AuthenticationManager authenticates each authentication request.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Returns the AuthenticationManager from the passed AuthenticationConfiguration.
        return config.getAuthenticationManager();
    }

    // PasswordEncoder is an interface that specifies a cryptographic hashing strategy for passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
