package com.techelevator.dao;

import com.techelevator.model.SearchResult;

import java.security.Principal;

public interface RecordDao {
    public void saveToLibrary(SearchResult record, Principal principal);
}
