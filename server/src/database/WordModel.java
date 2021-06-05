package database;

import org.bson.Document;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.*;

public class WordModel {

    private static WordModel singletonInstance = null;

    final String name = "Words";
    MongoCollection<Document> collection;

    private WordModel() {
        createCollection();
    }

    public static WordModel getInstance() {
        if (singletonInstance == null)
            singletonInstance = new WordModel();

        return singletonInstance;
    }

    private void createCollection() {
        collection = Database.getCollection(name);
        Document index = new Document("word", 1).append("url", 1);
        collection.createIndex(index);
    }

    public void dropCollection() {
        Database.dropCollection(name);
    }

    public void insertOne(String word, String url, int tf, String paragraph) {
        Document document = new Document().append("word", word).append("url", url).append("tf", tf).append("paragraph",
                paragraph);

        collection.insertOne(document);
    }

    public Document findOne(String word) {
        return collection.find(eq("word", word)).first();
    }

    public FindIterable<Document> find(String word) {
        return collection.find(eq("word", word)).sort(new Document("tf", -1));
    }

    public FindIterable<Document> findInMany(String[] words) {
        return collection.find(in("word", words)).sort(new Document("tf", -1));
    }
}