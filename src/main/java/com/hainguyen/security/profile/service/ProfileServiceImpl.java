package com.hainguyen.security.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.profile.Profile;
import com.hainguyen.security.profile.ProfileRepository;


@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Long save(Profile profile) {
        Profile savedProfile = profileRepository.save(profile);
        return savedProfile.getId();
    }
    
    @Override
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
