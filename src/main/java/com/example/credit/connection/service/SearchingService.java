package com.example.credit.connection.service;

import com.example.credit.connection.typeahead.schedule.TrieGeneratorService;
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
     * method for getting list of similar username for matching query
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
            return ResponseEntity.ok(search);
        }
    }

    public ResponseEntity<List<String>> userList(Pageable pageable, String q) {
        List<UserEntity> byNameLike = userRepo.findByNameLike(q, pageable);
        if (byNameLike.isEmpty()) {
            return null;
        }else{
            List<String> uList = new ArrayList<>();
            for (UserEntity user : byNameLike) {
                uList.add(user.getName());
            }
            return ResponseEntity.ok(uList);
        }
    }
}
