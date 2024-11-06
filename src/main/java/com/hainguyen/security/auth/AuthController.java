package com.hainguyen.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hainguyen.security.dto.request.AuthRequest;
import com.hainguyen.security.dto.request.ResetPassword;
import com.hainguyen.security.dto.response.AuthResponse;
import com.hainguyen.security.exception.CustomException;
import com.hainguyen.security.model.Token;
import com.hainguyen.security.model.User;
import com.hainguyen.security.security.RedisTokenService;
import com.hainguyen.security.security.jwt.JwtTokenUtils;
import com.hainguyen.security.service.TokenService;
import com.hainguyen.security.service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  
  @Autowired
  private JwtTokenUtils jwtTokenUtils;

  @Autowired
  private UserService userService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private RedisTokenService redisTokenService;

  @PostMapping(value = "/login")
  public ResponseEntity login(@RequestBody AuthRequest authLogin) {
      User user = userService.findByEmail(authLogin.getEmail());
      if (user == null) {
        return ResponseEntity.badRequest().body("Can not found your email");
      }
      if (!bCryptPasswordEncoder.matches(authLogin.getPassword(), user.getPassword())) {
        return ResponseEntity.badRequest().body("Password not match");
      }
      Token tokenRecord = createTokenRecord(user);
      tokenService.save(tokenRecord);
      redisTokenService.createToken(tokenRecord);
      log.info("Logged token to redis: {}", tokenRecord.getId());
      
      AuthResponse authResponse = AuthResponse.builder()
          .username(user.getEmail())
          .token(tokenRecord.getToken())
          .refreshToken(tokenRecord.getRefreshToken())
          .isAuthenticated(true)
          .build();
          
      return ResponseEntity.ok(authResponse);
  }

  private Token createTokenRecord(User user) {
    String token = jwtTokenUtils.createToken(user);
    String refreshToken = jwtTokenUtils.createRefreshToken(user);
    Token tokenRecord = Token.builder()
        .user(user)
        .token(token)
        .refreshToken(refreshToken)
        .status(true)
        .build();
    return tokenRecord;
  }

  @PostMapping("/refresh-token")
  public ResponseEntity refreshToken(@RequestParam String refreshToken) throws Exception {
    if (jwtTokenUtils.verifyToken(refreshToken)) {
      Claims claims = jwtTokenUtils.parseClaims(refreshToken);
      String subject = claims.getSubject();
      User user = userService.findByEmail(subject);
      if (user == null) {
        return ResponseEntity.badRequest().body("Not found email");
      }
      String newToken = jwtTokenUtils.createToken(user);
      Token tokenRecord = tokenService.getByRefreshToken(refreshToken);
      tokenRecord.setToken(newToken);
      tokenService.updateToken(tokenRecord);
      return ResponseEntity.ok(newToken);
    }
    return ResponseEntity.badRequest().body("Refresh token is invalid");
  }

  @PostMapping("/logout")
  public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) throws Exception, CustomException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.badRequest().body("User not authenticated");
    }
    String token = jwtTokenUtils.getToken(request);
    if (token == null) {
      return ResponseEntity.badRequest().body("User not authenticated");
    }
    Token tokenRecord = tokenService.getByToken(token);
    if (tokenRecord == null || !tokenRecord.isStatus()) {
      return ResponseEntity.badRequest().body("Token invalid");
    }
    jwtTokenUtils.verifyToken(token);
    new SecurityContextLogoutHandler().logout(request, response, authentication);
    authentication.setAuthenticated(false);
    tokenService.delete(tokenRecord.getId());
    return ResponseEntity.ok("User is logout");
  }

  @PostMapping("/forgot-password")
  public ResponseEntity forgotPassword(String email) {
    //check email is exist or not
    User user = userService.findByEmail(email);
    if (user == null) {
      throw new CustomException("Email not found");
    }
    //user is active or not

    //generate reset token
    final String resetKey = jwtTokenUtils.createResetKey(user);
    Token tokenRecord = new Token();
    tokenRecord.setToken(resetKey);
    tokenRecord.setUser(user);
    tokenRecord.setStatus(true);
    tokenService.save(tokenRecord);
    //send email confirm link
    String confirmLink = String.format("curl --location 'http://localhost:3000/api/auth/confirm-reset' \\\n" +
                         "header 'accept: */*' \\\n" +
                         "header 'Content-Type: application/json' \\\n" +
                         "--data 'resetKey:%s'", resetKey);
    return ResponseEntity.ok(confirmLink);
  }

  @PostMapping("/confirm-reset")
  public ResponseEntity confirmReset(@RequestParam String resetKey) throws Exception {
    jwtTokenUtils.verifyToken(resetKey);
    final String email = jwtTokenUtils.parseClaims(resetKey).getSubject();
    User user = userService.findByEmail(email);
    if (user == null) {
      return ResponseEntity.ok("can not found user");
    }
    return ResponseEntity.ok("confirm success");
  }

  @PostMapping("/change-password")
  public ResponseEntity changePassword(@RequestBody ResetPassword resetPassword) throws Exception {
    String password = resetPassword.getPassword();
    String confirmPassword = resetPassword.getConfirmPassword();
    if (!confirmPassword.equals(password)) {
      return ResponseEntity.badRequest().body("confirm password not match password, try agains");
    }
    String resetToken = resetPassword.getSecretToken();
    Token tokenRecord = tokenService.getByToken(resetToken);
    if (tokenRecord == null || tokenRecord.isStatus() == false) {
      return ResponseEntity.badRequest().body("reset-token invalid");
    }
    String email = jwtTokenUtils.parseClaims(resetToken).getSubject();
    User user = userService.findByEmail(email);
    if (user == null) {
      return ResponseEntity.badRequest().body("Can not found user match");
    }
    jwtTokenUtils.verifyToken(resetToken);
    userService.changePassword(user, password);
    return ResponseEntity.ok("password had changed success");
  }
}
