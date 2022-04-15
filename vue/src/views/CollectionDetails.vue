<template>
  <div>
    <h2>{{collection.name}}</h2>
    <div v-for="record in collection.records" v-bind:key="record.recordId" v-bind:collection="collection">
      <h1>{{record.title}}</h1>
      <img v-bind:src="record.coverImg" />
      
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
            recordId: ''
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



  },

  methods: {
      getCollectionName() {
         this.collection.name = collectionService.getCollectionName(this.$route.params.id);
         return this.collection.name;
      }
      
  
    
  }


}
</script>

<style>

</style>