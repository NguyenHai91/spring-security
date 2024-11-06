package com.hainguyen.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hainguyen.security.model.Token;

@Repository
public interface RedisTokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
