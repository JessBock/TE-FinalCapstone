package com.techelevator.dao;

import com.techelevator.model.RecordDTO;
import com.techelevator.model.SearchResult;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRecordDao implements RecordDao {
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcRecordDao(JdbcTemplate jdbcTemplate, UserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public void saveToLibrary(RecordDTO record, Principal principal) {
        long userId = userDao.findIdByUsername(principal.getName());

        String addToRecord = "INSERT INTO records(title, year, image) VALUES(?, ?, ?) RETURNING records_id";
        Long recordId = jdbcTemplate.queryForObject(addToRecord, Long.class, record.getTitle(), record.getYear(),
                 record.getCoverImg());

        String addToLibrary = "INSERT INTO users_records(user_id, records_id) VALUES (?,?)";
        jdbcTemplate.update(addToLibrary, userId, recordId);

        String addToArtist = "INSERT INTO artists(name, image, records_id) VALUES (?, ?, ?) RETURNING artists_id";
        for(int i = 0; i < record.getArtists().size(); i ++) {
            Long artistId = jdbcTemplate.queryForObject(addToArtist, Long.class, record.getArtists().get(i).getName(),
                    record.getCoverImg(), recordId);

            String updateRecordTable = "UPDATE records SET artists_id = ? WHERE records_id = ?";
            jdbcTemplate.update(updateRecordTable, artistId, recordId);
        }
        String addToGenre = "INSERT INTO genres(name) VALUES (?) RETURNING genres_id";
        for(int i = 0; i < record.getGenre().size(); i ++) {
            Long genreId = jdbcTemplate.queryForObject(addToGenre, Long.class, record.getGenre().get(i));

            String updateRecordTable = "UPDATE records SET genres_id = ? WHERE records_id = ?";
            jdbcTemplate.update(updateRecordTable, genreId, recordId);

            String updateRecordsGenres = "INSERT INTO records_genres(genres_id, records_id) VALUES(?,?)";
            jdbcTemplate.update(updateRecordsGenres, genreId, recordId);
        }
        String addToTracks = "INSERT INTO tracks(name, records_id, duration, position) VALUES (?, ?, ?, ?)";
        for(int i = 0; i < record.getTracklist().size(); i ++) {
            jdbcTemplate.update(addToTracks, record.getTracklist().get(i).getTitle(), recordId,
                    record.getTracklist().get(i).getDuration(), record.getTracklist().get(i).getPosition());
        }
    }

    @Override
    public List<RecordDTO> getLibrary(User user) {
        List<RecordDTO> records = new ArrayList<>();
        String sql = "SELECT * FROM records " +
                "JOIN users_records ON users_records.records_id = records.records_id " +
                "WHERE user_id = ?;";

       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, RecordDTO.class);
       while(results.next()) {
           return records;
       }
       return records;
    }


    private void mapRowToRecordDTO(SqlRowSet rowSet) {
        RecordDTO record = new RecordDTO();
        record.getArtists().get(0).setName("name");
    }


}
