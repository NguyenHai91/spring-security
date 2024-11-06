package com.hainguyen.security.repository;

import com.hainguyen.security.model.Token;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
  List<Token> findAll();
  Optional<Token> findByToken(String token);
  Optional<Token> findByRefreshToken(String refreshToken);
}
