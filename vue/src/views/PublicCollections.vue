<template>
  <div class="main">
      <div class="burger-casing">
        <Burger></Burger>
        <Sidebar>
        <div class="stats">
          <h3 class="databaseTitle">Database Stats: </h3>
          <h3>{{recordCount}} Total Records</h3>
            <table class="genre-table">
              <tr>
                <th>Genre Name</th>
                <th>Count</th>
              </tr>
              <tr v-for="stat in genreStats" v-bind:key="stat.name">
                <td>{{stat.name}}</td>
                <td class="number-count">{{stat.count}}</td>
              </tr>
            </table>
            <table class="artist-table">
              <tr>
                <th>Artist Name</th>
                <th>Count</th>
              </tr>
              <tr v-for="stat in artistStats" v-bind:key="stat.name">
                <td>{{stat.name}}</td>
                <td class="number-count">{{stat.count}}</td>
              </tr>
            </table>
        </div>
      </Sidebar>
    </div>
      <h1 id='title'>Our Public Collections!</h1>
      <div v-for='collection in publicCollections' v-bind:key='collection.collectionId'>
        <h2><router-link class="collectionLink" v-bind:to="{name: 'public-collection-details', params: {id: collection.collectionId}}">{{collection.collectionName}}</router-link></h2>
      </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js'
import recordService from '@/services/RecordService.js'
import Burger from '../components/Burger.vue';
import Sidebar from '../components/Sidebar.vue';
export default {
    components: {
      Burger,
      Sidebar,
    },
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

.main {
  overflow-x:hidden;
}

#title{
    text-align: center;
    color: honeydew;
    background-color: black;
    
    
}

div {
    font-family: "limelight-regular", serif;
    color: honeydew;
}

.stats{
  margin: 10px;
  align-items: center;
  justify-content: center;
  font-size: 15px;
}

.stats h3 {
  margin-bottom: 10px;
  font-weight: bold;
}
.stats table {
  text-align: left;
  margin-bottom: 15px;
}

.stats table td {
  padding: 8px;
  border: honeydew 1px solid;
}

.stats table th {
  color: #CC5500;
}

.databaseTitle {
  color: #CC5500;
}

.burger-casing {
  display: flex;
  justify-content: flex-end;
}

.number-count {
  text-align: end;
}

.collectionLink {
  text-decoration: none;
  color: honeydew;
}

.collectionLink:hover {
  color: #CC5500;
  text-decoration: underline;
}

</style>