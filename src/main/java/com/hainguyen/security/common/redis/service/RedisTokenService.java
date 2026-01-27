package com.hainguyen.security.common.redis.service;

import com.hainguyen.security.auth.token.Token;

public interface RedisTokenService {
    void setAuthToken(Token token);
    Token getAuthToken(String token);
    boolean checkAuthToken(String token);
    void deleteAuthToken(String token);
}
