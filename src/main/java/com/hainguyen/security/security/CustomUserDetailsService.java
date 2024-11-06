package com.hainguyen.security.security;

import com.hainguyen.security.model.User;
import com.hainguyen.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {
  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userService.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        authorities
    );
  }
}
