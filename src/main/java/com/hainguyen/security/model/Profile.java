package com.hainguyen.security.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hainguyen.security.enums.EGender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="profiles")
public class Profile extends BaseEntity<Long> {
    @NotNull(message="fullname is not null")
    private String fullname;

    @NotNull(message="gender is not null")
    private String Gender;

    @NotNull(message="birthday is not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    @Pattern(regexp = "^\\d{10}$", message = "phone number invalid")
    private String phone;

    @Max(value=250, message="Length city too long, max is 250 characters")
    private String city;

    @OneToOne(fetch=FetchType.LAZY)
    private User user;
}
