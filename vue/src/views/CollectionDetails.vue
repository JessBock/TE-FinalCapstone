<template>
  <div>
    <div v-for="record in collection.records" v-bind:key="record.id" v-bind:collection="collection">
      <h1>{{record.title}}</h1>
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
        records: [
          {
            title: '',
            coverImg: '',
          }
        ]
      }
    }
  },
  created() {
    collectionService.getCollectionById(this.$route.params.collection.collectionId)
    .then( response => {
      this.collection.records =  response.data;
      
      /*{
            
            title: '',
            coverImg: '',
      } */
      
      
     
    })
  }


}
</script>

<style>

</style>