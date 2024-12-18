package com.hainguyen.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hainguyen.security.model.Profile;
import com.hainguyen.security.repository.ProfileRepository;


public interface ProfileService {
    Long save(Profile profile);

    void deleteProfile(Long id);
}
