package com.spring.security.jwt.learn.spring_security_jwt.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.security.jwt.learn.spring_security_jwt.enumerated.Role;
import com.spring.security.jwt.learn.spring_security_jwt.model.User;
import com.spring.security.jwt.learn.spring_security_jwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCli implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() > 0) {
            return;
        }

        var admin = User.builder()
                .name("Admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("Admin@123"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
    }

}
