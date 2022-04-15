package com.techelevator.controller;

import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Collection;
import com.techelevator.model.RecordDTO;
import com.techelevator.model.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class CollectionController {
    private final CollectionDao collectionDao;
    private final UserDao userDao;

    public CollectionController(CollectionDao collectionDao, UserDao userDao) {
        this.collectionDao = collectionDao;
        this.userDao = userDao;
    }

    @RequestMapping(path= "/collections/{collectionName}", method = RequestMethod.POST)
    public void saveCollection(@PathVariable String collectionName, Principal principal) {
        collectionDao.addCollection(collectionName, principal);
    }

    @RequestMapping(path= "/collections", method = RequestMethod.GET)
    public List<Collection> getCollection(Principal principal) {
        User user = userDao.findByUsername(principal.getName());
        List<Collection> collections = collectionDao.getCollections(user);
        return collections;
    }

<<<<<<< Updated upstream
    @RequestMapping(path= "/collections/{collectionId}", method = RequestMethod.DELETE)
    public void deleteCollection(@PathVariable Long collectionId) {
        collectionDao.deleteCollection(collectionId);
    }
=======
    @RequestMapping(path = "/collections/{collectionId}", method= RequestMethod.GET)
    public List<RecordDTO> getCollectionById(@PathVariable long collectionId) {
        List<RecordDTO> recordsInCollection = collectionDao.getRecordsByCollectionId(collectionId);
        return recordsInCollection;

    }

>>>>>>> Stashed changes


}
