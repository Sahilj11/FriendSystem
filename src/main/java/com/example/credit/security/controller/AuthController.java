package com.example.credit.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.credit.security.dto.LoginDto;
import com.example.credit.security.dto.SignupDto;
import com.example.credit.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** AuthController */
@Controller
@RequestMapping(path = "/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping(path = "login")
    public String login() {
        return "loginform";
    }

    @PostMapping(path = "login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        if (authService.loginAuth(loginDto)) {
            return "home";
        }else{
            return "loginform";
        }
    }

    @GetMapping(path = "signup")
    public String signup() {
        return "signupform";
    }

    @PostMapping(path = "signup")
    public String signup(@Valid @RequestBody SignupDto signupDto) {
        if (!signupDto.password().equals(signupDto.confirmPass()) || signupDto.agreeterms() == false) {
           return "signupform"; 
        }
        if (authService.signupService(signupDto)) {
            return "home";
        }
        return "signupform";
    }
}
