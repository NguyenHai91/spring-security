package com.hainguyen.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.model.Role;
import com.hainguyen.security.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public Role getRoleById(String id) {
        return roleRepository.findById(id).get();
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    // public Role getRoleByName(String name) {
    //     return roleRepository.findByName(name).get();
    // }
}
