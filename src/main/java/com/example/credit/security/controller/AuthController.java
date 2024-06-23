package com.example.credit.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.credit.security.domain.LoginDto;
import com.example.credit.security.domain.*;
import com.example.credit.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** AuthController */
@Controller
@RequestMapping(path = "/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping(path = "login")
    public String login() {
        return "loginform";
    }

    // TODO: add param to url after user logout like login?logout
    @PostMapping(path = "login")
    public void login(@ModelAttribute LoginDto loginDto) {
        log.info(loginDto.email());
        authService.loginAuth(loginDto);
    }

    @GetMapping(path = "signup")
    public String signup() {
        return "signupform";
    }

    // TODO: redirect user to home page after account created
    @PostMapping(path = "signup")
    public String signup(@Valid @ModelAttribute SignupDto signupDto) {
        if (!signupDto.password().equals(signupDto.confirmPass()) || !signupDto.agreeterms()) {
            return "signupform";
        }
        if (authService.signupService(signupDto)) {
            return "redirect:/home";
        } else {
            return "signupform";
        }
    }
}
