package com.hainguyen.security.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
