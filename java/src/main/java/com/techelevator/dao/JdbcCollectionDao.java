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
public class JdbcCollectionDao implements CollectionDao {
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


        String getCollections = "SELECT collection_name, collections_id, share, collection_comments, user_id " +
                "FROM collections " +
                "WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(getCollections, user.getId());

        while (results.next()) {
            Collection collection = new Collection();
            collection.setCollectionName(results.getString("collection_name"));
            collection.setCollectionId(results.getLong("collections_id"));
            collection.setShare(results.getBoolean("share"));
            collection.setCollectionComments(results.getString("collection_comments"));
            collection.setUserId(results.getLong("user_id"));

            collections.add(collection);
        }

        return collections;
    }



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


    @Override
    public void deleteRecordFromCollection(Long recordId, Long collectionId) {

        String deleteRecordFromCollection = "DELETE " +
                "FROM collections_records " +
                "WHERE records_id = ? and collections_id = ?; ";

        jdbcTemplate.update(deleteRecordFromCollection, recordId, collectionId);

    }

    
@Override

    public List<RecordDTO> getRecordsByCollectionId(long collectionId) {
        List<RecordDTO> recordsInCollection = new ArrayList<>();

        String sql = "SELECT records.records_id, title, image " +
                "FROM records " +
                "JOIN collections_records ON records.records_id = collections_records.records_id " +
                "WHERE collections_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
        while (results.next()) {
            RecordDTO record = new RecordDTO();
            record.setRecordId(results.getLong("records_id"));
            record.setTitle(results.getString("title"));
            record.setCoverImg(results.getString("image"));
            recordsInCollection.add(record);
        }



        return recordsInCollection;
    }

    public String getCollectionName(long collectionId) {

        String sql = "SELECT collection_name " +
                "FROM collections " +
                "WHERE collections_id = ?;";

        String collectionName = jdbcTemplate.queryForObject(sql, String.class, collectionId);

        return collectionName;
    }

    public void addRecordToCollection(long recordId, long collectionId) {
        String sql = "INSERT INTO collections_records(records_id, collections_id) " +
                "VALUES(? , ?);";

        jdbcTemplate.update(sql, recordId, collectionId);

    }

    public List<Collection> getPublicCollections() {
        List <Collection> publicCollections = new ArrayList<>();

        String sql = "SELECT collections_id, collection_name, collection_comments " +
                "FROM collections " +
                "WHERE share = true;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {
            Collection collection = new Collection();
            collection.setCollectionId(results.getLong("collections_id"));
            collection.setCollectionName(results.getString("collection_name"));
            collection.setCollectionComments(results.getString("collection_comments"));
            publicCollections.add(collection);
        }
        return publicCollections;

    }




}