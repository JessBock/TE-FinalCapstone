package com.techelevator.controller;

import com.techelevator.model.RecordDTO;
import com.techelevator.model.SearchResponse;
import com.techelevator.model.SearchResult;
import com.techelevator.services.DiscogsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class RecordController {
    private DiscogsService discogs;

    public RecordController(DiscogsService discogs) {
        this.discogs = discogs;
    }


    @RequestMapping(value = "/search/{expression}", method = RequestMethod.GET)
    public SearchResponse search(@PathVariable String expression) {
        SearchResponse response = discogs.search(expression);
        return response;
    }



    @RequestMapping(value = "/releases/{id}", method = RequestMethod.GET)
    public RecordDTO getRecordByResourceURL(@PathVariable String id) {


        RecordDTO record = discogs.getRecords(id);
        return record;
    }

}
