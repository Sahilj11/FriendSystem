package com.example.credit.connection.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.credit.connection.service.AutoComplete;

import lombok.RequiredArgsConstructor;

/** ConnectionController */
@RestController
@RequestMapping(path = "/api/search")
@RequiredArgsConstructor
public class ConnectionController {

    private final AutoComplete aComplete;

    @GetMapping(path = "")
    public ResponseEntity<List<String>> searchUser(@RequestParam(required = true) String q) {
         return aComplete.searchList(q);
    }
}
