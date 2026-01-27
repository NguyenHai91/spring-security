package com.hainguyen.security.user.service;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

import com.hainguyen.security.user.User;

public interface UserService {
  
  User findByUsername(String username);

  
  User findByEmail(String email);

  void changePassword(User user, String password);

  User save(User user);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<User> getAll();
}
