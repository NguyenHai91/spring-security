package com.hainguyen.security.service;

import com.hainguyen.security.model.Token;

public interface RedisTokenService {
    void setAuthToken(Token token);
    Token getAuthToken(String token);
    boolean checkAuthToken(String token);
    void deleteAuthToken(String token);
}
