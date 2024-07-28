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

    /**
     * Initializes a new TrieNode with the default settings.
     * Sets the end-of-word flag to false and initializes the children array with null values.
     */
    public TrieNode() {
        this.eow = false;
        this.children = new TrieNode[ALPHABET_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            children[i] = null;
        }
    }

    /**
     * Inserts a word into the Trie starting from the given root node.
     * Converts the word to lowercase, then iterates through its characters to create or traverse nodes.
     * Marks the end of the word in the Trie.
     *
     * @param root the root node of the Trie.
     * @param word the word to be inserted into the Trie.
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
     * Searches for words in the Trie that start with the given prefix.
     * Converts the prefix to lowercase, then traverses the Trie to find the corresponding node.
     * Collects all words that start with the given prefix.
     *
     * @param root the root node of the Trie.
     * @param prefix the prefix to search for in the Trie.
     * @return a list of words that start with the given prefix. If no words are found, returns an empty list.
     */
    public List<String> search(TrieNode root, String prefix) {
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
     * Collects words from the Trie starting from the given node and adds them to the result list.
     * The collection stops if the current node is null or if the result list already contains 5 words.
     *
     * @param curNode the current node in the Trie from which to collect words.
     * @param result the list where collected words are added.
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
            return 26;
        } else {
            return c - 'a';
        }
    }
}