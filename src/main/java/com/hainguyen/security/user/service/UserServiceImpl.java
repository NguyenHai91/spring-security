package com.hainguyen.security.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.common.exception.CustomException;
import com.hainguyen.security.user.User;
import com.hainguyen.security.user.UserRepository;


import jakarta.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository repo;

  @Override
  public User findByUsername(String username) {
    return repo.findByUsername(username);
  }

  @Override
  public User findByEmail(String email) {
    return repo.findByEmail(email).get();
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void changePassword(User user, String password) {
    user.setPassword(password);
    repo.save(user);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public User save(User user) {
    try{
      return repo.save(user);
    } catch (Exception ex) {
      throw new CustomException(ex.getMessage());
    }
  }

  @Override
  public List<User> getAll() {
    return repo.findAll();
  }
}
