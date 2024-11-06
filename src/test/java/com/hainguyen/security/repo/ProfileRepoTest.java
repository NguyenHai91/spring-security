package com.hainguyen.security.repo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.hainguyen.security.model.Profile;
import com.hainguyen.security.model.Role;
import com.hainguyen.security.model.User;
import com.hainguyen.security.repository.ProfileRepository;

@DataJpaTest
public class ProfileRepoTest {
    
    @Autowired
    private ProfileRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProfile() {
        Role role = entityManager.find(Role.class, "ADMIN");

        User user = new User();
        user.setEmail("admintest@gmail.com");
        user.setPassword("admin");
        user.setRoles(List.of(role));
        User addedUser = entityManager.persist(user);

        Profile profile = new Profile();
        profile.setFullname("tester");
        profile.setGender("MALE");
        profile.setPhone("0909123789");
        profile.setCity("hcm");
        profile.setUser(addedUser);
        Profile addedProfile = repo.save(profile);

        assertNotNull(addedProfile);
        assertTrue(addedProfile.getId() > 0);
    }
}
