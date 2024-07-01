package com.example.credit.utils.inputvalidation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * SearchInput Input validation util class
 */
public class InputVal {

    private static final String searchQRegex = ".*[0-9\\W_&&[^\\s]].*";
    /**
     * Checks if input contains digits or special character
     *
     * @param q Search query
     * @return true if query is invalid
     */
    public static boolean queryInvalid(String q) {
        Pattern pattern = Pattern.compile(searchQRegex);
        Matcher matcher = pattern.matcher(q);
        return matcher.matches();
    }
}
