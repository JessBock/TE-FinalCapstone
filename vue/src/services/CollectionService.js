import axios from 'axios';

export default {
    addCollection(collection) {
       
        return axios.post('/collections/add', collection);
    
    }



}