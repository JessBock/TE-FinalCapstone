<template>
  <div class="allCollection">
      <div class="eachCollection" v-for="collection in collections" v-bind:key="collection.collectionId">
     
        <h1><router-link class="collectionLinks" v-bind:to="{name: 'collection-details', params: {id: collection.collectionId}}">{{collection.collectionName}}</router-link></h1>
        <button class = "deleteBtn" v-on:click="deleteCollection(collection)">Delete Collection</button>
      </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js';
export default {
    name: 'collections',
    data() {
        return {
            collections: [
         
            ],
            
            
            
        }
    },
   
  created() {
    const self = this;
    collectionService.getCollections()
    .then( response => {
      self.collections = response.data;
      this.$store.commit('SET_COLLECTION', response.data);
    })
  },
methods: {
  deleteCollection(collection) {
      this.$confirm(
        'Are you sure you want to delete "' +
        collection.collectionName +
        '"',
        "REMOVE COLLECTION",
        "warning")
      .then (() => {
        collectionService.deleteCollection(collection.collectionId)
        .then(
          collectionService.getCollections()
          .then( response => {
            this.collections = response.data;
            },
            location.reload()
          )
        )
      });
    },

    deleteCollectionFromRecord(collectionId, recordId){
      collectionService.deleteRecordFromCollection(collectionId, recordId)
       .then(
        collectionService.getCollections()
        .then( response => {
          this.collections = response.data;
          },
          location.reload()
        )
      )
    },
    }
  }


</script>

<style scoped>

.allCollection {
  display: flex;
  justify-content: center;
}

.eachCollection {
  background-color: black;
  border-radius: 5px;
  max-width: 25vw;
  margin: 10px;
  text-align: center;
}

.collectionLinks {
  color: white;
}

</style>