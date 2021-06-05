package api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/search")
public class SearchController {

    @GetMapping
    public List<Object> search(@RequestParam("q") String searchString) {
        return QueryService.search(searchString);
    }
}
