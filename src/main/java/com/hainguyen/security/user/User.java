package com.hainguyen.security.user;

import java.util.List;

import com.hainguyen.security.common.model.BaseEntity;
import com.hainguyen.security.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Table(name = "users")
@Entity
public class User extends BaseEntity<Long> {
  
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @NotNull(message= "password is not null")
  @Min(value= 3, message="length password is more than 3 characters")
  @Max(value= 50, message="length password is less than 50 characters")
  private String password;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Role> roles;
}
