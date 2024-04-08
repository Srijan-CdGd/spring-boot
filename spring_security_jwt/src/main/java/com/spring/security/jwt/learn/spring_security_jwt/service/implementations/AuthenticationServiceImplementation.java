package com.spring.security.jwt.learn.spring_security_jwt.service.implementations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.security.jwt.learn.spring_security_jwt.dto.request.LoginRequest;
import com.spring.security.jwt.learn.spring_security_jwt.dto.request.RegisterRequest;
import com.spring.security.jwt.learn.spring_security_jwt.dto.response.BasicResponse;
import com.spring.security.jwt.learn.spring_security_jwt.dto.response.LoginResponse;
import com.spring.security.jwt.learn.spring_security_jwt.enumerated.Role;
import com.spring.security.jwt.learn.spring_security_jwt.model.Token;
import com.spring.security.jwt.learn.spring_security_jwt.model.User;
import com.spring.security.jwt.learn.spring_security_jwt.repository.TokenRepository;
import com.spring.security.jwt.learn.spring_security_jwt.repository.UserRepository;
import com.spring.security.jwt.learn.spring_security_jwt.service.AuthenticationService;
import com.spring.security.jwt.learn.spring_security_jwt.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService{

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public BasicResponse<String> register(RegisterRequest registerRequest) {
        Optional<User> userExists = userRepository.findByEmail(registerRequest.getEmail());
        if(userExists.isPresent()){
            return BasicResponse.<String>builder()
            .message("User alreaddy exists with email " + registerRequest.getEmail())
            .data("")
            .build();
        }

        var user = User.builder()
                    .email(registerRequest.getEmail())
                    .name(registerRequest.getName())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.CUSTOMER)
                    .build();

        userRepository.save(user);
        return BasicResponse.<String>builder()
                            .message("User Registered successfully")
                            .data("")
                            .build();
    }

    @Override
    public BasicResponse<LoginResponse> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        Optional<User> userExists = userRepository.findByEmail(loginRequest.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userExists.get().getRole().toString());
        String accessToken = jwtUtil.generateToken(userExists.get());
        revokeAllUserTokens(userExists.get());
        saveUserToken(accessToken, userExists.get());
        return BasicResponse.<LoginResponse>builder()
                            .message("user Logged in successfully")
                            .data(LoginResponse.builder()
                                    .accessToken(accessToken)
                                    .build())
                            .build();
    }

    private void saveUserToken(String accessToken, User user) {
        var token = Token.builder()
                .token(accessToken)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();
        
                tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllByUser_IdAndRevokedFalseAndExpiredFalse(user.getId());
        if(validUserTokens.isEmpty()){ return; }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
