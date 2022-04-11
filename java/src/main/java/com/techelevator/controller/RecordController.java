package com.techelevator.controller;

import com.techelevator.model.SearchResponse;
import com.techelevator.services.DiscogsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RecordController {
    private DiscogsService discogs;

    public RecordController(DiscogsService discogs) {
        this.discogs = discogs;
    }


    @RequestMapping(value = "/search/{expression}", method = RequestMethod.GET)
    public SearchResponse search(@PathVariable String expression) {
        return discogs.search(expression);
    }

}
