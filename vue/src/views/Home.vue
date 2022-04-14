<template>
  <div class="home">
    <h1>My Library | </h1><h1><router-link v-bind:to="{name: 'view-collections'}">My Collections</router-link></h1>
    <div class="library">
    <div class="record" v-for="libraryRecord in library" v-bind:key="libraryRecord.id">
      <input type='text' v-model='collection.collectionName' placeholder="Enter A Collection Name to Add To..."/>
      <button id="addToCollection" type="submit" v-on:click.prevent='addToCollection(collection, record)'>Add To Collection </button>
      <img v-bind:src="libraryRecord.coverImg" />
      <h2>{{libraryRecord.title}}</h2>
      <h2 v-for="artist in libraryRecord.artists" v-bind:key ="artist.name">{{artist.name}}</h2>
      <h3>{{libraryRecord.year}}</h3>
      <h3>{{libraryRecord.genre}}</h3>
      <button class="deleteBtn" v-on:click="deleteFromLibrary(libraryRecord)">Delete From Library</button>

    </div>
    </div>
  </div>
</template>

<script>

import recordService from "@/services/RecordService.js";
import collectionService from '@/services/CollectionService.js';

export default {
  name: "home",
  data() {
    return {
      library: [],
      collection: {
        collectionName: '',
        records: []
      }
    }
  },
  created() {
    recordService.getLibrary()
      .then(
        response => {
          this.library = response.data;
        }
      )
  },
  methods: {
    deleteFromLibrary(record) {
      recordService.deleteFromLibrary(record.recordId)
      .then( response => {
        if(response.status == 200) {
          this.$store.commit('DELETE_FROM_STORE', record)
          recordService.getLibrary();
        }
      });
    },
    addToCollection(collection, record) {
      collection.records.push(record);
      this.$store.commit('SAVE_TO_COLLECTION',collection);

      collectionService.addCollection(collection);
        
      this.collection.collectionName = ''; 
      this.collection.records = [];

          
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
}

.record{
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
  
}

.deleteBtn {
  margin: 10px;
}

#addToCollection {
  margin-bottom: 15px;
}

</style>
