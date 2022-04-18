<template>
  <div class='recordDetails'>
      
      <img v-bind:src="details.coverImg" />
      <h1>{{details.artists[0].name}}</h1>
      <h2>{{details.title}}</h2>
      <div v-for="track in details.tracklist" v-bind:key="track.position">
          {{track.title}} : {{track.duration}} - {{track.position}}
      </div>
      <select v-model.number='collectionId' >
          <option v-for="collection in $store.state.collection"  v-bind:value="collection.collectionId" v-bind:key="collection.collectionId">{{collection.collectionName}}</option>
      </select>
      <button type=submit v-on:click.prevent="saveToCollection()">Add to Collection</button>
  </div>
</template>

<script>
import collectionService from '@/services/CollectionService.js';

export default {
    data() {
        return {
            details: {},
            collectionId: ''
        }
    },
    created() {
        this.details = this.$store.state.library.find( record => {
            return record.recordId === this.$route.params.id;
        }

        )
    },
    methods: {
        saveToCollection() {
            collectionService.addRecordToCollection(this.details.recordId, this.collectionId)
            
            
            
        },
       
    },
    computed: {
        
    }
}
</script>

<style>
.recordDetails{
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
    border-radius: 20px;
    margin: 5px;
}

</style>