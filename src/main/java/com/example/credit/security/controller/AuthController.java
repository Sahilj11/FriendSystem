package com.example.credit.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.credit.security.dto.LoginDto;
import com.example.credit.security.dto.SignupDto;

/**
 * AuthController
 */
@Controller
@RequestMapping(path = "/auth")
public class AuthController {

   @PostMapping(path="/login")
   public void login(LoginDto loginDto){
      // check if req correct
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
