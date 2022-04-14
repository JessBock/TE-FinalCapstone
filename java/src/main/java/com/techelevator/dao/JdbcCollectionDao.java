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
    public void saveCollection(RecordDTO record, Collection collection, Principal principal) {
        long userId = userDao.findIdByUsername(principal.getName());

        String addingCollection = "INSERT INTO collections (collection_name, count, share, collection_comments, user_id) " +
                "VALUES (?, (SELECT COUNT(collection_name) " +
                "FROM collections " +
                "WHERE user_id = ?) , ?, ?, ?); RETURNING collections_id";

        Long collectionId = jdbcTemplate.queryForObject(addingCollection, Long.class, collection.getCollectionName(), userId,
                collection.isShare(), collection.getCollectionComments(), userId);

        String getRecordId = "SELECT records_id " +
                "FROM records " +
                "WHERE title = ?;";

        Long recordId = jdbcTemplate.queryForObject(getRecordId, Long.class, record.getTitle());

        String addCollectionRec = "INSERT INTO collections_records(collections_id, records_id) " +
                "VALUES(?, ?);";

        jdbcTemplate.update(addCollectionRec, collectionId, recordId);

    }

    @Override
    public List<Collection> getCollections(User user) {

        List<Collection> collections = new ArrayList<>();

        String getCollections = "SELECT collection_name " +
                "FROM collections " +
                "WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(getCollections, user.getId());

        while(results.next()) {
            Collection collection = new Collection();
            collection.setCollectionName(results.getString("collection_name"));
            collections.add(collection);
        }

        return collections;
    }
}
