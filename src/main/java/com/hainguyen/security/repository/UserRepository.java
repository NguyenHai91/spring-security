package com.hainguyen.security.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hainguyen.security.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.username=?1")
  User findByUsername(String username);

  @Query("SELECT u FROM User u WHERE u.email=?1")
  Optional<User> findByEmail(String email);

  @Query("SELECT u FROM User u JOIN u.roles r WHERE u.id LIKE %?1 OR u.email LIKE %?1 OR u.username LIKE %?1 OR r.name LIKE %?1")
  Page<User> findAll(String keyword, Pageable pagable);

}
