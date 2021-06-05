package indexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tokenizer {
    private Set<String> stopWords = new HashSet<String>();
    private Stemmer stemmer = new Stemmer();

    Tokenizer() {
        initializeStopWords();
    }

    private void initializeStopWords() {
        Collections.addAll(stopWords, "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "you're",
                "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it",
                "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom",
                "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have",
                "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or",
                "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between",
                "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in",
                "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when",
                "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "much",
                "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "can", "will", "just", "don",
                "should", "now");
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public boolean isSingleCharacter(String word) {
        return word.length() <= 1;
    }

    public boolean isEmptyString(String word) {
        return word.trim().isEmpty();
    }

    public String getTextBlock(String[] words, int index) {
        final int range = 10;

        int begin = Math.max(0, index - range);
        int end = Math.min(words.length, index + range);

        StringBuilder result = new StringBuilder();

        for (int i = begin; i < end; i++)
            result.append(words[i]).append(" ");

        return result.toString();
    }

    public List<Word> getTokens(String str) {
        String[] tokens = str.split(" ");
        String stemmedToken;

        HashMap<String, Word> wordsMap = new HashMap<String, Word>();
        Word word;

        for (int i = 0; i < tokens.length; i++) {
            if (isStopWord(tokens[i]))
                continue;

            if (isEmptyString(tokens[i]))
                continue;

            if (isSingleCharacter(tokens[i]))
                continue;

            stemmedToken = stemmer.stem(tokens[i]);

            if (wordsMap.containsKey(stemmedToken)) {
                word = wordsMap.get(stemmedToken);
                word.incrementFrequency();
                continue;
            }

            word = new Word(stemmedToken);
            word.setTextBlock(getTextBlock(tokens, i));
            wordsMap.put(stemmedToken, word);
        }

        List<Word> wordsList = new ArrayList<Word>(wordsMap.values());
        return wordsList;
    }

}
