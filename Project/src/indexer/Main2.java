package indexer;
import crawler.ManipulateFile;
import java.io.File;
import java.util.ArrayList;

public class Main2 {
    public static void main (String[] args)  throws Exception {
        //--FetchedCrawlerHTML is an array of all the generated HTML
        ArrayList<String> fetchedCrawlerHTML;
        fetchedCrawlerHTML=Link.FromCrawlerToIndexer();
        //--String of extracted HTML without tags
        ArrayList<String> strippedHTMLArr;
        strippedHTMLArr=Link.StripHTMLTags(fetchedCrawlerHTML);
        System.out.println(strippedHTMLArr.get(35));
    }
}
