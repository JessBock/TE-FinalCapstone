<template>
  <div class="collectionMain">
    <h2 id="collectionName">{{collection.name.collectionName}}</h2>
    <h4><router-link class ="returnMainCollections" v-bind:to="{name: 'view-collections'}">Return</router-link></h4>
    <div class="recordsInCollection">
      <div class="record" v-for="record in collection.records" v-bind:key="record.recordId" v-bind:collection="collection">
        <div class="background">
          <img src="../assets/Gramophone_Vinyl_LP_Record.png" />
        </div>
        <div class="album-art">
          <img v-bind:src="record.coverImg" id="singleArt" />
          <h2>{{record.title}}</h2>
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
         this.collection.name = this.$store.state.collection.find(element => {
           return element.collectionId === this.$route.params.id
         });
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
.returnMainCollections {
  color: honeydew;
  text-decoration: none;
  background-color: rgba(0,0,0,.8);
}

.returnMainCollections:hover {
  color: #CC5500;
  text-decoration: underline;
}
#collectionName {
  color: honeydew;
  background-color: rgba(0,0,0,.8);
  border-radius: 10px;
  font-size: 40px;
  padding: 5px;
  margin: 10px;
}

.collectionMain {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: "limelight-regular", serif;
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
  font-size: 20px;
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

.background img {
 
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

.background:hover {
 animation: rotation 8s infinite linear;
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