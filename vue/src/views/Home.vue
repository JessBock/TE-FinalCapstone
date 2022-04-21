<template>
  <div class="home">

    <div class="burger-casing">
      <Burger></Burger>
      <Sidebar>
        <div class="stats">
            <h3 class="libraryTitle">Library Stats: </h3>
            <h3>{{libraryCount}} Total Records</h3>


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


    <nav class="home_navigation">
      <h1>My Library |</h1>
      <h1>
        <router-link class="collectionLink" v-bind:to="{ name: 'view-collections' }"
          >My Collections</router-link
        >
      </h1>
    </nav>


    <div class="library">
      <div 
        class="record"
        v-for="libraryRecord in displayLibrary"
        v-bind:key="libraryRecord.id"
      >
        <div class="background">
          <img src="../assets/Gramophone_Vinyl_LP_Record.png" v-on:click="goToTracks(libraryRecord.recordId)"/>
        </div>

        <div class="album-art">
          <img class="coverImage" v-bind:src="libraryRecord.coverImg" v-on:click="goToTracks(libraryRecord.recordId)"/>
          <h2>{{ libraryRecord.title }}</h2>
          <h2 v-for="artist in libraryRecord.artists" v-bind:key="artist.name">
            {{ artist.name }}
          </h2>
          <h3>{{ libraryRecord.year }}</h3>
          <h3>{{ libraryRecord.genre }}</h3>
          <button class="deleteBtn" v-on:click="deleteFromLibrary(libraryRecord)">
            Delete From Library
          </button>
        </div>
      </div>
    </div>
  </div>
  
</template>

<script>
import recordService from "@/services/RecordService.js";
import collectionService from "@/services/CollectionService.js";
import Burger from '../components/Burger.vue';
import Sidebar from '../components/Sidebar.vue';

export default {
  name: "home",
  components: {
    Burger,
    Sidebar,
  },
  data() {
    return {
      genreStats: {},
      artistStats: {},
      libraryCount: 0
    };
  },
  created() {
    recordService.getLibrary()
      .then(
        response => {
          this.library = response.data;
          this.$store.commit('SET_LIBRARY', response.data);
        }
    );
    const self = this;
    collectionService.getCollections()
    .then( response => {
      self.collections = response.data;
      this.$store.commit('SET_COLLECTION', response.data);
    });

    recordService.getGenreStats()
    .then(response => {
      this.genreStats = response.data;
    });

    recordService.getArtistStats()
    .then(response => {
      this.artistStats = response.data;
    });

    recordService.getUserLibraryCount()
    .then(response => {
      this.libraryCount = response.data
    })

  },
  methods: {
    deleteFromLibrary(record) {
      this.$confirm(
        "Are you sure you want to delete '" +
        record.title +
        "' from your library?",
        "REMOVE RECORD",
        "warning")
      .then(() => {
        recordService.deleteFromLibrary(record.recordId);
        //location.reload();
        this.$store.commit('DELETE_FROM_STORE', record.recordId);
        this.$router.push({name: 'home'});
      })
    },

    /*createCollection(collectionName) {
      this.$store.commit("SAVE_TO_COLLECTION", collectionName);
      collectionService.addCollection(collectionName)
      .then((response) => {
        if (response.status === 200) {
        this.$router.push({ name: "view-collections" });
        }
      });        
    }, */

    goToTracks(id) {
      this.$router.push({name: 'library-record-details', params: {id: id}});
    },
  },
  computed: {
    displayLibrary() {
      let displayLibrary = this.$store.state.library;
      return displayLibrary;
    }
  }
};
</script>
<style scoped>

.home {
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
  overflow-x:hidden;
}

.addACollection {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.library {
  display: flex;
  flex-wrap: wrap;  
}

h1 {
  padding: 10px;
  color: honeydew;
  background-color: rgba(0,0,0,.8);
  font-family: "limelight-regular", serif;
  border-radius: 5px;
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

.libraryTitle {
  color: #CC5500;
}

.number-count {
  text-align: end;
}

.record {
  position: relative;
  display: flex;
  flex-direction: column;
  color: honeydew;
  margin: 1vw;
  padding: 20px;
  width: 500px;
  height: 500px;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 50%;
  margin: 5px;
  font-family: "limelight-regular", serif;
  font-size: 10px;
}

.album-art {
  z-index: 1;
}

.background {
  position:absolute;
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  align-items: center;
  justify-content: center;
 
}

.background img{
 position:absolute;
 background-position: center;
 height: 100%;
 width: 100%;
 z-index: 0;
}

.background img:hover {
 animation: rotation 8s infinite linear;
 cursor: url('../assets/record_needle3.png'), auto;
}

.deleteBtn {
  margin: 10px;
  cursor: pointer;
}

#addToCollection {
  margin-bottom: 15px;
}

.nav_container {
  display: flex;
  justify-content: center;
}

.home_navigation {
  display: flex;
  justify-content: center;
  padding: 10px;
  border-radius: 5px;
}

.coverImage {
  width: 250px;
  height: auto;
}

@keyframes rotation {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(359deg);
  }
}

.burger-casing {
  display: flex;
  justify-content: flex-end;
}

.collectionLink {
  color: honeydew;
  text-decoration: none;
}

.collectionLink:hover {
  color: #CC5500;
  text-decoration: underline;
}

</style>
