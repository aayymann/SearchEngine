package indexer;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2 {
    public static void main (String[] args)  throws Exception, Throwable {
        //--FetchedCrawlerHTML is an array of all the generated HTML
        ArrayList<String> fetchedCrawlerHTML;
        ArrayList<String> fetchedHyperlinksArr = new ArrayList<String>();
        fetchedCrawlerHTML=Link.FromCrawlerToIndexer();
        fetchedHyperlinksArr=Link.GetHyperLinksArr();

        //--String of extracted HTML without tags
        ArrayList<String> strippedHTMLArr;
        ArrayList<String> processedArr;
        strippedHTMLArr=Link.StripHTMLTags(fetchedCrawlerHTML);
        //System.out.println(strippedHTMLArr.get(0));
        processedArr=Link.doFurtherProcessing(strippedHTMLArr);
        //System.out.println(processedArr.get(0));
        int size  = strippedHTMLArr.size();

        //--CREATE DATABASE CONNECTION
        Database.createDatabase();
        //--TODO ::REPLACE 1 WITH SIZE
        for(int c= 0 ; c<1 ; c++){
            String [] wordsArrForEachDoc= processedArr.get(c).split(" ");
            String [] wordsArrForEachDocWithStoppingWords= strippedHTMLArr.get(c).split(" ");
            int length = wordsArrForEachDoc.length;
            String [] descriptionForEachWord= new String [length];
            System.out.println("The length is " + length);
            for(int i=0 ; i< length ; i++){
                if(i>10 && i+10<length) {
                    String temp = "";
                    for(int l=i-10;l<10+i;l++) {
                        temp += wordsArrForEachDocWithStoppingWords[l];
                        if(l<19+l)
                            temp += " ";
                    }
                    descriptionForEachWord[i] = temp;
                }
                else if(i<=10){
                    String temp = "";
                    int y;
                    if (length>20)
                        y=20+i;
                    else
                        y=length;
                    for(int l=i;l<y;l++) {
                        temp += wordsArrForEachDocWithStoppingWords[l];
                        if(l<19+l)
                            temp += " ";
                    }
                    descriptionForEachWord[i] = temp;
                }
                else if(i+10>=length){
                    String temp = "";
                    int l2;
                    if(length>20)
                        l2=i-20;
                    else
                        l2=0;

                    for(int l=l2;l<i;l++) {
                        temp += wordsArrForEachDocWithStoppingWords[l];
                        if(l<19+l)
                            temp += " ";
                    }
                    descriptionForEachWord[i] = temp;
                }
                System.out.println( "word "+wordsArrForEachDoc[i] +" desc " +descriptionForEachWord[i]);
            }
            //
            String[] test = processedArr.get(c).split(" ");
            String Result = Stemmer.Stemming(test);
            System.out.println(Result);
            String wordsArr[] = Result.split(" ");
            Map<String, Integer> mapOfWords;
            mapOfWords=Link.GetMapOfKeyWords(wordsArr,descriptionForEachWord);
            ArrayList<String>newDescriptionForEachWord = Link.GetNewDescpOfEachWordArr();
            //
            System.out.println("The size of map is " + mapOfWords.size() + " and the new descpr size is " + newDescriptionForEachWord.size());
            int tempSehs = 0;
            for (String name: mapOfWords.keySet()) {
                String key = name;
                int value = mapOfWords.get(name);
                //System.out.println(key + " " + value +"    " + newDescriptionForEachWord.get(tempSehs));
                tempSehs++;
            }
            //
            Document[] docs = new Document[test.length];
            for(int i = 0;i<test.length;i++){
                docs[i] = new Document("word",test[i]); //TODO :: HUSSIEN WORD(key of mapOfWords), TF(value of mapOfWords) , HYPERLINK fetchedHyperlinksArr[c] , newDescriptionForEachWord[i]
            }
            Database.insertManyDocs(docs);
        }
        //Database.printDatabase();
    }
}
