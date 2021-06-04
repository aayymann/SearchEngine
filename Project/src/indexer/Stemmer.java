package indexer;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class Stemmer {
    //Call this function Anywhere pass a String with the Content and it will return the same string with all the words Stemmed
    /*
    Needed files:
    Among
    SnowballProgram
    SnowballStemmer
    ext Folder
     */
    public static String Stemming(String content) throws Throwable {
        Class stemClass = Class.forName("indexer.ext.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
        String[] Splitted = content.split(" ");
        StringBuilder result = new StringBuilder();
        for (String s : Splitted) {
            stemmer.setCurrent(s);
            stemmer.stem();
            result.append(stemmer.getCurrent()).append(" ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 1));
        return result.toString();
    }
    public static void main(String[] args) throws Throwable {
        String content = "youtube sign help build better youtube creators creators like made youtube today want help shape future signing user research studies sharing thoughts youll help us build better youtube audience world sign heres get involved 01 sign give back youtube community participating feedback sessions click sign link questionnaire arrive inbox information share better well able match projects benefit opinions expertise 02 take part opportunities come invite participate feedback sessions take place youtubegoogle offices online wherever 03 let us say thanks feedback matters complete study say thanks things like gift cards keep donations favorite charity connect youtube blog youtube works jobs press youtube culture trends products youtube go youtube kids youtube music youtube originals youtube premium youtube select youtube studio youtube tv business developers youtube advertising creators creating youtube kids creator academy creator research creator services directory youtube artists youtube creators youtube nextup youtube space youtube vr commitments creators change csai match social impact youtube products business creators commitments blog youtube works jobs press youtube culture trends youtube go youtube kids youtube music youtube originals youtube premium youtube select youtube studio youtube tv developers youtube advertising creating youtube kids creator academy creator research creator services directory youtube artists youtube creators youtube nextup youtube space youtube vr creators change csai match social impact policies safety copyright brand guidelines privacy terms help update information opt out";
        String Result = Stemmer.Stemming(content);
        System.out.println(Result);
        String[] test = Result.split(" ");
        Document[] docs = new Document[test.length];
        for(int i = 0;i<test.length;i++){
            docs[i] = new Document("word",test[i]);
        }
        Database.createDatabase();
        Database.insertManyDocs(docs);
        Database.printDatabase();
    }
}
