package com.example.credit.connection.typeahead.schedule;

import com.example.credit.connection.typeahead.utils.TrieNode;
import com.example.credit.entities.UserEntity;
import com.example.credit.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrieGeneratorService {

    private final UserRepo userRepo;
    private TrieNode root;

    public TrieGeneratorService(UserRepo userRepo) {
        this.userRepo = userRepo;
        this.root = new TrieNode();
    }

    /**
     * Schedule task, generate trie for typeahead feature
     *
     * @return refernce to the root of new generated trie
     */
    //@Scheduled(cron = "0 0 0 * * SUN")
    @Scheduled(fixedDelay = 100000)
    public TrieNode trieGenerate() {
        TrieNode newRoot = new TrieNode();
        String[] names = userRepo.findAll().stream().map(UserEntity::getName).toArray(String[]::new);
        for (String entity : names) {
            newRoot.insert(newRoot, entity.toLowerCase());
        }
        this.root = newRoot;
        return root;
    }

    /**
     * Getter for root of trie
     *
     * @return reference of root
     */
    public TrieNode getRoot() {
        // root.printAll(root);
        return root;
    }
}
