package com.techelevator.dao;

import com.techelevator.model.RecordDTO;
import com.techelevator.model.SearchResult;
import com.techelevator.model.Stat;
import com.techelevator.model.User;

import java.security.Principal;
import java.util.List;

public interface RecordDao {
    public void saveToLibrary(RecordDTO record, Principal principal);

    public void deleteFromLibrary(Long recordId);

    public List<RecordDTO> getLibrary(User user);

    public void setCommentsAndCondition(long recordId, String comments, String condition);

    public List<Stat> getGenreStats(Long userId);

    public List<Stat> getArtistStats(Long userId);

}
