package com.hainguyen.security.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hainguyen.security.model.Role;
import com.hainguyen.security.repository.RoleRepository;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class RoleServiceTest {
    @MockBean
    private RoleRepository repo;

    @InjectMocks
    private RoleService service;

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setName("TEST");
        Mockito.when(repo.save(role)).thenReturn(role);
        Role addedRole = service.create(role);
        assertNotNull(addedRole);
        assertTrue((addedRole.getName()).equals("TEST"));
    }

    @Test
    public void testGetRole() {
        Role role = new Role();
        role.setName("TEST");
        Mockito.when(repo.findById(role.getName())).thenReturn(Optional.of(role));
        Role roleNew = service.getRoleById(role.getName());
        assertNotNull(roleNew);
    }
}
