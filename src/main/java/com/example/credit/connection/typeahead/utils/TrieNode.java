package com.example.credit.connection.typeahead.utils;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrieNode {

    private static final int ALPHABET_SIZE = 26;
    private boolean eow;
    private TrieNode[] children;
    private String word;

    /** Initialize {@link TrieNode} eow to false and node array to null */
    public TrieNode() {
        this.eow = false;
        this.children = new TrieNode[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            children[i] = null;
        }
    }

    /**
     * Insert word into trie
     *
     * @param root pointer to current node
     * @param word word need to be inserted
     */
    public void insert(TrieNode root, String word) {
        word = word.replaceAll("\\s+", "").toLowerCase();
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (curr.children[c - 'a'] == null) {
                TrieNode addNode = new TrieNode();
                curr.children[c - 'a'] = addNode;
            }
            curr = curr.children[c - 'a'];
        }
        curr.eow = true;
        curr.word = word;
    }

    /**
     * Search for all words that starts with param
     *
     * @param root   Pointer to node
     * @param prefix word to be searched
     * @return List of 5 words that starts with prefix
     */
    public List<String> search(TrieNode root, String prefix) {
        prefix = prefix.replaceAll("\\s+", "").toLowerCase();
        log.warn(prefix);
        TrieNode curNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (curNode.children[c - 'a'] == null) {
                return new ArrayList<>();
            }
            curNode = curNode.children[c - 'a'];
        }
        List<String> results = new ArrayList<>();
        collectWords(curNode, results, 0);
        return results;
    }

    /**
     * Helper function which through the trie
     *
     * @param curNode Pointer to current node
     * @param result  Array holding 5 string
     * @param i       counter to ensure only 5 words are added
     */
    private static void collectWords(TrieNode curNode, List<String> result, int i) {
        if (curNode == null || i > 5) {
            return;
        }
        if (curNode.eow) {
            result.add(curNode.word);
            i++;
        }
        for (int j = 0; j < ALPHABET_SIZE; j++) {
            if (curNode.children[j] != null) {
                collectWords(curNode.children[j], result, i);
            }
        }
    }

    public void printAll(TrieNode curNode) {
        if (curNode == null) {
            return;
        }
        if (curNode.eow) {
            log.info(curNode.word);
        }
        for (int j = 0; j < ALPHABET_SIZE; j++) {
            if (curNode.children[j] != null) {
                printAll(curNode.children[j]);
            }
        }
    }
}
