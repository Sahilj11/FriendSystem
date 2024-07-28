package com.example.credit.connection.typeahead.schedule;

import com.example.credit.connection.typeahead.utils.TrieNode;
import com.example.credit.entities.UserEntity;
import com.example.credit.repo.UserRepo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrieGeneratorService {

    private final UserRepo userRepo;

    /**
     * -- GETTER --
     *  Getter for root of trie
     */
    @Getter
    private TrieNode root;

    public TrieGeneratorService(UserRepo userRepo) {
        this.userRepo = userRepo;
        this.root = new TrieNode();
    }

    /**
     * Periodically generates and updates the Trie with user names from the repository.
     * Scheduled to run at fixed intervals specified by the fixedDelay parameter.
     *
     * @return the updated root node of the Trie.
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
}
