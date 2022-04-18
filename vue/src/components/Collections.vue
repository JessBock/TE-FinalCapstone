<template>
  <div >
      <div v-for="collection in collections" v-bind:key="collection.collectionId">
     
      <h1><router-link v-bind:to="{name: 'collection-details', params: {id: collection.collectionId}}">{{collection.collectionName}}</router-link></h1>
       <button class = "deleteBtn" v-on:click="deleteCollection(collection.collectionId)">Delete Collection</button>
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
  deleteCollection(collectionId) {
    collectionService.deleteCollection(collectionId)
    .then(
        collectionService.getCollections()
        .then( response => {
          this.collections = response.data;
          },
          location.reload()
        )
      )
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

<style>

</style>