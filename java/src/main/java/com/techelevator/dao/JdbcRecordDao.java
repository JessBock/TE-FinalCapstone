package com.techelevator.dao;

import com.techelevator.model.SearchResult;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class JdbcRecordDao implements RecordDao {
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcRecordDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public void saveToLibrary(SearchResult record, Principal principal) {
        long userId = userDao.findIdByUsername(principal.getName());

        /*
        May need to update Table Names!!!
         */
        String addRecord = "INSERT INTO records(title, year, genre, image) VALUES(?, ?, ?, ?)"


        String addToLibrary = "INSERT INTO users_records"

    }
}
