import axios from 'axios';

export default {
    addCollection(collectionName) {
       
        return axios.post('/collections/' + collectionName);
    
    },

    getCollections() {
        return axios.get('/collections');
    },
<<<<<<< Updated upstream
=======

    getCollectionById(collectionId) {
        return axios.get('/collections/' + collectionId);
    }

>>>>>>> Stashed changes

    deleteCollection(collectionId) {
        return axios.delete('/collections/' + collectionId);
    }

}