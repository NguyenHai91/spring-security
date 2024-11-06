package com.hainguyen.security.dto.response;

import java.util.List;

import com.hainguyen.security.model.Role;
import com.hainguyen.security.model.User;

import lombok.Setter;

@Setter
public class UserResponse {
    private Long id;
    
    private String email;
    
    private String username;

    private List<Role> roles;

    public static UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setRoles(user.getRoles());
        return userResponse;
    }
}
