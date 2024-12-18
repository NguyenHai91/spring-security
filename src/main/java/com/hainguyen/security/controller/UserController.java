package com.hainguyen.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hainguyen.security.config.i18n.Translator;
import com.hainguyen.security.dto.request.UserRequest;
import com.hainguyen.security.model.Profile;
import com.hainguyen.security.model.Role;
import com.hainguyen.security.model.User;
import com.hainguyen.security.service.ProfileService;
import com.hainguyen.security.service.RoleService;
import com.hainguyen.security.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/api/user")
@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private ProfileService profileService;

  @Autowired
  private RoleService roleService;

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    List<User> listUser = userService.getAll();
    return ResponseEntity.ok(listUser);
  }

  @PostMapping("/save")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userDto) {
    if (userService.findByEmail(userDto.getEmail()).getId() > 0) {
      return ResponseEntity.badRequest().body("Email had been register");
    }
    User newUser = UserRequest.mapToEntity(userDto);
    List<Role> roles = new ArrayList<>();
    for (Role item : userDto.getRoles()) {
        Role role = roleService.getRoleById(item.getName());
        roles.add(role);
    }
    newUser.setRoles(userDto.getRoles());
    userService.save(newUser);
   
    Profile profile = new Profile();
    profile = UserRequest.mapToProfile(userDto);
    profile.setUser(newUser);
    profileService.save(profile);
    return ResponseEntity.ok("Create user success");
  }

  @PostMapping("/update")
  @PostAuthorize("returnObject.username == authentication.principle.username")
  public ResponseEntity<?> updateUser(@RequestBody UserRequest userDto){
    User updatingUser = userService.findByUsername(userDto.getUsername());
    if (updatingUser == null) {
      return ResponseEntity.badRequest().body("User not found");
    }
    User editingUser = UserRequest.mapToEntity(userDto);
    List<Role> roles = new ArrayList<>();
    userDto.getRoles().stream().map(new Function<Role, Role>() {
        @Override
        public Role apply(Role item) {
            return roleService.getRoleById(item.getName());
        }
    }).forEachOrdered(role -> {
        roles.add(role);
      });
    updatingUser.setRoles(userDto.getRoles());
    updatingUser.setUsername(editingUser.getUsername());
    updatingUser.setPassword(editingUser.getPassword());
    userService.save(updatingUser);

    profileService.save(UserRequest.mapToProfile(userDto));
    return ResponseEntity.badRequest().body("Update user success");
  }


  @GetMapping("/lang")
  public ResponseEntity language() {
    return ResponseEntity.ok(Translator.toLocale("user.add.success"));
  }

}
