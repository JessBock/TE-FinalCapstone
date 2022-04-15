import axios from 'axios';

export default {
    addCollection(collectionName) {
       
        return axios.post('/collections/' + collectionName);
    
    },

    getCollections() {
        return axios.get('/collections');
    },

    getCollectionById(collectionId) {
        return axios.get('/collections/' + collectionId);
    },

    deleteCollection(collectionId) {
        return axios.delete('/collections/' + collectionId);
    }

}