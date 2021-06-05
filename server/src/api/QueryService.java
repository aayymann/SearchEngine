package api;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.FindIterable;

import database.WordModel;
import indexer.Stemmer;

public class QueryService {
    static WordModel wordModel = WordModel.getInstance();
    static Stemmer stemmer = new Stemmer();

    public static List<Object> search(String searchString) {
        String[] stemmedWords = stemmer.stemSentence(searchString);
        FindIterable<Document> iterDoc = wordModel.findInMany(stemmedWords);

        List<Object> docs = new ArrayList<Object>();
        for (final Document doc : iterDoc) {
            doc.remove("_id");
            docs.add(doc);
        }

        return docs;
    }
}
