package com.accesspoint.service;

import com.accesspoint.entity.ProfileRequest;
import com.accesspoint.entity.ProfileResponse;

public interface UserService {

    public ProfileResponse createProfile(ProfileRequest request);
}
