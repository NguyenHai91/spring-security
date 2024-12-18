package com.hainguyen.security.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.hainguyen.security.model.User;


public interface UserService {
  
  User findByUsername(String username);

  
  User findByEmail(String email);

  void changePassword(User user, String password);

  User save(User user);

  @PreAuthorize("hasAuthority('ADMIN')")
  List<User> getAll();
}
