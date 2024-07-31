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

    /**
     * Authenticates a user using the provided login credentials.
     * If authentication is successful, generates a JWT token for the user.
     *
     * @param loginDto the login data transfer object containing the user's email and password.
     * @return a {@link ResponseEntity} containing the JWT token and HTTP status code.
     *         - HTTP 200 OK with the JWT token if authentication is successful.
     *         - HTTP 400 BAD REQUEST with an error message if authentication fails.
     */
    public ResponseEntity<String> loginAuth(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
            SecurityUser uDetails = (SecurityUser) userDetailsService.loadUserByUsername(loginDto.email());
            String username = uDetails.getUsername();
            int uId = uDetails.getUserID();
            String jwtToken = JwtUtil.generateToken(username, loginDto.email(),uId);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.warn("Exception occured while authentication {}", e.getMessage());
            return new ResponseEntity<>("Incorrect Credentials", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Registers a new user using the provided signup details.
     * If the registration is successful, generates a JWT token for the new user.
     *
     * @param signupDto the signup data transfer object containing the user's name, email, and password.
     * @return a {@link ResponseEntity} containing the JWT token and HTTP status code.
     *         - HTTP 201 CREATED with the JWT token if registration is successful.
     *         - HTTP 400 BAD REQUEST with an error message if the email is already in use or other validation issues occur.
     *         - HTTP 500 INTERNAL SERVER ERROR with a generic error message if an unknown error occurs.
     */
    public ResponseEntity<String> signupService(SignupDto signupDto) {
        UserEntity uEntity = new UserEntity();
        try {
            uEntity.setName(signupDto.name());
            uEntity.setEmail(signupDto.email());
            uEntity.setPassword(passwordEncoder.encode(signupDto.password()));
            uEntity.setRole(roleRepo.findByRoleName("FREE"));
            userRepo.save(uEntity);
            int uId = uEntity.getUserId();
            String jwtToken = JwtUtil.generateToken(signupDto.name(), signupDto.email(),uId);
            return new ResponseEntity<>(jwtToken, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                log.warn("Email id {} already in use.\n{}", signupDto.email(), e.getMessage());
                return new ResponseEntity<>("Email id already in use.", HttpStatus.BAD_REQUEST);
            }
                return new ResponseEntity<>("Some issue with values entered.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.warn("Error occurred while creating new account {}", e.getMessage());
            return new ResponseEntity<>("Something bad happened , Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
