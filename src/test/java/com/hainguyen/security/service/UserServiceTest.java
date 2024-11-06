package com.hainguyen.security.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hainguyen.security.model.User;
import com.hainguyen.security.repository.UserRepository;

public class UserServiceTest {
    @MockBean
    private UserRepository repo;

    @InjectMocks
    private UserService service;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        Mockito.when(repo.save(user)).thenReturn(user);

        User addedUser = service.save(user);
        assertNotNull(addedUser);
        assertTrue(addedUser.getId() > 0);
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        Mockito.when(repo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User addedUser = service.findByEmail(user.getEmail());
        assertNotNull(addedUser);
        assertTrue(addedUser.getId() > 0);
    }
}
