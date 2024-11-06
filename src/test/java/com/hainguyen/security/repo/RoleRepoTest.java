package com.hainguyen.security.repo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hainguyen.security.model.Role;
import com.hainguyen.security.repository.RoleRepository;

@DataJpaTest
public class RoleRepoTest {
    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setName("ADMIN");
        role.setDescription("all permission on system");
        Role addedRole = repo.save(role);
        assertNotNull(addedRole);
        assertTrue(addedRole.getName().length() > 0);
    }

    @Test
    public void testUpdateRole() {
        Role role = repo.findById("ADMIN").get();
        role.setDescription("");
        repo.save(role);
        assertTrue(repo.save(role).getDescription().equals(""));
    }

    @Test
    public void testDeleteRole() {
        Role role = repo.findById("ADMIN").get();
        repo.deleteById(role.getName());
        assertNull(repo.findById(role.getName()));
    }
}
