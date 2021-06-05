package database;

import org.bson.Document;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.*;

import java.util.Arrays;

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

    public void insertOne(String word, String url, int tf, String paragraph, String title) {
        Document document = new Document().append("word", word).append("url", url).append("tf", tf)
                .append("paragraph", paragraph).append("title", title);

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

    public AggregateIterable<Document> getNumDocumentsPerWord() {
        Document groupQuery = new Document().append("_id", "$word").append("numDocuments",
                new Document().append("$sum", 1));

        Document groupStage = new Document("$group", groupQuery);
        return collection.aggregate(Arrays.asList(groupStage));
    }

    public int getNumDocuments() {
        Document groupQuery = new Document().append("_id", "$url");
        Document groupStage = new Document("$group", groupQuery);

        Document countQuery = new Document().append("_id", "null").append("count", new Document().append("$sum", 1));
        Document countStage = new Document("$group", countQuery);

        AggregateIterable<Document> itr = collection.aggregate(Arrays.asList(groupStage, countStage));

        int count = (int) itr.first().get("count");
        return count;
    }
}