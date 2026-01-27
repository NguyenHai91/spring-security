package com.hainguyen.security.role;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;


public interface RoleService {

    @PreAuthorize("hasAuthority('ADMIN')")
     Role create(Role role);

     Role getRoleById(String id);

     @PreAuthorize("hasAuthority('ADMIN')")
     List<Role> getAll();

    // Role getRoleByName(String name);
}
