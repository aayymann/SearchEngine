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

    public List<Token> getTokens(String str) {
        String[] words = str.split(" ");
        String stemmedWord;

        HashMap<String, Token> tokensMap = new HashMap<String, Token>();
        Token token;

        for (int i = 0; i < words.length; i++) {
            if (isStopWord(words[i]))
                continue;

            if (isEmptyString(words[i]))
                continue;

            if (isSingleCharacter(words[i]))
                continue;

            stemmedWord = stemmer.stem(words[i]);

            if (tokensMap.containsKey(stemmedWord)) {
                token = tokensMap.get(stemmedWord);
                token.incrementFrequency();
                continue;
            }

            token = new Token(stemmedWord);
            token.setTextBlock(getTextBlock(words, i));
            tokensMap.put(stemmedWord, token);
        }

        List<Token> wordsList = new ArrayList<Token>(tokensMap.values());
        return wordsList;
    }

}
