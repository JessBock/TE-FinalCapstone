package com.techelevator.controller;

import com.techelevator.dao.RecordDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
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
    public RecordDTO getRecordByIdFromDiscogs(@PathVariable String id) {
        RecordDTO record = discogs.getRecords(id);
        return record;
    }

    @RequestMapping(path= "/library", method= RequestMethod.POST)
    public void saveRecord(@RequestBody RecordDTO record, Principal principal) {
        recordDao.saveToLibrary(record, principal);
    }

    @RequestMapping(path = "/library/{recordId}", method = RequestMethod.DELETE)
    public void deleteRecord(@PathVariable Long recordId) {
        recordDao.deleteFromLibrary(recordId);
    }

    @RequestMapping(path = "/library", method = RequestMethod.GET)
    public List<RecordDTO> getLibrary(Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        List<RecordDTO> library = recordDao.getLibrary(user);
        return library;
    }

    @RequestMapping(path = "/library/addComments", method = RequestMethod.PUT)
    public void addCommentsAndCondition(@RequestBody RecordDTO recordDTO) {
        recordDao.setCommentsAndCondition(recordDTO.getRecordId(), recordDTO.getComments(), recordDTO.getCondition());
    }

    @RequestMapping(path = "/library/genreStats", method = RequestMethod.GET)
    public List<Stat> getGenreStats(Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        List<Stat> genreStats = recordDao.getGenreStats(user.getId());
        return genreStats;
    }

    @RequestMapping(path = "/library/artistStats", method = RequestMethod.GET)
    public List<Stat> getArtistStats(Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        List<Stat> artistStats = recordDao.getArtistStats(user.getId());
        return artistStats;
    }

    @RequestMapping(path = "/library/count", method = RequestMethod.GET)
    public int getLibraryCount(Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        return recordDao.getUserRecordCount(user.getId());
    }

    @RequestMapping(path = "/database/genreStats", method = RequestMethod.GET)
    public List<Stat> getDatabaseGenreStats() {
        List<Stat> genreStats = recordDao.getDatabaseGenreStats();
        return genreStats;
    }

    @RequestMapping(path = "/database/artistStats", method = RequestMethod.GET)
    public List<Stat> getDatabaseArtistStats() {
        List<Stat> artistStats = recordDao.getDatabaseArtistStats();
        return artistStats;
    }

    @RequestMapping(path = "/database/count", method = RequestMethod.GET)
    public int getDatabaseRecordCount() {
        return recordDao.getDatabaseRecordCount();
    }

}
