package nl.teunwillems.sort;

import java.text.Normalizer;
import java.util.*;

/**
 * Created by Teun on 22-2-2016.
 */
public class Parser {

    public static final String delimiterRegex = "[^a-zA-Z0-9-]+";

    private String[] input;
    private String originalInput;

    public String[] getWords(String input) {
        if (input.equals(originalInput)) return this.input;
        originalInput = input;
        input = input.toLowerCase();
        return (this.input = input.split(delimiterRegex));
    }

    public LinkedHashSet<String> getUniqueWords(String input) {
        String[] words = getWords(input);

        // Create arraylist
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
        // Sort arraylist
        wordList.sort((o1, o2) -> o1.compareTo(o2) * -1);
        // Linked hashset can only contain unique values
        return new LinkedHashSet<>(wordList);
    }

    public Map<String, Set<Integer>> getConcordantie(String input){
        TreeMap<String,Set<Integer>> concordanatie = new TreeMap<>();
        int lineCounter = 0;
        for (String line : input.split("\n")){
            lineCounter ++;
            for (String word : line.split("[ ,. \\n]+")) {

                String normalizedString = stringNormalizer(word);

                if (word.isEmpty()){
                    continue;
                }
                if (!concordanatie.containsKey(normalizedString)) {
                    concordanatie.put(normalizedString, new HashSet<>());
                }

                Set<Integer> set = concordanatie.get(normalizedString);
                set.add(lineCounter);
            }
        }
        return concordanatie;
    }

    private String stringNormalizer(String input){
        String normalizedWord = input;

//		http://stackoverflow.com/a/8523728
        normalizedWord = Normalizer.normalize(normalizedWord, Normalizer.Form.NFD);
        normalizedWord = normalizedWord.replaceAll("[^\\p{ASCII}]", "");

        normalizedWord = normalizedWord.toLowerCase();
        return normalizedWord;
    }

}
