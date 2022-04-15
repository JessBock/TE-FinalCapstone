package com.techelevator.dao;

import com.techelevator.model.Collection;
import com.techelevator.model.RecordDTO;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCollectionDao implements CollectionDao{
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private RecordDao recordDao;

    public JdbcCollectionDao(JdbcTemplate jdbcTemplate, UserDao userDao, RecordDao recordDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
        this.recordDao = recordDao;
    }


    @Override
    public void addCollection(String collectionName, Principal principal) {
        long userId = userDao.findIdByUsername(principal.getName());
        Collection collection = new Collection();

        String addingCollection = "INSERT INTO collections (collection_name, share, collection_comments, user_id) " +
                "VALUES (?, ?, ?, ?) RETURNING collections_id";

        Long collectionId = jdbcTemplate.queryForObject(addingCollection, Long.class, collectionName,
                collection.isShare(), collection.getCollectionComments(), userId);


    }

    @Override
    public List<Collection> getCollections(User user) {

        List<Collection> collections = new ArrayList<>();

<<<<<<< Updated upstream
        String getCollections = "SELECT collection_name, collections_id, share, collection_comments, user_id " +
=======
        String getCollections = "SELECT collection_name, collections_id " +
>>>>>>> Stashed changes
                "FROM collections " +
                "WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(getCollections, user.getId());

        while(results.next()) {
            Collection collection = new Collection();
            collection.setCollectionName(results.getString("collection_name"));
            collection.setCollectionId(results.getLong("collections_id"));
<<<<<<< Updated upstream
            collection.setShare(results.getBoolean("share"));
            collection.setCollectionComments(results.getString("collection_comments"));
            collection.setUserId(results.getLong("user_id"));
=======
>>>>>>> Stashed changes
            collections.add(collection);
        }

        return collections;
    }
/*
    public void addRecordToCollection(RecordDTO record) {
        String getRecordId = "SELECT records_id " +
                "FROM records " +
                "WHERE title = ?;";

<<<<<<< Updated upstream
    @Override
    public void deleteCollection(Long collectionId) {

        String deleteCollection = "DELETE " +
                "FROM collections_records " +
                "WHERE collections_id = ?;" +
                "DELETE " +
                "FROM collections " +
                "WHERE collections_id = ?; ";

        jdbcTemplate.update(deleteCollection, collectionId, collectionId);

    }

    /*
=======
        Long recordId = jdbcTemplate.queryForObject(getRecordId, Long.class, record.getTitle());
>>>>>>> Stashed changes

        String getCollectionId = "SELECT collections_id " +
                "FROM collections " +
                "WHERE collection_name = ?";

        String addCollectionRec = "INSERT INTO collections_records(collections_id, records_id) " +
                "VALUES(?, ?);";

        jdbcTemplate.update(addCollectionRec,  record.getRecordId());
    }
*/

    public List<RecordDTO> getRecordsByCollectionId(long collectionId) {
        List <RecordDTO> recordsInCollection = new ArrayList<>();

        String sql = "SELECT records.records_id, title, image " +
                "FROM records " +
                "JOIN collections_records ON records.records_id = collections_records.records_id " +
                "WHERE collections_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
        while(results.next()) {
            RecordDTO record = new RecordDTO();
            record.setRecordId(results.getLong("records.records_id"));
            record.setTitle(results.getString("title"));
            record.setCoverImg(results.getString("image"));
            recordsInCollection.add(record);
        }

        return recordsInCollection;
    }






}
