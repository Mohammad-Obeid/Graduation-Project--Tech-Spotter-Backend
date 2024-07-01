package com.example.GradProJM.Authentication;
import com.example.GradProJM.Confiuration.JwtService;
import com.example.GradProJM.Model.LoginRequest;
import com.example.GradProJM.Model.User;
import com.example.GradProJM.Repos.userRepository;
import com.example.GradProJM.token.Token;
import com.example.GradProJM.token.TokenRepository;
import com.example.GradProJM.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final userRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

//  private final CustomerService customerService;
//
//  public AuthenticationResponse register(RegisterRequest request) {
//    var customerDto=new CustomerDTO();
//    customerDto.setFirstName(request.getFirstname());
//    customerDto.setLastName(request.getLastname());
//    customerDto.setBornAt(request.getBornAt());
//
//    var customer= customerService.createCustomer(customerDto);
//
//    var user = User.builder()
//        .firstname(request.getFirstname())
//        .lastname(request.getLastname())
//        .email(request.getEmail())
//        .password(passwordEncoder.encode(request.getPassword()))
//        .customerId(customer.getId())
//        .role(Role.CUSTOMER)
//        .build();
//    var savedUser = repository.save(user);
//    var jwtToken = jwtService.generateToken(user);
//    var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//    return AuthenticationResponse.builder()
//        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//        .build();
//  }

//    public AuthenticationResponse register(RegisterRequest request) {
//        var user = User.builder()
//                .userName(request.getUserName())
//                .userPassword(passwordEncoder.encode(request.getPassword()))
//                .userEmail(request.getEmail())
//                .role(request.getRole())
//                .userPhoneNumber(request.getUserPhoneNumber())
//                .joinDate(LocalDateTime.now())
//                .build();
//        User savedUser = repository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwtToken);
//        if(user.getRole()== Role.ADMIN){
//            Employee emp = new Employee();
//            emp.setUser(savedUser);
//            emp.setEmployeeRole(request.getEmp().getEmployeeRole());
////            empRepo.save(emp);
//        }
//        else{
//            Customer customer = new Customer();
//            customer.setUser(savedUser);
////            custRepo.save(customer);
//        }
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }

    public AuthenticationResponse  authenticate(LoginRequest request) {
        String email=request.getUserNameOrEmail();
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserNameOrEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByuserEmail(email)
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        DeleteAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void DeleteAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserid());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setUser(user);
            tokenRepository.delete(token);

        });
//        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByuserEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                DeleteAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}