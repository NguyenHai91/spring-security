package com.hainguyen.security.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.model.Role;
import com.hainguyen.security.repository.RoleRepository;
import com.hainguyen.security.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(String id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
    
    // @Override
    // public Role getRoleByName(String name) {
    //     return roleRepository.findByName(name).get();
    // }
}
