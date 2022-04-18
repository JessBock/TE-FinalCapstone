<template>
  <div class="collectionMain">
    <h2>{{collection.name}}</h2>
    <div class="recordsInCollection">
      <div class="record" v-for="record in collection.records" v-bind:key="record.recordId" v-bind:collection="collection">

        <div class="background">
          <img src="../assets/Gramophone_Vinyl_LP_Record.png" />
        </div>

        <div class="album-art">
          <img v-bind:src="record.coverImg" id="singleArt" />
          <h2>{{record.title}}</h2>
        <!--<h2>{{record.artists[0]}}</h2>
        <h3>{{record.year}}</h3>
        <h3>{{record.genre}}</h3>-->
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>

import collectionService from '@/services/CollectionService.js'
export default {
  name: 'collection-details',
  props: [],
  
  data() {
    return {
      collection: {
        name: '',
        records: [
          {
            title: '',
            coverImg: '',
            recordId: '',
            artists: '',
            year: '',
            genre: [],
          }
        ]
      }
    }
  },
  created() {
    collectionService.getCollectionById(this.$route.params.id)
    .then( response => {
      this.collection.records = response.data;
      });
         this.collection.name = collectionService.getCollectionName(this.$route.params.id).then(() => {
          return this.collection.name;
         })
  },

  methods: {
      getCollectionName() {
         this.collection.name = collectionService.getCollectionName(this.$route.params.id).then(() => {
          return this.collection.name;
         });
      } 
  }



}
</script>

<style scoped>
.collectionMain {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.recordsInCollection {
  display: flex;
  flex-wrap: wrap;
}

.record {
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
  /*background-position: center;
 background-size: 600px; */
  font-family: "vinyl-regular", "limelight-regular", "carosello-regular",
    "frontage-condensed-outline", serif;
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

#singleArt {
  width: 250px;
  height: auto;
}

</style>