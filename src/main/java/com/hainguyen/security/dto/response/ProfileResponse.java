package com.hainguyen.security.dto.response;

import java.util.Date;

import com.hainguyen.security.model.Profile;

import lombok.Setter;

@Setter
public class ProfileResponse {
    private Long id;

    private String fullName;

    private Date birthday;

    private String gender;

    private String phone;

    private String city;

    public static ProfileResponse mapToUserResponse(Profile profile) {
        ProfileResponse profileDto = new ProfileResponse();
        profileDto.setId(profile.getId());
        profileDto.setFullName(profile.getFullname());
        profileDto.setBirthday(profile.getBirthday());
        profileDto.setGender(profile.getGender());
        profileDto.setPhone(profile.getPhone());
        profileDto.setCity(profile.getCity());
        return profileDto;
    }
}
