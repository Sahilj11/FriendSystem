package com.example.credit.security.controller;

import com.example.credit.security.domain.LoginDto;
import com.example.credit.security.domain.SignupDto;
import com.example.credit.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** AuthController */
@RestController
@RequestMapping(path = "/auth/")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    // TODO: add param to url after user logout like login?logout
    @PostMapping(path = "login")
    public String login(@RequestBody LoginDto loginDto) {
        log.info("Email entered by user is " + loginDto.email());
        if (authService.loginAuth(loginDto)) {
            return "Login success";
        } else {
            return "Login failed";
        }
    }

    @GetMapping(path = "signup")
    public String signup() {
        return "sign";
    }

    // TODO: redirect user to home page after account created
    @PostMapping(path = "signup")
    public String signup(@Valid @ModelAttribute SignupDto signupDto) {
        if (!signupDto.password().equals(signupDto.confirmPass()) || !signupDto.agreeterms()) {
            return "si";
        }
        if (authService.signupService(signupDto)) {
            return "reome";
        } else {
            return "sign";
        }
    }
}
