package com.hainguyen.security.security.oauth2;
import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.hainguyen.security.model.User;

@Component
public class JWTtoUserConvertor implements Converter<Jwt, UsernamePasswordAuthenticationToken>{

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt jwt) {
        User user = new User();
        user.setEmail(jwt.getSubject());
        return new UsernamePasswordAuthenticationToken(user, jwt, Collections.EMPTY_LIST);
    }
    
}
