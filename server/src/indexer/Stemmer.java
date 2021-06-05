package indexer;

import org.tartarus.snowball.ext.EnglishStemmer;

public class Stemmer {

    private EnglishStemmer stemmer;

    Stemmer() {
        stemmer = new EnglishStemmer();
    }

    public String stem(String[] words) {
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            stemmer.setCurrent(word);
            stemmer.stem();
            result.append(stemmer.getCurrent()).append(" ");
        }

        result = new StringBuilder(result.substring(0, result.length() - 1));
        return result.toString();
    }

    public String stem(String word) {
        stemmer.setCurrent(word);
        stemmer.stem();
        return stemmer.getCurrent();
    }
}
