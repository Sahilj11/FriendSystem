package com.example.credit.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.credit.security.dto.LoginDto;
import com.example.credit.security.dto.SignupDto;
import com.example.credit.security.service.AuthService;
import com.example.credit.security.service.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

/**
 * AuthController
 */
@Controller
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

   @PostMapping(path="/login")
   public void login(LoginDto loginDto){
       authService.loginAuth(loginDto);
     // login user to app
     // redirecting it to home page 
   } 
   @PostMapping(path="/signup")
   public void signup(SignupDto signupDto){
     // check if req correct
     // adding user to database
     // redirecting it to home page
   }

}
