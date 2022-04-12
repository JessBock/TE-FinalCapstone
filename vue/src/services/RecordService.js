import axios from 'axios';

export default {
    search(searchTerm) {
        return axios.get('/search/' + searchTerm);
    },

    getRecordById(id) {
        return axios.get('/releases/' + id);
    },

    saveToDB(payload) {
        return axios.post('/library', payload);
    }
}