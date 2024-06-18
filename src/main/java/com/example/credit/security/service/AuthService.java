package com.example.credit.security.service;

import com.example.credit.entities.UserEntity;
import com.example.credit.repo.RoleRepo;
import com.example.credit.repo.UserRepo;
import com.example.credit.security.dto.LoginDto;
import com.example.credit.security.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** AuthService. * */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final CustomUserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public boolean loginAuth(LoginDto loginDto) {
        log.info("This is service method " + loginDto.email());
        UserDetails userDetails = userDetailService.loadUserByUsername(loginDto.email());
        log.info(userDetails.toString());
        return passwordEncoder.matches(loginDto.password(), userDetails.getPassword());
    }

    public boolean signupService(SignupDto signupDto) {
        UserEntity uEntity = new UserEntity();
        try {
            uEntity.setName(signupDto.name());
            uEntity.setEmail(signupDto.email());
            uEntity.setPassword(passwordEncoder.encode(signupDto.password()));
            uEntity.setRole(roleRepo.findByRoleName("FREE"));
            userRepo.save(uEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
