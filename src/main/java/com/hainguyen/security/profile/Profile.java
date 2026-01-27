package com.hainguyen.security.profile;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hainguyen.security.common.enums.EGender;
import com.hainguyen.security.common.model.BaseEntity;
import com.hainguyen.security.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
