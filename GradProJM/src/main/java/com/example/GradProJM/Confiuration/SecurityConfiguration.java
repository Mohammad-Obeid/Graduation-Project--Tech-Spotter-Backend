package com.example.GradProJM.Confiuration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/admin/**","/customer/**",
                                "/address/**","/customer/**",
                                "/image/**","order/**",
                                "/orderitem/**","/paymentMethod/**",
                                "/product/**","/productImage/**",
                                "/report/**","/productShop/**",
                                "/shop/**","/shoppingCart/**",
                                "/user/**","/wishlist/**").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority(CUSTOMER.name(),ADMIN.name())
//                        .requestMatchers("/admin/**").hasAnyAuthority(ADMIN.name())
//                        .requestMatchers("/employee/**").hasAnyAuthority(ADMIN.name())
//                        .requestMatchers("/customer/**").hasAnyAuthority(CUSTOMER.name(), ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}