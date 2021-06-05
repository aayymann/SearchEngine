package indexer;

import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.WordModel;

public class Main {

    static final String databaseConnUrl = "mongodb://localhost:27017";

    public static void main(String[] args) throws Exception, Throwable {
        ArrayList<String> fetchedCrawlerHTML = Link.FromCrawlerToIndexer();
        ArrayList<String> fetchedHyperlinksArr = Link.GetHyperLinksArr();
        ArrayList<String> strippedHTMLArr = Link.StripHTMLTags(fetchedCrawlerHTML);

        Database.connect(databaseConnUrl);
        WordModel wordModel = WordModel.getInstance();

        Tokenizer tokenizer = new Tokenizer();
        //
        int size = fetchedCrawlerHTML.size();
        for (int i = 0; i < size; i++) { // TODO: Edit for loop length
            String documentContent = strippedHTMLArr.get(i);
            List<Token> tokens = tokenizer.getTokens(documentContent);

            for (Token token : tokens) {
                wordModel.insertOne(token.getText(), fetchedHyperlinksArr.get(i), token.getFrequency(),
                        token.getTextBlock());
            }
        }
    }
}
