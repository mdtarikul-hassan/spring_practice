package com.accesspoint.service.impl;

import com.accesspoint.io.ProfileRequest;
import com.accesspoint.io.ProfileResponse;
import com.accesspoint.entity.UserEntity;
import com.accesspoint.repo.UserRepo;
import com.accesspoint.service.EmailService;
import com.accesspoint.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return convertToProfileResponse(existingUser);
    }

    @Override
    public void sendResetOtp(String email) {
        UserEntity existingUser = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        // generate otp ( 6 digit )
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));

        // calculate expiration (up to 10 minutes)
        Long expirationTime = System.currentTimeMillis()+10*60*1000;

        // update the user profile
        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpiredAt(expirationTime);

        // send into the database
        userRepo.save(existingUser);


        try{
            emailService.sendResetOtpEmail(existingUser.getEmail(), otp);
        } catch (Exception e) {
            throw new RuntimeException("Unable to sent otp!");
        }

    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity existingUser = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found "+email));

        if(existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        if(existingUser.getResetOtpExpiredAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired");
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpiredAt(0L);

        userRepo.save(existingUser);

    }

    @Override
    public void sendOtp(String email) {
        UserEntity existingUser = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found"));

        if(existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified()) {
            return;
        }

        // generate 6 digit otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));

        // calculate expiration (up to 5 minutes)
        Long expirationTime = System.currentTimeMillis()+5*60*1000;

        // update the userEntity
        existingUser.setVerifyOtp(otp);
        existingUser.setVerifyOtpExpiredAt(expirationTime);

        // save the database
        userRepo.save(existingUser);

        try{
            emailService.sendOtpEmail(existingUser.getEmail(), otp);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to sent otp!");
        }

    }

    @Override
    public void verifyOtp(String email, String otp) {
        UserEntity existingUser = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("email not found"));

        if(existingUser.getVerifyOtp() == null || !existingUser.getVerifyOtp().equals(otp)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        if(existingUser.getVerifyOtpExpiredAt() < System.currentTimeMillis()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP expired");
        }

        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOtp(null);
        existingUser.setVerifyOtpExpiredAt(0L);

        userRepo.save(existingUser);

    }

    private ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .userId(newProfile.getUserId())
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();

    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .verifyOtp(null)
                .verifyOtpExpiredAt(null)
                .resetOtp(null)
                .resetOtpExpiredAt(null)
                .build();
    }

}
