package com.example.credit.security.controller;

import com.example.credit.security.dto.LoginDto;
import com.example.credit.security.dto.SignupDto;
import com.example.credit.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

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

    @PostMapping(path = "login")
    public void login(@ModelAttribute LoginDto loginDto) {
        log.info(loginDto.email());
        authService.loginAuth(loginDto);
    }

    @GetMapping(path = "signup")
    public String signup() {
        return "signupform";
    }

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
