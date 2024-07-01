package com.example.credit.connection.typeahead.utils;

/** TitleCase */
public class TitleCase {

    /**
     * Convert String to titlecase use StringBuilder
     *
     * @param q Input string
     * @return Titlecased String
     */
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
