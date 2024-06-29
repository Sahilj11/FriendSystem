package com.example.credit.connection.controller;

import com.example.credit.connection.dto.UserListDto;
import com.example.credit.connection.service.SearchingService;
import com.example.credit.utils.inputvalidation.InputVal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** ConnectionController */
@RestController
@RequestMapping(path = "/api/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class ConnectionController {

    private final SearchingService searching;

    // TODO: what will be the case if no search query is there
    // TODO: return list of user
    @GetMapping(path = "search/ta")
    public ResponseEntity<List<String>> searchUser(@RequestParam(required = true) String q) {
        if (q.length() == 0 || InputVal.queryInvalid(q)) {
            return null;
        }
        return searching.taList(q);
    }

    @GetMapping(path = "search")
    public ResponseEntity<List<UserListDto>> searchUserList(
            @RequestParam(required = true) String q,
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size) {
        if (page == null || size == null || InputVal.queryInvalid(q)) {
            log.warn("Cannot be null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            if (page < 0 || size < 1) {
                log.warn("Page size or page number is invalid");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                Pageable pageable = PageRequest.of(page, size);
                log.warn(String.format("Page is %d and size is %d", page, size));
                return searching.userList(pageable, q);
            }
        }
    }
}
