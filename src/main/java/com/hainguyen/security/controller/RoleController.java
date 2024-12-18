package com.hainguyen.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hainguyen.security.model.Role;
import com.hainguyen.security.service.RoleService;

@RequestMapping("/api/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
      Role savedRole = roleService.create(role);
      return ResponseEntity.ok(savedRole);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles(){
      return ResponseEntity.ok(roleService.getAll());
    }
}
