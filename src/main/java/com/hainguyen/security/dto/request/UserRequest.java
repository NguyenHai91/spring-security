package com.hainguyen.security.dto.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hainguyen.security.enums.EGender;
import com.hainguyen.security.model.Profile;
import com.hainguyen.security.model.Role;
import com.hainguyen.security.model.User;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class UserRequest implements Serializable {
    private Long id;

    @Email(message = "email is empty or invalid")
    private String email;
    
    private String username;

    @NotBlank(message = "password is not empty")
    private String password;

    @NotBlank(message = "full name is not blank")
    private String fullName;

    @NotBlank(message = "birthday is not blank")
    private Date birthday;

    @Max(value = 250, message="city max 250 characters")
    private String city;

    @Pattern(regexp = "^\\d{10}$", message = "phone number invalid")
    private String phone;

    private String gender;

    private List<Role> roles;

    public static User mapToEntity(UserRequest userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public static Profile mapToProfile(UserRequest userDto) {
        Profile profile = new Profile();
        profile.setFullname(userDto.getFullName());
        profile.setBirthday(userDto.getBirthday());
        profile.setCity(userDto.getCity());
        profile.setPhone(userDto.getPhone());
        profile.setGender(userDto.getGender());
        return profile;
    }

}
