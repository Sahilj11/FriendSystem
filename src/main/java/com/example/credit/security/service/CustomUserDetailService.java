package com.example.credit.security.service;

import com.example.credit.entities.UserEntity;
import com.example.credit.repo.UserRepo;
import lombok.RequiredArgsConstructor;

import java.util.Optional;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** CustomUserDetailService */
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo usRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = usRepo.findByUniquename(username);
        try {
            var userEntity = user.orElseThrow();
            return new SecurityUser(userEntity);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username not exist");
        }
    }
}
