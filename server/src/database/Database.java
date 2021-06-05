package database;

import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public static MongoDatabase database;

    public static void connect(String uri) {
        configureLogging();
        MongoClient mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase("index");
    }

    public static void configureLogging() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
    }

    public static void createCollection(String name) {
        database.createCollection(name);
    }

    public static MongoCollection<Document> getCollection(String name) {
        return database.getCollection(name);
    }

    public static void dropCollection(String name) {
        database.getCollection(name).drop();
    }

    public static void insertDoc(String collection, Document doc) {
        database.getCollection(collection).insertOne(doc);
    }

    public static void insertManyDocs(String collection, Document[] docs) {
        List<Document> list = new ArrayList<Document>();
        Collections.addAll(list, docs);
        database.getCollection(collection).insertMany(list);
    }

    public static void printCollection(String name) {
        MongoCollection<Document> collection = database.getCollection(name);
        FindIterable<Document> iterDoc = collection.find();

        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}