package com.demo.security;

import com.demo.dto.LoginRequestDto;
import com.demo.dto.LoginResponseDto;
import com.demo.dto.SignupResponseDto;
import com.demo.entity.User;
import com.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

//        User user = (User) authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = authUtils.generateAccessToken(user);

        System.out.println("JWT Token: " + token);
        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        User user  = userRepo.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null){
            throw new BadCredentialsException("Invalid username");
        }

        user = userRepo.save(User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build());
        return new SignupResponseDto(user.getId(), user.getUsername());
    }
}
