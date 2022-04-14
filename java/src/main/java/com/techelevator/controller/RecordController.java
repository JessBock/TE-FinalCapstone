package com.techelevator.controller;

import com.techelevator.dao.RecordDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.RecordDTO;
import com.techelevator.model.SearchResponse;
import com.techelevator.model.SearchResult;
import com.techelevator.model.User;
import com.techelevator.services.DiscogsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class RecordController {
    private final DiscogsService discogs;
    private final RecordDao recordDao;
    private final UserDao userDao;

    public RecordController(DiscogsService discogs, RecordDao recordDao, UserDao userDao) {
        this.discogs = discogs;
        this.recordDao = recordDao;
        this.userDao = userDao;
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


    @RequestMapping(path= "/library", method= RequestMethod.POST)
    public void saveRecord(@RequestBody RecordDTO record, Principal principal) {
        recordDao.saveToLibrary(record, principal);
    }

    @RequestMapping(path = "/library", method = RequestMethod.GET)
    public List<RecordDTO> getLibrary(Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        List<RecordDTO> library = recordDao.getLibrary(user);
        return library;
    }

}
