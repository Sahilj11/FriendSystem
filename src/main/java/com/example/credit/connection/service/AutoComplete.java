package com.example.credit.connection.service;

import com.example.credit.connection.schedule.TrieGeneratorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** AutoComplete */
@Service
@RequiredArgsConstructor
@Slf4j
public class AutoComplete {

    private final TrieGeneratorService tService;


    /**
     * method for getting list of similar username for matching query
     * 
     * @param query search parameter
     */
    public ResponseEntity<List<String>> searchList(String query) {
        TrieNode root = tService.getRoot();
        if (root == null) {
            log.error("Trie not generated");
            return ResponseEntity.ok(List.of("Empty"));
        } else {
            List<String> search = root.search(root, query);
            return ResponseEntity.ok(search);
        }
    }
}
