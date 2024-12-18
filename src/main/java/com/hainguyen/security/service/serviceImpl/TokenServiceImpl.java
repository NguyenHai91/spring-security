package com.hainguyen.security.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.exception.CustomException;
import com.hainguyen.security.model.Token;
import com.hainguyen.security.model.User;
import com.hainguyen.security.repository.TokenRepository;
import com.hainguyen.security.security.jwt.JwtTokenUtils;
import com.hainguyen.security.service.BaseRedisService;
import com.hainguyen.security.service.TokenService;
import com.hainguyen.security.service.UserService;


@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseRedisService redisService;

    @Override
    public Token getByToken(String token) {
        Optional<Token> tokenRecord = tokenRepository.findByToken(token);
        return tokenRecord.orElse(null);
    }

    @Override
    public Token getByRefreshToken(String refreshToken) {
        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        return token.orElse(null);
    }

    @Override
    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    @Override
    public void updateToken(Token token) {
        Token editingToken = tokenRepository.findById(token.getId()).get();
        editingToken.builder()
            .token(token.getToken())
            .refreshToken(token.getRefreshToken())
            .status(token.isStatus())
            .build();
        tokenRepository.save(editingToken);
    }

    @Override
    public String forgotPassword(String email) {

        return null;
    }

    @Override
    public String resetPassword(String SECRET_KEY, String RESET_TOKEN) throws Exception {
        final String email = jwtTokenUtils.parseClaims(RESET_TOKEN).getSubject();
        User user = userService.findByEmail(email);
        if (user == null) {
        throw new CustomException("Reset_token invalid");
        }
        jwtTokenUtils.verifyToken(RESET_TOKEN);

        return "reset";
    }

    @Override
    public void updateStatus(String tokenString, boolean status) throws Exception {
        Token token = tokenRepository.findByToken(tokenString).get();
        token.setStatus(status);
        tokenRepository.save(token);
    }

    @Override
    public void delete(Long tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    @Override
    public List<Token> findAll() {
        return tokenRepository.findAll();
    }
        
}
