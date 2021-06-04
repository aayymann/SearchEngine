package indexer;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    public static MongoDatabase database;
    public static void createDatabase() {

        //Remove intensive logging
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        //Connecting to Database with local host
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("index");
        //Drop the collection first because you can't create a new one with same name
        dropCollection("Words"); //TODO: Must Be Removed when code is Working properly its for test purposes only
        //Creating a collection This needs to Run Once only
        database.createCollection("Words"); //TODO: Must Be Removed when code is Working properly its for test purposes only
    }

    public static void dropCollection(String CollectionName){
        //Getting a Collection
        //MongoCollection<Document> collection = database.getCollection("sampleCollection");
        database.getCollection(CollectionName).drop();
    }
    public static void insertDoc(Document word){
        database.getCollection("Words").insertOne(word);
    }

    public static void insertManyDocs(Document[] words){
        /*      Creating a document
        Document document1 = new Document("title", "newDB")
                .append("description", "database")
                .append("likes", 100)
                .append("url", "http://www.tutorialspoint.com/mongodb/")
                .append("by", "tutorials point");
        */
        List<Document> list = new ArrayList<Document>();
        for(int i =0;i<words.length;i++) {
            list.add(words[i]);
        }
        database.getCollection("Words").insertMany(list);
    }
    public static void printDatabase(){
        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("Words");
        // Getting the iterable object
        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            i++;
        }
    }
}
