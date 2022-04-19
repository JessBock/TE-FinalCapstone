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
    public void setCommentsAndCondition(long recordId, String comments, String condition) {
        String sql = "UPDATE records " +
                "SET records_comments = ?, condition = ? " +
                "WHERE records_id = ?;";
        jdbcTemplate.update(sql, comments, condition, recordId);
    }

    @Override
    public void deleteFromLibrary(Long recordId) {
        String sql = "DELETE FROM users_records " +
                "WHERE records_id = ?; " +

                "DELETE FROM records_genres " +
                "WHERE records_id = ?; " +

                "DELETE FROM genres " +
                "WHERE genres_id = (SELECT records_genres.genres_id " +
                "FROM records_genres " +
                "JOIN records ON records_genres.records_id = records.records_id " +
                "WHERE records.records_id = ?); " +

                "DELETE FROM tracks " +
                "WHERE records_id = ?; " +

                "DELETE FROM artists " +
                "WHERE records_id = ?; " +

                "DELETE FROM collections_records " +
                "WHERE records_id = ?;" +

                "DELETE FROM records " +
                "WHERE records_id = ?;";
        jdbcTemplate.update(sql, recordId, recordId, recordId, recordId, recordId, recordId, recordId);
    }
    @Override
    public List<RecordDTO> getLibrary(User user) {
        RecordDTO record = new RecordDTO();
        List<RecordDTO> records = new ArrayList<>();
        String sql = "SELECT DISTINCT records.title, records.year, records.image, records.records_id, records_comments, condition " +
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

    @Override
    public List<Stat> getGenreStats(Long userId) {
        List<Stat> genreStats = new ArrayList<>();
        String genres = "SELECT DISTINCT genres.name " +
                "FROM records " +
                "JOIN records_genres on records.records_id = records_genres.records_id " +
                "JOIN genres on records_genres.genres_id = genres.genres_id " +
                "JOIN users_records on records.records_id = users_records.records_id " +
                "WHERE users_records.user_id = ?;";
        SqlRowSet genreSet = jdbcTemplate.queryForRowSet(genres, userId);
        try {
           while (genreSet.next()) {
               Stat genre = new Stat();
               String countString = "SELECT COUNT(records.records_id) " +
                       "FROM records " +
                       "JOIN records_genres on records.records_id = records_genres.records_id " +
                       "JOIN genres on records_genres.genres_id = genres.genres_id " +
                       "JOIN users_records on records.records_id = users_records.records_id " +
                       "WHERE genres.name = ? and users_records.user_id = ?;";
               String genreName = genreSet.getString("name");
               int count = jdbcTemplate.queryForObject(countString, int.class, genreName, userId);
               genre.setCount(count);
               genre.setName(genreName);
               genreStats.add(genre);
           }
        } catch (Exception exception) {
            System.out.println("Query returned Null");
        }
        return genreStats;
    }

    @Override
    public List<Stat> getArtistStats(Long userId) {
        List<Stat> artistStats = new ArrayList<>();
        String artists = "SELECT DISTINCT artists.name " +
                "FROM artists " +
                "JOIN records on artists.records_id = records.records_id " +
                "JOIN users_records on records.records_id = users_records.records_id " +
                "WHERE users_records.user_id = ?;";
        SqlRowSet artistSet = jdbcTemplate.queryForRowSet(artists, userId);
        try {
            while (artistSet.next()) {
                Stat artist = new Stat();
                String countString = "SELECT COUNT(records.records_id) " +
                        "FROM artists " +
                        "JOIN records on artists.records_id = records.records_id " +
                        "JOIN users_records on records.records_id = users_records.records_id " +
                        "WHERE artists.name = ? and users_records.user_id = ?;";
                String artistName = artistSet.getString("name");
                int count = jdbcTemplate.queryForObject(countString, int.class, artistName, userId);
                artist.setName(artistName);
                artist.setCount(count);
                artistStats.add(artist);
            }
        } catch (Exception exception) {
            System.out.println("Query returned Null");
        }
        return artistStats;
    }

    @Override
    public int getUserRecordCount(Long userId) {
        int count = 0;
        String sql = "SELECT COUNT(records.records_id) " +
                "FROM records " +
                "JOIN users_records on records.records_id = users_records.records_id " +
                "WHERE users_records.user_id = ?;";
        try {
             count = jdbcTemplate.queryForObject(sql, int.class, userId);
        } catch (Exception exception) {
            System.out.println("Query returned Null");
        }
        return count;
    }

    @Override
    public List<Stat> getDatabaseGenreStats() {
        List<Stat> genreStats = new ArrayList<>();
        String genres = "SELECT DISTINCT genres.name " +
                "FROM records " +
                "JOIN records_genres on records.records_id = records_genres.records_id " +
                "JOIN genres on records_genres.genres_id = genres.genres_id";
        SqlRowSet genreSet = jdbcTemplate.queryForRowSet(genres);
        try {
            while (genreSet.next()) {
                Stat genre = new Stat();
                String countString = "SELECT COUNT(records.records_id) " +
                        "FROM records " +
                        "JOIN records_genres on records.records_id = records_genres.records_id " +
                        "JOIN genres on records_genres.genres_id = genres.genres_id " +
                        "WHERE genres.name = ?;";
                String genreName = genreSet.getString("name");
                int count = jdbcTemplate.queryForObject(countString, int.class, genreName);
                genre.setCount(count);
                genre.setName(genreName);
                genreStats.add(genre);
            }
        } catch (Exception exception) {
            System.out.println("Query returned Null");
        }
        return genreStats;
    }

    @Override
    public List<Stat> getDatabaseArtistStats() {
        List<Stat> artistStats = new ArrayList<>();
        String artists = "SELECT DISTINCT artists.name " +
                "FROM artists " +
                "JOIN records on artists.records_id = records.records_id ";
        SqlRowSet artistSet = jdbcTemplate.queryForRowSet(artists);
        try {
            while (artistSet.next()) {
                Stat artist = new Stat();
                String countString = "SELECT COUNT(records.records_id) " +
                        "FROM artists " +
                        "JOIN records on artists.records_id = records.records_id " +
                        "WHERE artists.name = ?;";
                String artistName = artistSet.getString("name");
                int count = jdbcTemplate.queryForObject(countString, int.class, artistName);
                artist.setName(artistName);
                artist.setCount(count);
                artistStats.add(artist);
            }
        } catch (Exception exception) {
            System.out.println("Query returned Null");
        }
        return artistStats;
    }

    @Override
    public int getDatabaseRecordCount() {
        int count = 0;
        String sql = "SELECT COUNT(records.records_id) " +
                "FROM records;";
        try {
            count = jdbcTemplate.queryForObject(sql, int.class);
        } catch (Exception exception) {
            System.out.println("Query returned Null");
        }
        return count;
    }

    private RecordDTO mapRowToGetLibrary(SqlRowSet rowSet) {
        RecordDTO record = new RecordDTO();
        record.setTitle(rowSet.getString("title"));
        record.setYear(rowSet.getString("year"));
        record.setCoverImg(rowSet.getString("image"));
        record.setRecordId(rowSet.getLong("records_id"));
        record.setComments(rowSet.getString("records_comments"));
        record.setCondition(rowSet.getString("condition"));
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
