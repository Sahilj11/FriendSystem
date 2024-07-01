package com.example.credit.connection.typeahead.utils;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrieNode {

    private static final int ALPHABET_SIZE = 27;
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

    // TODO: Add spaces into the trie
    /**
     * Insert word into trie
     *
     * @param root pointer to current node
     * @param word word need to be inserted
     */
    public void insert(TrieNode root, String word) {
        word = word.toLowerCase();
        TrieNode curr = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = getIndex(c);
            if (curr.children[index] == null) {
                curr.children[index] = new TrieNode();
            }
            curr = curr.children[index];
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
        log.warn("This is prefix " + prefix);
        prefix = prefix.toLowerCase();
        TrieNode curNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            int index = getIndex(c);
            if (curNode.children[index] == null) {
                return new ArrayList<>();
            }
            curNode = curNode.children[index];
        }
        List<String> results = new ArrayList<>();
        collectWords(curNode, results);
        return results;
    }

    /**
     * Helper function which through the trie
     *
     * @param curNode Pointer to current node
     * @param result  Array holding 5 string
     */
    private void collectWords(TrieNode curNode, List<String> result) {
        if (curNode == null || result.size() == 5) {
            return;
        }
        if (curNode.eow) {
            result.add(curNode.word);
        }
        for (int j = 0; j < ALPHABET_SIZE; j++) {
            if (curNode.children[j] != null) {
                collectWords(curNode.children[j], result);
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

    private int getIndex(char c) {
        if (c == ' ') {
            log.warn("This is a space");
            return 26;
        } else {
            return c - 'a';
        }
    }
}
