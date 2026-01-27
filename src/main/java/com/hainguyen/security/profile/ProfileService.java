package com.hainguyen.security.profile;


public interface ProfileService {
    Long save(Profile profile);

    void deleteProfile(Long id);
}
