package indexer;
import crawler.ManipulateFile;
import java.io.File;
import java.util.ArrayList;

public class Main2 {
    public static void main (String[] args)  throws Exception {
        //--FetchedCrawlerHTML is an array of all the generated HTML
        ArrayList<String> fetchedCrawlerHTML;
        ArrayList<String> fetchedHyperlinksArr = new ArrayList<String>();
        fetchedCrawlerHTML=Link.FromCrawlerToIndexer();
        fetchedHyperlinksArr=Link.GetHyperLinksArr();

        //--String of extracted HTML without tags
        ArrayList<String> strippedHTMLArr;
        strippedHTMLArr=Link.StripHTMLTags(fetchedCrawlerHTML);
        int i=0;
        System.out.println(strippedHTMLArr.get(i));
        System.out.println(fetchedHyperlinksArr.get(i));
    }
}
