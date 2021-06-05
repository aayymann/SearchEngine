package api;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import database.WordModel;

@RestController
@RequestMapping(path = "/api/search")
public class SearchController {
    final WordModel wordModel = WordModel.getInstance();

    @GetMapping
    public Document search(@RequestParam("q") String searchString) {
        return wordModel.findOne(searchString);
    }
}
