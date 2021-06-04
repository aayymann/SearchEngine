package indexer;

import crawler.ManipulateFile;
import org.jsoup.Jsoup;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class Link {
    private static ArrayList<String> fetchedHyperlinksArr = new ArrayList<String>();
    static ArrayList<String>  FromCrawlerToIndexer(){
        int numOfCrawlers= ManipulateFile.ReadNumberFromFile("./src/crawler/numOfCrawlers.txt");
        String [] crawlersArr = new String [numOfCrawlers];
        int []fileCountArr =  new int [numOfCrawlers];
        for(int i=0 ; i<numOfCrawlers ; i++){
            crawlersArr[i]=String.valueOf(i);
        }
        //--Count how many files in a folder
        String[] pathToCrawlerHTML= new String [numOfCrawlers];
        String[] pathToCrawlerHyperlink= new String [numOfCrawlers];
        for(int i=0 ; i< numOfCrawlers ; i++){
            //--FOR THE HTML && HYPERLINKS
            pathToCrawlerHTML[i]="./websites/Crawler"+crawlersArr[i];
            pathToCrawlerHyperlink[i]="./hyperlinks/Crawler"+crawlersArr[i];
            //System.out.println(pathToCrawlerHyperlink[i]);
            File directory=new File(pathToCrawlerHTML[i]);
            fileCountArr[i]=directory.list().length;
            pathToCrawlerHTML[i]+="/";
            pathToCrawlerHyperlink[i]+="/";

        }
        //
        ArrayList<String> fetchedCrawlerHTML = new ArrayList<String>();
        fetchedCrawlerHTML=Files.FetchingFromCrawlerFiles(pathToCrawlerHTML,crawlersArr,numOfCrawlers,fileCountArr);
        fetchedHyperlinksArr=Files.FetchingFromCrawlerFiles(pathToCrawlerHyperlink,crawlersArr,numOfCrawlers,fileCountArr);
        return fetchedCrawlerHTML;
    }

    static ArrayList<String> GetHyperLinksArr (){
        return fetchedHyperlinksArr;
    }
    static ArrayList<String> StripHTMLTags(ArrayList<String> fetchedCrawlerHTML){
        ArrayList<String> stirppedHTML = new ArrayList<String>();
        int size = fetchedCrawlerHTML.size();
        for(int i=0 ; i< size ; i++){
            String temp = Jsoup.parse(fetchedCrawlerHTML.get(i)).text();
            temp=temp.toLowerCase();
            //--Remove hyperlinks
            temp = temp.replaceAll("((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)","");
            stirppedHTML.add(temp);
        }
        return stirppedHTML;
    }

    static ArrayList<String> doFurtherProcessing(ArrayList<String> stirppedHTML){
        ArrayList<String> stirppedHTML2 = new ArrayList<String>();
        int size = stirppedHTML.size();
        for(int i=0 ; i< size ; i++){
            String temp=stirppedHTML.get(i);
            //--Remove special charachters
            temp=temp.replaceAll("-"," ");
            temp= temp.replaceAll("[-®#%~!@#$%^&*()_+/*?<>':;–.,`’\"]*","");
            //--Replace any single char with nothing
            temp=temp.replaceAll(" [a-zA-Z0-9] "," ");
            //--Replace stopping words with nothing
            String temp2 =temp;
            temp=temp.replaceAll(" (me|my|myself|we|our|ours|ourselves|you|your|you\'re|yours|yourself|yourselves|he|him|his|himself|she|her|hers|hereself|it|its|itself|they|them|their|theirs|themselves|what|which|who|whom|this|that|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|doing|an|the|and|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|before|after|above|below|to|from|up|down|in|out|on|off|over|under|agian|further|then|once|here|there|when|where|why|how|all|any|both|each|few|more|most|other|some|much|no|nor|not|only|own|same|so|than|too|very|can|will|just|don|should|now) "," ");
            while(!(temp.equals(temp2))){
                temp2=temp;
                temp=temp.replaceAll(" (me|my|myself|we|our|ours|ourselves|you|your|you\'re|yours|yourself|yourselves|he|him|his|himself|she|her|hers|hereself|it|its|itself|they|them|their|theirs|themselves|what|which|who|whom|this|that|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|doing|an|the|and|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|before|after|above|below|to|from|up|down|in|out|on|off|over|under|agian|further|then|once|here|there|when|where|why|how|all|any|both|each|few|more|most|other|some|much|no|nor|not|only|own|same|so|than|too|very|can|will|just|don|should|now) "," ");
            }
            //--Replace 2 or more white spaces with a single white space
            temp=temp.replaceAll("\\s{2,}"," ");
            stirppedHTML2.add(temp);
        }
        return stirppedHTML2;
    }
}
