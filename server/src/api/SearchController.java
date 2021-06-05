package api;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.FindIterable;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import database.WordModel;
import indexer.Stemmer;

@RestController
@RequestMapping(path = "/api/search")
public class SearchController {
    final WordModel wordModel = WordModel.getInstance();
    final Stemmer stemmer = new Stemmer();

    @GetMapping
    public List<Object> search(@RequestParam("q") String searchString) {
        String[] stemmedWords = stemmer.stemSentence(searchString);
        FindIterable<Document> iterDoc = wordModel.findInMany(stemmedWords);

        List<Object> docs = new ArrayList<Object>();
        for (final Document doc : iterDoc) {
            doc.remove("_id");
            docs.add(doc);
        }

        return docs;
    }
}
