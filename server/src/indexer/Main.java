package indexer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import database.Database;
import database.IDFModel;
import database.WordModel;
import com.mongodb.client.*;
import org.bson.Document;

import javax.print.Doc;

public class Main {

    static final String databaseConnUrl = "mongodb://localhost:27017";
    public static void main(String[] args) throws Exception, Throwable {

        //--IF THE CRAWLER DID NOT CRAWL AGAIN AFTER THE INDEXER DO NOT DO INDEXER FUNCTIONALITY AS IT WOULD BE REDUNDANT
        try {
            int indicator = Integer.parseInt(Files.ReadFile("./out/indicator.txt"));
            if (indicator == 234) {
                indicator = 432;
                Files.WriteNumberInFile("./out/indicator.txt", indicator);
                try {
                    ArrayList<String> fetchedCrawlerHTML = Link.FromCrawlerToIndexer();
                    ArrayList<String> fetchedHyperlinksArr = Link.GetHyperLinksArr();
                    ArrayList<String> strippedHTMLArr = Link.StripHTMLTags(fetchedCrawlerHTML);
                    ArrayList<String> titles = Link.extractHTMLTitles(fetchedHyperlinksArr);

                    Database.connect(databaseConnUrl);
                    WordModel wordModel = WordModel.getInstance();


                    Tokenizer tokenizer = new Tokenizer();
                    //
                    int size = fetchedCrawlerHTML.size();
                    for (int i = 0; i < size; i++) {
                        String documentContent = strippedHTMLArr.get(i);
                        List<Token> tokens = tokenizer.getTokens(documentContent);
                        for (Token token : tokens) {
                            wordModel.insertOne(token.getText(), fetchedHyperlinksArr.get(i), token.getFrequency(),
                                    token.getTextBlock(), titles.get(i));
                        }
                    }

                    IDFModel idfModel = IDFModel.getInstance();
                    int totalNumberOfDocs = wordModel.getNumDocuments();
                    AggregateIterable<Document> iterDoc = wordModel.getNumDocumentsPerWord();

                    for (final Document doc : iterDoc) {
                        String word = (String)doc.get("_id");
                        Integer numDocsPerWord = (Integer)doc.get("numDocuments");
                        float wordIDF = (float) Math.log(totalNumberOfDocs/(float)numDocsPerWord);
                        Document newDoc = new Document().append("word",word).append("IDF", wordIDF);
                        idfModel.insertOne(newDoc);
                    }
                } catch (Exception ex) {
                    System.out.println("THE CRAWLER DID NOT GENERATE FILES");
                }
            }
        } catch (Exception ex2) {
            System.out.println("RUN THE CRAWLER FIRST FOR SOME DATA FETCHING");
        }
    }
}
