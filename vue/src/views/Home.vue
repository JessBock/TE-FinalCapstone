<template>
  <div class="home">
    <nav class="home_navigation">
      <h1>My Library |</h1>
      <h1>
        <router-link v-bind:to="{ name: 'view-collections' }"
          >My Collections</router-link
        >
      </h1>
    </nav>
    <input
      type="text"
      v-model="collectionName"
      placeholder="Enter A Collection Name"
    />
    <button
      id="addToCollection"
      type="submit"
      v-on:click.prevent="createCollection(collectionName)"
    >
      Create A New Collection
    </button>
    <div class="library">
      <div
        class="record"
        v-for="libraryRecord in library"
        v-bind:key="libraryRecord.id"
      >
        <img class="coverImage" v-bind:src="libraryRecord.coverImg"  v-on:click="goToTracks(libraryRecord.recordId)" />
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
</template>

<script>
import recordService from "@/services/RecordService.js";
import collectionService from "@/services/CollectionService.js";

export default {
  name: "home",
  data() {
    return {
      library: [],
      collectionName: "",
    };
  },
  created() {
    recordService.getLibrary()
      .then(
        response => {
          this.library = response.data;
          this.$store.commit('SET_LIBRARY', response.data);
        }
      )
  },
  methods: {
    deleteFromLibrary(record) {
      recordService.deleteFromLibrary(record.recordId).then(
        recordService.getLibrary().then((response) => {
          this.library = response.data;
          this.$store.commit('SET_LIBRARY', response.data);
          },
          location.reload()
        )
      )
    },
    createCollection(collectionName) {
      this.$store.commit("SAVE_TO_COLLECTION", collectionName);

      collectionService.addCollection(collectionName).then((response) => {
        if (response.status === 200) {
          this.$router.push({ name: "view-collections" });
        }
      })
          
  },

  goToTracks(id) {


    this.$router.push({name: 'library-record-details', params: {id: id}});
  }
  }
};
</script>
<style scoped>
.home {
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
  color: white;
  background: black;
  padding: 10px;
  border-radius: 5px;
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
}

.record {
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
  background-image: url("../assets/Gramophone_Vinyl_LP_Record.png");
  background-position: center;
  background-size: 600px;
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
  font-size: 10px;
}

.deleteBtn {
  margin: 10px;
}

#addToCollection {
  margin-bottom: 15px;
}

.home_navigation {
  display: flex;
  justify-content: space-between;
}

.coverImage {
  width: 250px;
  height: auto;
}




</style>
