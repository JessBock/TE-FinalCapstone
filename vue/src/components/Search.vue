<template>
  <form v-on:submit.prevent="searchWebsite()">
      <input type="text" v-model="searchTerm"/>
      <button >Search</button>
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
  </form>
</template>

<script>
import recordService from '@/services/RecordService.js';
import RecordDetails from './RecordDetails.vue';

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
            const self = this;
            recordService.search(this.searchTerm)
            .then( response => {
                self.results = self.filterResults(response.data.results);
                this.$router.push({name: 'results'});
                self.recordDetails = [];
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
              if (error.response) {
            this.errorMsg = "Received an error from the server: " + error.response.statusText;
            } else if (error.request) {
             this.errorMsg = "Error submitting request, could not reach server";
            } else {
              this.errorMsg = "I'm sorry, there was an error. Please try again later.";
        }
      });
    },

    filterResults(results) {
        return results.filter( img => {
          return img.type == 'master';
        })
      }
    },
    computed: {
      

    },
    created() {
      
    },
  
}
</script>

<style scope>

.single-result{
  display: flex;
  flex-direction: column;
  background-color: black;
  color: white;
  margin: 1vw;
  padding: 20px;
  width: 500px;
  height: 500px;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 50%;
  margin: 5px;
  background-image: url('https://gallery.yopriceville.com/var/resizes/Free-Clipart-Pictures/Music-PNG/Gramophone_Vinyl_LP_Record_PNG_Transparent_Clip_Art_Image.png?m=1462983196');
  background-position: center;
  background-size: 525px;
}

img {
  max-width: 300px;
  height: 300px;
}



</style>