package com.techelevator.dao;

import com.techelevator.model.Collection;
import com.techelevator.model.RecordDTO;
import com.techelevator.model.User;

import java.security.Principal;
import java.util.List;

public interface CollectionDao {
    public void saveCollection(RecordDTO record, Collection collection, Principal principal);

    public List<Collection> getCollections(User user);


}
