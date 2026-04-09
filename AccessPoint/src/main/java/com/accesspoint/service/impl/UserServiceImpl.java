package com.accesspoint.service.impl;

import com.accesspoint.entity.ProfileRequest;
import com.accesspoint.entity.ProfileResponse;
import com.accesspoint.entity.UserEntity;
import com.accesspoint.repo.UserRepo;
import com.accesspoint.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        if(!userRepo.existsByEmail(request.getEmail())) {
            UserEntity newProfile = convertToUserEntity(request);
            newProfile = userRepo.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Your email is already exits");
    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .userId(newProfile.getUserId())
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .build();

    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .isAccountVerified(true)
                .verifyOtp(null)
                .verifyOtpExpiredAt(null)
                .resetOtp(null)
                .resetOtpExpiredAt(null)
                .build();
    }

}
