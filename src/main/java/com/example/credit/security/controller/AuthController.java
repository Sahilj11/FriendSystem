package com.example.credit.security.controller;

import com.example.credit.security.dto.LoginDto;
import com.example.credit.security.dto.SignupDto;
import com.example.credit.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** AuthController */
@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping(path = "/login")
    public String loginForm() {
        return "loginForm";
    }

    //@PostMapping(path = "/login")
    //public void login(LoginDto loginDto) {
    //    if (authService.loginAuth(loginDto)) {
    //    }
    //    // login user to app
    //    // redirecting it to home page
    //}

    @PostMapping(path = "/signup")
    public void signup(SignupDto signupDto) {
        // check if req correct
        // adding user to database
        // redirecting it to home page
    }
}
