<template>
  <div >
      <div v-for="collection in collections" v-bind:key="collection.id">
      <h1>{{collection.collectionName}}</h1>
      </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js';
export default {
    name: 'collections',
    data() {
        return {
            collections: []
        }
    },
   
  created() {
    collectionService.getCollections()
    .then( response => {
      this.collections = response.data;
    })
  }

    
}
</script>

<style>

</style>