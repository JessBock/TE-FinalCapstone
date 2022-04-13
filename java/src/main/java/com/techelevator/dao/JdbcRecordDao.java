package com.techelevator.dao;

import com.techelevator.model.*;
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
        RecordDTO record = new RecordDTO();
        List<RecordDTO> records = new ArrayList<>();
        String sql = "SELECT DISTINCT records.title, records.year, records.image " +
                "FROM records " +
                "JOIN users_records ON users_records.records_id = records.records_id " +
                "JOIN artists ON records.records_id = artists.records_id " +
                "WHERE user_id = ?;";

       SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user.getId());
       while(results.next()) {
            record = mapRowToGetLibrary(results);
            String getGenres = "SELECT DISTINCT genres.name FROM genres " +
                    "JOIN records_genres ON genres.genres_id = records_genres.genres_id " +
                    "JOIN records ON records_genres.records_id = records.records_id " +
                    "JOIN artists ON records.records_id = artists.records_id " +
                    "WHERE records.title = ?;";
            SqlRowSet genres = jdbcTemplate.queryForRowSet(getGenres, record.getTitle());
            while(genres.next()) {
                record.getGenre().add(genres.getString("name"));
            }

            String getArtists = "SELECT artists.name " +
                    "FROM artists " +
                    "JOIN records ON artists.records_id = records.records_id " +
                    "WHERE records.title = ?;";
            SqlRowSet artists = jdbcTemplate.queryForRowSet(getArtists, record.getTitle());
            while (artists.next()) {
                    Artists newArtist = new Artists();
                    newArtist.setName(artists.getString("name"));
                    record.getArtists().add(newArtist);
            }

            String getTracks = "SELECT * FROM tracks " +
                    "JOIN records ON tracks.records_id = records.records_id " +
                    "WHERE records.title = ?;";
            SqlRowSet tracks = jdbcTemplate.queryForRowSet(getTracks, record.getTitle());
            while (tracks.next()) {
                Tracks newTrack = new Tracks();
                newTrack = mapRowToTrack(tracks);
                record.getTracklist().add(newTrack);
            }
            records.add(record);
       }
       return records;
    }


    private RecordDTO mapRowToGetLibrary(SqlRowSet rowSet) {
        RecordDTO record = new RecordDTO();
        record.setTitle(rowSet.getString("title"));
        record.setYear(rowSet.getString("year"));
        record.setCoverImg(rowSet.getString("image"));
        return record;
    }

    private Tracks mapRowToTrack(SqlRowSet rowSet) {
        Tracks newTrack = new Tracks();
        newTrack.setTitle(rowSet.getString("name"));
        newTrack.setDuration(rowSet.getString("duration"));
        newTrack.setPosition(rowSet.getString("position"));
        return newTrack;
    }

}
