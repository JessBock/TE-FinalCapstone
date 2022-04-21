<template>
  <div>
<!-- Search Results -->
    <div class="search-result" v-show="show == false">
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

<!-- Record Details -->
    <div class="recordDetails" v-show="show == true">
      <div class="recordImage">
        <img v-bind:src="$store.state.record.cover_image" />
        <h1 v-for="artist in newRecord.artists" v-bind:key="artist.id">
          {{ artist.name }}
        </h1>
        <h2>{{newRecord.title}}</h2>
      
      </div>

      <div class="text">
      <div class="tracks">
        <h2>Tracks:</h2>
        <div v-for="track in newRecord.tracklist" v-bind:key="track.position">
        {{ track.title }} : {{ track.duration }} - {{ track.position }}
        </div>
      </div>
      <br/>
      <h4 class="return" v-on:click="returnToSearch">Return</h4>
                                                                  <!-- only show button if user logged in-->
      <button type="submit" v-on:click="saveToLibrary(newRecord)" v-if="$store.state.token != ''">Add To Library</button>
      </div>
    </div>
  </div>
</template>

<script>
import recordService from "@/services/RecordService.js";

export default {
  name: "record-details",
  data() {
    return {
      
    };
  },
  created() {
  
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
    show() {
      let isShow = false;
      if(this.$store.state.recordDetails.length !== 0) {
        isShow = true;
      }
      return isShow;
    }
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
            this.$store.commit("UPDATE_RECORD_DETAILS", [])
          );
      });
    },
    goToDetails(id) {
      recordService
        .getRecordById(id)
        .then((response) => {
          this.$store.commit("UPDATE_RECORD_DETAILS", response.data);

          let album = this.$store.state.results.find((element) => {
            return element.id == id;
          });
          this.$store.commit("UPDATE_RECORD", album)
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
    returnToSearch() {
      this.newRecord = {},
      this.$store.commit("UPDATE_RECORD_DETAILS", [])
    }
  },
};
</script>

<style scoped>
div {
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
}
.recordDetails {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin: auto;
  flex-grow: 1;
  height: 85vh;
}
.single-result {
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
  font-size: 13px;
  cursor: pointer;
}
.searchImage {
  max-width: 250px;
  height: auto;
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






.text {
    display: flex;
    flex-direction: column;
    background-color: black;
    color: white;
    margin: 1vw;
    flex-grow: 2;
    padding: 20px;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 20px;
}

.text div {
    flex-grow: 1;
    display: flex;
    align-items: center;
    justify-items: center;
}

.tracks {
    flex-direction: column;
}

.return {
  color: honeydew;
  text-decoration: none;
}

.return:hover {
  color: #CC5500;
  text-decoration: underline;
}

</style>

    