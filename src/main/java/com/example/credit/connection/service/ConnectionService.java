package com.example.credit.connection.service;

import com.example.credit.connection.dto.FriendReqResponse;
import com.example.credit.connection.dto.TypeAheadDto;
import com.example.credit.connection.dto.UserListDto;
import com.example.credit.connection.typeahead.schedule.TrieGeneratorService;
import com.example.credit.connection.typeahead.utils.TitleCase;
import com.example.credit.connection.typeahead.utils.TrieNode;
import com.example.credit.entities.Friend_request;
import com.example.credit.entities.UserEntity;
import com.example.credit.repo.FriendReqRepo;
import com.example.credit.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * AutoComplete
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {

    private final TrieGeneratorService tService;
    private final UserRepo userRepo;
    private final FriendReqRepo friendReqRepo;

    /**
     * Provides List of user having name similar to query , it use {@link TrieNode}
     * to search through the
     * user. Before generating {@link TypeAheadDto} result names are converted to
     * TitleCase. It also
     * Generates search url for each user found. If no user is found it returns
     * null.
     *
     * @param query search parameter
     * @return {@link TypeAheadDto} which contains name , id , searchUrl.
     */
    public ResponseEntity<List<TypeAheadDto>> taList(String query) {
        TrieNode root = tService.getRoot();
        log.info("Search Query of user {}", query);
        if (root == null) {
            log.error("Trie not generated");
            return ResponseEntity.ok(null);
        } else {
            List<String> search = root.search(root, query);
            List<TypeAheadDto> tAheadDtos = new ArrayList<>();
            String baseUrl = UriComponentsBuilder.fromPath("http://localhost:8080/api/search")
                    .queryParam("q", query)
                    .queryParam("page", 0)
                    .queryParam("size", 10)
                    .encode()
                    .build()
                    .toUriString();
            if (search.isEmpty()) {
                return ResponseEntity.ok(null);
            }
            for (int i = 0; i < search.size(); i++) {
                String temp = TitleCase.titleString(search.get(i));
                TypeAheadDto tDto = new TypeAheadDto(i, temp, baseUrl);
                tAheadDtos.add(tDto);
            }
            return ResponseEntity.ok(tAheadDtos);
        }
    }

    // TODO: need to return something other than sql id

    /**
     * Method for fetching list of users matching the query , pagination is applied.
     * Url example
     * /api/search?q=example&page=0&size=10
     *
     * @param pageable object contains page number and size of page
     * @param q        Search query
     * @return Provide list of {@link UserListDto} and null if no such users.
     */
    public ResponseEntity<List<UserListDto>> userList(Pageable pageable, String q) {
        List<UserEntity> byNameLike = userRepo.findByNameLike(q, pageable);
        if (byNameLike.isEmpty()) {
            return null;
        } else {
            List<UserListDto> uList = new ArrayList<>();
            for (UserEntity user : byNameLike) {
                uList.add(new UserListDto(user.getUserId(), user.getName(), user.getEmail()));
            }
            return ResponseEntity.ok(uList);
        }
    }

    // TODO: Handle Exception handling.
    public void sendFriendReq(int uid2, int uid1) {
        try {
            log.warn("User with id {} sending request to User with id {}", uid1,uid2);
            Friend_request friend_request = new Friend_request();
            friend_request.setRequestor("UID1");
            if (uid1 < uid2) {
                if (friendReqRepo.existsByUid1(uid1)) {
                    log.warn("User with id {} is already friends with User with id {}",uid1,uid2);
                    return;
                }
                friend_request.setUid1(uid1);
                friend_request.setUid2(uid2);
                friend_request.setRequestor("UID1");
            } else {
                if (friendReqRepo.existsByUid2(uid1)) {
                    log.warn("User with id {} is already friends with User with id {}",uid1,uid2);
                    return;
                }
                friend_request.setUid1(uid2);
                friend_request.setUid2(uid1);
                friend_request.setRequestor("UID2");
            }
            friendReqRepo.save(friend_request);
        }  catch (Exception e) {
            log.warn("Issue occurred while sending friend request from userId {} to userID {}. {}",uid1,uid2,e.getMessage());
        }
    }

    public void acceptFriendReq(FriendReqResponse friendReqResponse, int userId) {
        return;
    }
}