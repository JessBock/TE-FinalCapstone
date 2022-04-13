<template>
  <div v-on:submit.prevent="searchWebsite()">
      <input type="text" v-model="searchTerm"/>
      <button type="submit" v-on:click.prevent="searchWebsite()">Search</button>
      <div class="search_result">
        <div class="single-result" v-for="result in results" v-bind:key="result.id" v-bind:result="result">
          <img v-bind:src="result.cover_image" v-on:click="goToDetails(result.id)"/>
          <h2> {{result.title}} </h2>
          <h4> {{result.year}} : {{result.genre}}</h4>
        </div>
      </div>
      <div>
          <record-details v-bind:key="recordDetails" v-bind:record="record" v-bind:recordDetails="recordDetails"/>
      </div>
  </div>
</template>

<script>
import recordService from '@/services/RecordService.js';
import RecordDetails from './RecordDetails.vue';
//import moment from 'moment';

export default {
  components: { RecordDetails },
    name: "search",
    
    data() {
        return {
            searchTerm: '',
            results: [],
            recordDetails: [],
            errorMsg: '',
            coverImg: '',
            record: {}
        }
    },

    methods: {
        searchWebsite() {
            recordService.search(this.searchTerm)
            .then( response => {
                this.results = response.data.results;
            })
            .catch( error => {
            if (error.response) {
            this.errorMsg = "Received an error from the server: " + error.response.statusText;
            } else if (error.request) {
             this.errorMsg = "Error submitting request, could not reach server";
            } else {
              this.errorMsg = "I'm sorry, there was an error. Please try again later.";
        }
      })
     },
        goToDetails(id) {
          const self = this;
            recordService.getRecordById(id)
            .then( response => {
                self.recordDetails = response.data;
                self.record = self.results.find( element => {
                    return element.id === id;
                });
                
                self.results = [];
            })
            .catch( error => {
              if (!this.recordDetails) {
                alert("This selection does not have any additional details. Please make another choice.")
            } else if (error.response) {
            this.errorMsg = "Received an error from the server: " + error.response.statusText;
            } else if (error.request) {
             this.errorMsg = "Error submitting request, could not reach server";
            } else {
              this.errorMsg = "I'm sorry, there was an error. Please try again later.";
        }
      });
    }
    },
    created() {
      
    },
  /*  filters: {
      moment: function(year) {
        return moment(year).format('YYYY');
      }
    }
*/
}
</script>

<style scope>

.single-result{
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas: 
    "img h2 h4";
}

img {
  max-width: 300px;
  height: 300px;
  grid-area: image;
}

h2 {
  grid-area: h2;
}

h4 {
  grid-area: h4;
}

</style>