package com.example.credit.security.service;

import com.example.credit.entities.UserEntity;
import com.example.credit.repo.RoleRepo;
import com.example.credit.repo.UserRepo;
import com.example.credit.security.domain.LoginDto;
import com.example.credit.security.domain.SignupDto;
import com.example.credit.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
            UserDetails uDetails = userDetailsService.loadUserByUsername(loginDto.email());
            String username = uDetails.getUsername();
            String jwtToken = JwtUtil.generateToken(username, loginDto.email());
            return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Exception occured while authentication " + e.getMessage());
            return new ResponseEntity<String>("Incorrect Credentials", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> signupService(SignupDto signupDto) {
        UserEntity uEntity = new UserEntity();
        try {
            uEntity.setName(signupDto.name());
            uEntity.setEmail(signupDto.email());
            uEntity.setPassword(passwordEncoder.encode(signupDto.password()));
            uEntity.setRole(roleRepo.findByRoleName("FREE"));
            userRepo.save(uEntity);
            String jwtToken = JwtUtil.generateToken(signupDto.name(), signupDto.email());
            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                log.warn("Email id " + signupDto.email() + " already in use.\n" + e.getMessage());
                return new ResponseEntity<>("Email id already in use.", HttpStatus.BAD_REQUEST);
            }
                return new ResponseEntity<>("Some issue with values entered.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.warn("Error occured while creating new account " + e.getMessage());
            return new ResponseEntity<>("Something bad happended , Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }
}
