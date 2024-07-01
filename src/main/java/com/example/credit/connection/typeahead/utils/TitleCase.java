package com.example.credit.connection.typeahead.utils;

/** TitleCase */
public class TitleCase {

    public static String titleString(String q) {
        String[] words = q.split("\\s+");
        StringBuilder res = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                res.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return res.toString().trim();
    }
}
