package com.example.credit.security.service;

import com.example.credit.security.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** AuthService */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;

    public boolean loginAuth(LoginDto loginDto) {

        UserDetails userDetails = userDetailService.loadUserByUsername(loginDto.username());
        if (passwordEncoder.matches(loginDto.password(), userDetails.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
