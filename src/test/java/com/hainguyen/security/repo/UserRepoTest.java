package com.hainguyen.security.repo;

import javax.management.relation.Role;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hainguyen.security.model.User;
import com.hainguyen.security.repository.UserRepository;

@DataJpaTest
public class UserRepoTest {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser() {
        Role role = entityManager.find(Role.class, "ADMIN");
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        User addedUser = repo.save(user);
        assertNotNull(addedUser);
        assertTrue(addedUser.getId() > 0);
    }

    @Test
    public void testDeleteUser() {
        User user = entityManager.find(User.class, 1);
        repo.delete(user);
        assertNull(entityManager.find(User.class, 1));
    }
}
