package com.techelevator.dao;

import com.techelevator.model.RecordDTO;
import com.techelevator.model.SearchResult;
import com.techelevator.model.User;

import java.security.Principal;
import java.util.List;

public interface RecordDao {
    public void saveToLibrary(RecordDTO record, Principal principal);

    public List<RecordDTO> getLibrary(User user);

}
