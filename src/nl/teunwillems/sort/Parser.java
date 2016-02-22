package nl.teunwillems.sort;

import java.util.*;

/**
 * Created by Teun on 22-2-2016.
 */
public class Parser {

    public static final String delimiterRegex = "[^a-zA-Z0-9-]+";

    public static String[] getWords(String input) {
        input = input.toLowerCase();
        return input.split(delimiterRegex);
    }

    public static LinkedHashSet<String> getUniqueWords(String input) {
        String[] words = Parser.getWords(input);

        // Create arraylist
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
        // Sort arraylist
        wordList.sort((o1, o2) -> o1.compareTo(o2) * -1);

        return new LinkedHashSet<String>(wordList);
    }
}
