package com.hainguyen.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
@Entity
public class Token extends BaseEntity<Long> {

  @Column(nullable= false)
  private String token;

  @Column(nullable = false)
  private String refreshToken;
  
  @Column(nullable=false)
  private boolean status;

  @NotNull
  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  private User user;

}
