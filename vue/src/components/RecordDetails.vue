<template>
  <div>
    <div class="search-result">
      <div
        class="single-result"
        v-for="result in $store.state.results"
        v-bind:key="result.id"
        v-bind:result="result"
      >
      <div class="background">
        <img src="../assets/Gramophone_Vinyl_LP_Record.png" />
      </div>
      <div class="album-art">
        <img
          class="searchImage"
          v-bind:src="result.cover_image"
          v-on:click="goToDetails(result.id)"
        />
        <h2>{{ result.title }}</h2>
        <h4>{{ result.year }} : {{ result.genre }}</h4>
        </div>
      </div>
    </div>

    <div class="recordDetails" v-show="show == true">
      <h1 v-for="artist in newRecord.artists" v-bind:key="artist.id">
        {{ artist.name }}
      </h1>
      <img v-bind:src="$store.state.record.cover_image" />
      <h5 v-for="track in newRecord.tracklist" v-bind:key="track.position">
        {{ track.title }} : {{ track.duration }} - {{ track.position }}
      </h5>

      <button
        type="submit"
        v-on:click="saveToLibrary(newRecord)"
      >
        Add To Library
      </button>
    </div>
  </div>
</template>

<script>
import recordService from "@/services/RecordService.js";

export default {
  name: "record-details",

  data() {
    return {
      show: false,
    };
  },
  computed: {
    newRecord() {
      let newRecord = {
        genre: this.$store.state.record.genre,
        title: this.$store.state.recordDetails.title,
        artists: this.$store.state.recordDetails.artists,
        tracklist: this.$store.state.recordDetails.tracklist,
        year: this.$store.state.record.year,
        coverImg: this.$store.state.record.cover_image,
      };
      return newRecord;
    },
  },

  methods: {
    saveToLibrary(newRecord) {
      this.$confirm(
        "You want to add '" +
          this.$store.state.recordDetails.title +
          "' to your library?",
        "Add Record",
        "question"
      ).then(() => {
        this.$store.commit("SAVE_TO_LIBRARY", newRecord);
        recordService
          .saveToDB(newRecord)
          .then(
            (this.newRecord = {}),
            this.$router.push({ name: "home" }),
            location.reload()
          );
      });
    },
    goToDetails(id) {
      recordService
        .getRecordById(id)
        .then((response) => {

          this.show = true;

          this.$store.commit("UPDATE_RECORD_DETAILS", response.data);

          let album = this.$store.state.results.find((element) => {
            return element.id === id;
          });
          this.$store.commit("UPDATE_RECORD", album);
          this.$store.commit("UPDATE_RESULTS", []);
        })
        .catch((error) => {
          if (error.response) {
            this.errorMsg =
              "Received an error from the server: " + error.response.statusText;
          } else if (error.request) {
            this.errorMsg = "Error submitting request, could not reach server";
          } else {
            this.errorMsg =
              "I'm sorry, there was an error. Please try again later.";
          }
        });
    },
  },
};
</script>

<style scoped>
.recordDetails {
  display: flex;
  flex-direction: column;
  background-color: black;
  color: white;
  margin: 1vw;
  padding: 20px;
  width: 30vw;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 20px;
  margin: 5px;
}
.single-result {
  position: relative;
  display: flex;
  flex-direction: column;
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
  /*background-image: url("../assets/Gramophone_Vinyl_LP_Record.png"); 
  background-position: center;
  background-size: 600px; */
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
  font-size: 13px;
  cursor: pointer;
}
.searchImage {
  max-width: 250px;
  height: auto;
}
.record-details {
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
  font-size: 17px;
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

.background:hover {
 animation: rotation 8s infinite linear;
}
.search-result {
  display: flex;
  flex-wrap: wrap;
}

@keyframes rotation {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(359deg);
  }
}
</style>

    