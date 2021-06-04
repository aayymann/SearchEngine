package indexer;
import crawler.ManipulateFile;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.ArrayList;

public class Main2 {
    public static void main (String[] args)  throws Exception, Throwable {
        //--FetchedCrawlerHTML is an array of all the generated HTML
        ArrayList<String> fetchedCrawlerHTML;
        ArrayList<String> fetchedHyperlinksArr = new ArrayList<String>();
        fetchedCrawlerHTML=Link.FromCrawlerToIndexer();
        fetchedHyperlinksArr=Link.GetHyperLinksArr();

        //--String of extracted HTML without tags
        ArrayList<String> strippedHTMLArr;
        strippedHTMLArr=Link.StripHTMLTags(fetchedCrawlerHTML);
        int size  = strippedHTMLArr.size();

        //--CREATE DATABASE CONNECTION
        Database.createDatabase();

        for(int c= 0 ; c<size ; c++){
            String Result = Stemmer.Stemming(strippedHTMLArr.get(c));
            String[] test = Result.split(" ");
            Document[] docs = new Document[test.length];
            for(int i = 0;i<test.length;i++){
                docs[i] = new Document("word",test[i]);
            }
            Database.insertManyDocs(docs);
        }
        Database.printDatabase();
    }
}
