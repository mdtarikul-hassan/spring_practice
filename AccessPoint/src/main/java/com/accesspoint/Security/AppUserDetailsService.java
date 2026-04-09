package com.accesspoint.Security;

import com.accesspoint.entity.UserEntity;
import com.accesspoint.repo.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    public AppUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity existingUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found for the email : "+email));
        return new User(existingUser.getEmail(),existingUser.getPassword(), new ArrayList<>());
    }


}
