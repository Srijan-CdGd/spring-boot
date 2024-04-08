package com.spring.security.jwt.learn.spring_security_jwt.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.learn.spring_security_jwt.dto.request.LoginRequest;
import com.spring.security.jwt.learn.spring_security_jwt.dto.request.RegisterRequest;
import com.spring.security.jwt.learn.spring_security_jwt.dto.response.BasicResponse;
import com.spring.security.jwt.learn.spring_security_jwt.dto.response.LoginResponse;
import com.spring.security.jwt.learn.spring_security_jwt.repository.TokenRepository;
import com.spring.security.jwt.learn.spring_security_jwt.repository.UserRepository;
import com.spring.security.jwt.learn.spring_security_jwt.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        BasicResponse<String> response = new BasicResponse<>();
        try {
            response = authenticationService.register(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setMessage("Something went wrong");
            response.setData("");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginRequest loginRequest) {
        BasicResponse<LoginResponse> response = new BasicResponse<>();
        try {
            response = authenticationService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Something went wrong");
            response.setData(LoginResponse.builder().build());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getAll/users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getAll/tokens")
    public ResponseEntity<?> getAllTokens() {
        return new ResponseEntity<>(tokenRepository.findAll().stream().collect(Collectors.toList()), HttpStatus.OK);
    }
}