import axios from 'axios';

export default {
    search(searchTerm) {
        return axios.get('/search/' + searchTerm);
    },

    //calls discogs api
    getRecordById(id) {
        return axios.get('/releases/' + id);
    },

    saveToDB(payload) {
        return axios.post('/library', payload);
    },

    deleteFromLibrary(recordId) {
        return axios.delete('/library/' + recordId);
    },

    getLibrary() {
        return axios.get('/library');
    },

    setCommentsAndCondition(payload) {
        return axios.put('/library/addComments', payload);
    },

    getGenreStats() {
        return axios.get('library/genreStats')
    },

    getArtistStats() {
        return axios.get('library/artistStats')
    }


}