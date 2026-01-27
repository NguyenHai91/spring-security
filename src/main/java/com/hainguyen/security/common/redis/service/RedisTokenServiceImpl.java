package com.hainguyen.security.common.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.auth.token.Token;

@Service
public class RedisTokenServiceImpl implements RedisTokenService {

    @Autowired
    private BaseRedisService baseRedisService;

    private final String key = "token";

    @Override
    public void setAuthToken(Token token) {
        baseRedisService.hashSet(key, token.getToken(), token);
    }

    @Override
    public void deleteAuthToken(String token) {
        baseRedisService.delete(key, token);    
    }

    @Override
    public Token getAuthToken(String token) {
        Token tokenRecord = (Token) baseRedisService.hashGet(key, token); 
        return tokenRecord;   
    }

    @Override
    public boolean checkAuthToken(String token) {
        Token tokenRecord = (Token) baseRedisService.hashGet(key, token); 
        return tokenRecord != null;
    }
    
}
