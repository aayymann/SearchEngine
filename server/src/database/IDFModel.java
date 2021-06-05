package database;

import org.bson.Document;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.*;

public class IDFModel {
    private static IDFModel singletonInstance = null;

    final String name = "IDFs";
    MongoCollection<Document> collection;

    private IDFModel() {
        createCollection();
    }

    public static IDFModel getInstance() {
        if (singletonInstance == null)
            singletonInstance = new IDFModel();

        return singletonInstance;
    }

    private void createCollection() {
        collection = Database.getCollection(name);
        Document index = new Document("word", 1).append("IDF", 1);
        collection.createIndex(index);
    }

    public void insertOne(Document doc) {
        collection.insertOne(doc);
    }

    public Document findOne(String word) {
        return collection.find(eq("word", word)).first();
    }

    public FindIterable<Document> find(String word) {
        return collection.find(eq("word", word)).sort(new Document("IDF", -1));
    }

    public FindIterable<Document> findInMany(String[] words) {
        return collection.find(in("word", words)).sort(new Document("IDF", -1));
    }


}
