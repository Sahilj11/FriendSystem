package com.example.credit.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.credit.security.domain.LoginDto;
import com.example.credit.security.domain.SignupDto;
import com.example.credit.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AuthController
 */
@RestController
@RequestMapping(path = "/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("Email entered by user is " + loginDto.email());
        return authService.loginAuth(loginDto);
    }

    @PostMapping(path = "signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) {
        if (!signupDto.password().equals(signupDto.confirmPass()) || !signupDto.agreeterms()) {
            return new ResponseEntity<>("Password not match or terms not aggred", HttpStatus.BAD_REQUEST);
        }
        return authService.signupService(signupDto);
    }
}
