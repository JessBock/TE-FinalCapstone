import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    isNavOpen: false,
    token: currentToken || '',
    user: currentUser || {},
    library: [],
    results: [],
    recordDetails: [],
    record: {},
    collection: [
      {
      collectionName: '',
      records: []
    }
  ],
    publicCollection: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },

    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },

    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },

    SAVE_TO_LIBRARY(state, newRecord) {
      state.library.push(newRecord);
    },

    DELETE_FROM_STORE(state, recordId) {
      let index = state.library.findIndex(album => album.recordId === recordId)
      state.library.splice(index, 1);
    },

    SAVE_TO_COLLECTION(state, collection) {
      state.collection.push(collection);
    },

    SET_LIBRARY(state, payload) {
        state.library = payload;
    },

    UPDATE_RESULTS(state, listResults) {
      state.results = listResults;
    },

    UPDATE_RECORD_DETAILS(state, album) {
      state.recordDetails = album;
    },

    UPDATE_RECORD(state, record) {
      state.record = record;
    },

    SET_COLLECTION(state, collection) {
      state.collection = collection;
    },

    SET_PUBLIC_COLLECTIONS(state, collection) {
      state.publicCollection = collection;
    },

    TOGGLE_NAV(state) {
        state.isNavOpen = !state.isNavOpen
    },
  } 
})