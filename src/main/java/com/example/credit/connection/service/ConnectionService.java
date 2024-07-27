package com.example.credit.connection.service;

import com.example.credit.connection.dto.TypeAheadDto;
import com.example.credit.connection.dto.UserListDto;
import com.example.credit.connection.typeahead.schedule.TrieGeneratorService;
import com.example.credit.connection.typeahead.utils.TitleCase;
import com.example.credit.connection.typeahead.utils.TrieNode;
import com.example.credit.entities.UserEntity;
import com.example.credit.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoComplete
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {

    private final TrieGeneratorService tService;
    private final UserRepo userRepo;

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
}