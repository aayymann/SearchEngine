package indexer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception, Throwable {
        ArrayList<String> fetchedCrawlerHTML = Link.FromCrawlerToIndexer();
        ArrayList<String> fetchedHyperlinksArr = Link.GetHyperLinksArr();
        ArrayList<String> strippedHTMLArr = Link.StripHTMLTags(fetchedCrawlerHTML);

        Tokenizer tokenizer = new Tokenizer();

        for (int c = 0; c < 1; c++) {
            String str = strippedHTMLArr.get(c);
            List<Word> words = tokenizer.getTokens(str);

            for (Word word : words) {
                System.out.println(
                        "word: " + word.getText() + " block: " + word.getTextBlock() + "  tf:" + word.getFrequency());
            }
        }
    }
}
