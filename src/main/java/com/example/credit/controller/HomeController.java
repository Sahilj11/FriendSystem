package com.example.credit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 */
@Controller
public class HomeController {

   @GetMapping(path="/home")
   public String home(){
      return "home"; 
   } 
}
