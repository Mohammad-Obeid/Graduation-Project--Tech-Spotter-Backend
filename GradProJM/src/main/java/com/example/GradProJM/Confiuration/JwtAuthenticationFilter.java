package com.example.GradProJM.Confiuration;

import com.example.GradProJM.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // The JwtService is responsible for the handling of JWT.
    private final JwtService jwtService;
    // The UserDetailsService is used to retrieve user-related data.
    private final UserDetailsService userDetailsService;
    // The TokenRepository is used to retrieve token-related data.
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the Authorization header from the request.
        final String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);

        final String jwt;
        final String userEmail;
        // If the Authorization header is null or does not start with "Bearer ", continue with the next filter in the chain.
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        System.out.println(jwt);

        System.out.println(jwt);
        userEmail = jwtService.extractUsername(jwt);

        System.out.println(userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user-specific data.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println(userDetails);
            System.out.println(userDetails.getAuthorities());
            // Check if the token is valid.
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            // If the JWT is valid, create an Authentication and set it in the SecurityContext.
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue with the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}
