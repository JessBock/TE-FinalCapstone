<template>
  <div class="home">
    <h1>My Library | </h1><h1><router-link v-bind:to="{name: 'view-collections'}">My Collections</router-link></h1>
    <div class="library">
    <div class="record" v-for="record in library" v-bind:key="record.id">
      <input type='text' v-model='collection.collectionName' placeholder="Enter A Collection Name to Add To..."/>
      <button id="addToCollection" type="submit" v-on:click.prevent='addToCollection(collection, record)'>Add To Collection </button>
      <img v-bind:src="record.coverImg" />
      <h2>{{record.title}}</h2>
      <h2 v-for="artist in record.artists" v-bind:key ="artist.name">{{artist.name}}</h2>
      <h3>{{record.year}}</h3>
      <h3>{{record.genre}}</h3>
      <!--<button id="addToCollection" type="submit">Add To Collection </button> -->
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
  color: black;
}

.record{
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
}

#addToCollection {
  margin-bottom: 15px;
}

</style>
