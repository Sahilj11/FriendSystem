package com.example.credit.billing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/billing")
public class BillingController {

    @GetMapping(path = "")
    public void getBillHistory(){

    }
}
