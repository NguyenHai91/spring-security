package com.hainguyen.security.service;

import java.util.List;

import com.hainguyen.security.model.Token;


public interface TokenService {

   Token getByToken(String token);

   Token getByRefreshToken(String refreshToken);

   Token save(Token token);

   void updateToken(Token token);

   String forgotPassword(String email);

   String resetPassword(String SECRET_KEY, String RESET_TOKEN) throws Exception;

   void updateStatus(String tokenString, boolean status) throws Exception;

   void delete(Long tokenId);

   List<Token> findAll();
}
