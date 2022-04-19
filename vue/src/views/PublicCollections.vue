<template>
  <div>
      <div class="stats">
        <h2>Database Stats: {{recordCount}} Total</h2>
          <table class="genre-table">
            <tr>
              <th>Genre Name</th>
              <th>Count</th>
            </tr>
            <tr v-for="stat in genreStats" v-bind:key="stat.name">
              <td>{{stat.name}}</td>
              <td>{{stat.count}}</td>
            </tr>
          </table>
          <table class="artist-table">
            <tr>
              <th>Artist Name</th>
              <th>Count</th>
            </tr>
            <tr v-for="stat in artistStats" v-bind:key="stat.name">
              <td>{{stat.name}}</td>
              <td>{{stat.count}}</td>
            </tr>
          </table>
    </div>
      <h1 id='title'>Our Public Collections!</h1>
      <div v-for='collection in publicCollections' v-bind:key='collection.collectionId'>
        <h2><router-link v-bind:to="{name: 'public-collection-details', params: {id: collection.collectionId}}">{{collection.collectionName}}</router-link></h2>
      </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js'
import recordService from '@/services/RecordService.js'
export default {
    data() {
        return {
            publicCollections: [],           
            genreStats: {},
            artistStats: {},
            recordCount: 0,
        }
    },

    created() {
        const self = this;
        collectionService.getPublicCollections()
        .then( response => {
            self.publicCollections = response.data;
            this.$store.commit('SET_PUBLIC_COLLECTIONS', response.data);
        });
         recordService.getDatabaseGenreStats()
        .then( response => {
            self.genreStats = response.data;
        });
        recordService.getDatabaseArtistStats()
        .then( response => {
            self.artistStats = response.data;
        });
        recordService.getDatabaseRecordCount()
        .then( response => {
            self.recordCount = response.data;
        })
    }





    }


</script>

<style scoped>
#title{
    text-align: center;
    color: honeydew;
    background-color: black;
    
    
}


</style>