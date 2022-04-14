package com.techelevator.controller;

import com.techelevator.dao.CollectionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Collection;
import com.techelevator.model.RecordDTO;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
public class CollectionController {
    private final CollectionDao collectionDao;
    private final UserDao userDao;

    public CollectionController(CollectionDao collectionDao, UserDao userDao) {
        this.collectionDao = collectionDao;
        this.userDao = userDao;
    }

    @RequestMapping(path= "/collections/add", method = RequestMethod.POST)
    public void saveCollection(@RequestBody Collection collection, RecordDTO record, Principal principal) {
        collectionDao.saveCollection(record, collection, principal);
    }



}
