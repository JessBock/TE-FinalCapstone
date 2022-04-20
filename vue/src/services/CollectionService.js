import axios from 'axios';

export default {
    addCollection(collectionName, isPublic) {
       
        return axios.post('/collections/' + collectionName + '/' + isPublic);
    
    },

    getCollections() {
        return axios.get('/collections');
    },

    getCollectionById(collectionId) {
        return axios.get('/collections/' + collectionId);
    },


    deleteCollection(collectionId) {
        return axios.delete('/collections/' + collectionId);
    },

    getCollectionName(collectionId) {
        return axios.get('/collections/' + collectionId + '/name');
    },

    addRecordToCollection(recordId, collectionId) {
        return axios.post('/collections/' + collectionId + '/add/' + recordId);
    },
        
    deleteRecordFromCollection(collectionId, recordId) {
        return axios.delete('/collections/' + collectionId + '/' + recordId);
    },

    getPublicCollections() {
        return axios.get('/all-collections');
    }

}