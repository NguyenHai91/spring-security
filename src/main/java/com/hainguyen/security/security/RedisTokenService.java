package com.hainguyen.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.exception.CustomException;
import com.hainguyen.security.model.Token;
import com.hainguyen.security.repository.RedisTokenRepository;

@Service
public class RedisTokenService {
    @Autowired
    private RedisTokenRepository redisTokenRepository;

    public void createToken(Token token) {
        redisTokenRepository.save(token);
    }

    public void deleteToken(Long id) {
        redisTokenRepository.deleteById(id);
    }

    public Token getById(Long id) {
        return redisTokenRepository.findById(id).orElseThrow(() -> new CustomException("Token not found"));
    }

    public Token getByToken(String token) {
        return redisTokenRepository.findByToken(token).orElseThrow(() -> new CustomException("Token not found"));
    }
}
