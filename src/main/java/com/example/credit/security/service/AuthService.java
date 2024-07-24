package com.example.credit.security.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.credit.entities.UserEntity;
import com.example.credit.repo.RoleRepo;
import com.example.credit.repo.UserRepo;
import com.example.credit.security.domain.LoginDto;
import com.example.credit.security.domain.SignupDto;
import com.example.credit.utils.JwtUtil;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** AuthService. * */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    public ResponseEntity<String> loginAuth(LoginDto loginDto) {
       try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        UserDetails uDetails = userDetailsService.loadUserByUsername(loginDto.email());
        String username = uDetails.getUsername();
        String jwtToken = JwtUtil.generateToken(username,loginDto.email());
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
       } catch (Exception e) {
        // TODO: handle exception
        log.warn("Exception occured while authentication "+e);
           return new ResponseEntity<String>("Incorrect Credentials",HttpStatus.BAD_REQUEST);
       } 
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
