package com.example.credit.connection.controller;

import com.example.credit.connection.dto.*;
import com.example.credit.connection.service.ConnectionService;
import com.example.credit.connection.service.FriendRequestService;
import com.example.credit.utils.JwtUtil;
import com.example.credit.utils.inputvalidation.InputVal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ConnectionController
 */
@RestController
@RequestMapping(path = "/api/")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connService;
    private final FriendRequestService friendRequestService;

    // TODO: what will be the case if no search query is there

    /**
     * Searches for user suggestions based on a query string for a type-ahead feature.
     * If the query string is empty or invalid, it returns an empty list.
     *
     * @param q the query string to search for user suggestions. It must not be empty or invalid according to {@link InputVal#queryInvalid(String)}.
     * @return a {@link ResponseEntity} containing a list of {@link TypeAheadDto} objects with user suggestions,
     * or an empty list if the query string is empty or invalid.
     */
    @GetMapping(path = "search/ta")
    public ResponseEntity<List<TypeAheadDto>> searchUser(@RequestParam String q) {
        if (q.isEmpty() || InputVal.queryInvalid(q)) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return connService.taList(q);
    }

    /**
     * Searches for a list of users based on a query string, page number, and page size.
     * If any of the parameters are invalid, it returns a {@link HttpStatus#BAD_REQUEST} response.
     *
     * @param q    the query string to search for users. It must not be null or invalid according to {@link InputVal#queryInvalid(String)}.
     * @param page the page number to retrieve. It must not be null and must be greater than or equal to 0.
     * @param size the number of items per page. It must not be null and must be greater than 0.
     * @return a {@link ResponseEntity} containing a list of {@link UserListDto} objects if the parameters are valid,
     * or a {@link HttpStatus#BAD_REQUEST} response if any parameters are invalid.
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

    /**
     * Handles the creation of a new friend request.
     * Extracts the user ID from the JWT token in the Authorization header and uses it as the sender of the friend request.
     *
     * @param friendReqDto the DTO containing the receiver's user ID for the friend request.
     * @param request      the HTTP request containing the Authorization header with the JWT token.
     * @return a {@link ResponseEntity} containing a message indicating the result of the friend request operation.
     */
    @PostMapping(path = "freq")
    public ResponseEntity<String> connectionReq(@RequestBody FriendReqDto friendReqDto, HttpServletRequest request) {
        int uId = JwtUtil.extractId(request);
        return friendRequestService.sendFriendReq(friendReqDto.receiverId(), uId);
    }

    /**
     * Handles actions on a friend request, such as accepting or denying the request.
     * Extracts the user ID from the JWT token in the Authorization header and performs the specified action.
     *
     * @param userId  the ID of the user who sent the friend request.
     * @param action  the action to be performed on the friend request (ACCEPT or DENY).
     * @param request the HTTP request containing the Authorization header with the JWT token.
     * @return a {@link ResponseEntity} containing a message indicating the result of the friend request action.
     * - HTTP 200 (OK) if the action is successfully performed.
     * - HTTP 400 (Bad Request) if the action is invalid or not allowed.
     * - HTTP 500 (Internal Server Error) if an error occurs while performing the action.
     */
    @PatchMapping(path = "freq")
    public ResponseEntity<String> actOnRequest(@RequestParam int userId, @RequestParam FriendReqResponse action, HttpServletRequest request) {
        int loggedId = JwtUtil.extractId(request);
        return switch (action) {
            case ACCEPT -> {
                log.warn("Request accepted of {}", userId);
                yield friendRequestService.acceptFriendReq(userId, loggedId);
            }
            case DENY -> {
                log.warn("Request denied of {}", userId);
                yield friendRequestService.rejectFriendReq(userId, loggedId);
            }
        };
    }

    /**
     * Handles the deletion of a friend request by the sender.
     * Extracts the user ID from the JWT token in the Authorization header and deletes the friend request sent by the user.
     *
     * @param userId  the ID of the user who is the receiver of the friend request.
     * @param request the HTTP request containing the Authorization header with the JWT token.
     * @return a {@link ResponseEntity} containing a message indicating the result of the delete operation.
     * - HTTP 200 (OK) if the friend request is successfully deleted.
     * - HTTP 400 (Bad Request) if the action is invalid or not allowed.
     * - HTTP 500 (Internal Server Error) if an error occurs while performing the deletion.
     */
    @DeleteMapping(path = "freq")
    public ResponseEntity<String> actOnRequestBySender(@RequestParam int userId, HttpServletRequest request) {
        int loggedId = JwtUtil.extractId(request);
        return friendRequestService.deleteSendRequest(userId, loggedId);
    }

    @GetMapping(path = "freq")
    public ResponseEntity<Page<FriendReqPendingDto>> getPendingRequest(@RequestParam boolean sent,Pageable pageable, HttpServletRequest request) {
        int loggedId = JwtUtil.extractId(request);
        return friendRequestService.getPendingRequest(sent,loggedId, pageable);
    }

    @GetMapping(path = "flist")
    public ResponseEntity<Page<FriendListDto>> getFriendList(Pageable pageable,HttpServletRequest request){
        int loggedId = JwtUtil.extractId(request);
        return friendRequestService.getFriendList(loggedId,pageable);
    }
}