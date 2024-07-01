package com.example.credit.connection.service;

import com.example.credit.connection.dto.UserListDto;
import com.example.credit.connection.typeahead.schedule.TrieGeneratorService;
import com.example.credit.connection.typeahead.utils.TitleCase;
import com.example.credit.connection.typeahead.utils.TrieNode;
import com.example.credit.entities.UserEntity;
import com.example.credit.repo.UserRepo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** AutoComplete */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchingService {

    private final TrieGeneratorService tService;
    private final UserRepo userRepo;

    /**
     * method for getting list of similar username for matching query in title casing
     *
     * @param query search parameter
     */
    public ResponseEntity<List<String>> taList(String query) {
        TrieNode root = tService.getRoot();
        if (root == null) {
            log.error("Trie not generated");
            return ResponseEntity.ok(List.of("Empty"));
        } else {
            List<String> search = root.search(root, query);
            if (search.isEmpty()) {
                return ResponseEntity.ok(search);
            }
            for (int i = 0; i < search.size(); i++) {
                String temp = TitleCase.titleString(search.get(i));
                search.set(i, temp);
            }
            return ResponseEntity.ok(search);
        }
    }

    // TODO: need to return something other than sql id
    /**
     * Method for fetching list of users matching the query , pagination is applied
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
