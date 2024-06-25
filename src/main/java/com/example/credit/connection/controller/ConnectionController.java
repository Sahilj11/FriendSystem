package com.example.credit.connection.controller;

import com.example.credit.connection.service.AutoComplete;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
/** ConnectionController */
@RestController
@RequestMapping(path = "/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class ConnectionController {

    private final AutoComplete aComplete;

    // TODO: what will be the case if no search query is there
    // TODO: return list of user
    @GetMapping(path = "")
    public ResponseEntity<List<String>> searchUser(
            @RequestParam(required = true) String q,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size) {
        try {
            int pageNumber = page.orElse(0);
            int pageSize = size.orElse(10);
            log.warn("Page is " + pageNumber + " and size is " + pageSize);
            return aComplete.searchList(q);
        } catch (IllegalArgumentException e) {
            log.warn("Illegal Argument");
            return ResponseEntity.badRequest().build();
        }
    }
}
