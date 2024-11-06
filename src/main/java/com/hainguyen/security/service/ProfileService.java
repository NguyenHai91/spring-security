package com.hainguyen.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.model.Profile;
import com.hainguyen.security.repository.ProfileRepository;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Long save(Profile profile) {
        Profile savedProfile = profileRepository.save(profile);
        return savedProfile.getId();
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
