package com.accesspoint.service;

import com.accesspoint.io.ProfileRequest;
import com.accesspoint.io.ProfileResponse;

public interface UserService {

    public ProfileResponse createProfile(ProfileRequest request);

    public ProfileResponse getProfile(String email);

    public void sendResetOtp(String email);

    public void resetPassword(String email, String otp, String newPassword);

    public void sendOtp(String email);

    public void verifyOtp(String email, String otp);

}
