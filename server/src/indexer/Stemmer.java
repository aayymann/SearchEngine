package indexer;

import org.tartarus.snowball.ext.EnglishStemmer;

public class Stemmer {

    public static String stem(String[] words) {
        EnglishStemmer stemmer = new EnglishStemmer();
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            stemmer.setCurrent(word);
            stemmer.stem();
            result.append(stemmer.getCurrent()).append(" ");
        }

        result = new StringBuilder(result.substring(0, result.length() - 1));
        return result.toString();
    }
}
