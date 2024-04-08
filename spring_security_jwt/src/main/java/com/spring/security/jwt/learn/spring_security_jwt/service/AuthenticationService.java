package com.spring.security.jwt.learn.spring_security_jwt.service;

import com.spring.security.jwt.learn.spring_security_jwt.dto.request.LoginRequest;
import com.spring.security.jwt.learn.spring_security_jwt.dto.request.RegisterRequest;
import com.spring.security.jwt.learn.spring_security_jwt.dto.response.BasicResponse;
import com.spring.security.jwt.learn.spring_security_jwt.dto.response.LoginResponse;

public interface AuthenticationService {

    BasicResponse<String> register(RegisterRequest registerRequest);

    BasicResponse<LoginResponse> login(LoginRequest loginRequest);

}
