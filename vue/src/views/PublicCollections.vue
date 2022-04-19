<template>
  <div>
      <h1 id='title'>Our Public Collections!</h1>
      <div v-for='collection in publicCollections' v-bind:key='collection.collectionId'>
        <h2><router-link v-bind:to="{name: 'public-collection-details', params: {id: collection.collectionId}}">{{collection.collectionName}}</router-link></h2>
      </div>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js'
export default {
    data() {
        return {
            publicCollections: []
        }
    },

    created() {
        const self = this;
        collectionService.getPublicCollections()
        .then( response => {
            self.publicCollections = response.data;
            this.$store.commit('SET_PUBLIC_COLLECTIONS', response.data);
        })
    }

}
</script>

<style scoped>
#title{
    text-align: center;
    color: honeydew;
    background-color: black;
    display: ;
    
}


</style>