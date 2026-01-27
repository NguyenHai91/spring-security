package com.hainguyen.security.profile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.profile.Profile;
import com.hainguyen.security.profile.ProfileRepository;


public interface ProfileService {
    Long save(Profile profile);

    void deleteProfile(Long id);
}
