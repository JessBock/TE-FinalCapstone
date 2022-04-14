import axios from 'axios';

export default {
    addCollection(collectionName) {
       
        return axios.post('/collections/add', collectionName);
    
    },

    getCollections() {
        return axios.get('/collections');
    }



}