package com.hainguyen.security.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.hainguyen.security.common.model.BaseEntity;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.hainguyen.security.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Table(name = "users")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private Long id;
  
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @NotNull(message= "password is not null")
  @Min(value= 3, message="length password is more than 3 characters")
  @Max(value= 50, message="length password is less than 50 characters")
  private String password;

  @OneToMany(fetch = FetchType.EAGER)
  private List<Role> roles;

  @CreatedDate
  private LocalDate createdAt;

  @UpdateTimestamp
  private Date updatedAt;
}
