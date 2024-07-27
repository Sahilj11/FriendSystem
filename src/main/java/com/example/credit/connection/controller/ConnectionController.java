package com.example.credit.connection.controller;

import com.example.credit.connection.dto.FriendReqDto;
import com.example.credit.connection.dto.FriendReqResponse;
import com.example.credit.connection.dto.TypeAheadDto;
import com.example.credit.connection.dto.UserListDto;
import com.example.credit.connection.service.ConnectionService;
import com.example.credit.utils.JwtUtil;
import com.example.credit.utils.inputvalidation.InputVal;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * ConnectionController
 */
@RestController
@RequestMapping(path = "/api/")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connService;

    // TODO: what will be the case if no search query is there

    /**
     * Provide list of name like the query string use trie
     *
     * @param q Query string
     * @return List of names like q
     */
    @GetMapping(path = "search/ta")
    public ResponseEntity<List<TypeAheadDto>> searchUser(@RequestParam String q) {
        if (q.isEmpty() || InputVal.queryInvalid(q)) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return connService.taList(q);
    }

    /**
     * Endpoint to get list of users
     *
     * @param q    Query String
     * @param page Page number
     * @param size Size of each page
     * @return {@link UserListDto} contains id name and email
     */
    @GetMapping(path = "search")
    public ResponseEntity<List<UserListDto>> searchUserList(@RequestParam String q, @RequestParam Integer page, @RequestParam Integer size) {
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
                log.warn("User in security context {}", SecurityContextHolder.getContext().getAuthentication().getName());
                return connService.userList(pageable, q);
            }
        }
    }

    // TODO: make profile url for each user
    // TODO: endpoint to set connection request must contain identifier for sender and receiver
    @PostMapping(path = "freq")
    public void connectionReq(@RequestBody FriendReqDto friendReqDto, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7).trim();
        int uId = JwtUtil.extractId(token);
        connService.sendFriendReq(friendReqDto.receiverId(),uId);
    }

    @PutMapping(path = "freq")
    public void actOnRequest(@RequestParam() int userId, @RequestParam() FriendReqResponse action){
        switch (action){
            case ACCEPT:
                log.warn("Request accepted of {}",userId);
                break;
            case DENY:
                log.warn("Request denied of {}",userId);
                break;
            default:
                throw new IllegalArgumentException("Invalid action: "+action);
        }
    }
}