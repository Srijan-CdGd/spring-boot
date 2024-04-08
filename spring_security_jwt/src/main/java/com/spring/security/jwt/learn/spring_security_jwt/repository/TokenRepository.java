package com.spring.security.jwt.learn.spring_security_jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.jwt.learn.spring_security_jwt.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUser_IdAndRevokedFalseAndExpiredFalse(long id);

    Optional<Token> findByToken(String token);

}
