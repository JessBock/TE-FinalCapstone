<template>
  <div>
      <div>
       <h1>{{newRecord.artists[0].name}} </h1>
       <img v-bind:src="record.cover_image"/> 
       <h5 v-for="track in newRecord.tracklist" v-bind:key="track.position"> {{track.title}} : {{track.duration}} - {{track.position}}</h5>
       
       <button v-show="record.type == 'master'" type="submit" v-on:click="saveToLibrary(newRecord)">Add To Library</button>
       
      </div>
  </div>
</template>

<script>

import recordService from '@/services/RecordService.js';

export default {
  
    name: "record-details",
    props: ["recordDetails", "record"],

    data() {
      return {
        newRecord: {
      
        }
      }
    },
    created() {
      this.newRecord = {
            genre: this.record.genre,
            title: this.recordDetails.title,
            artists: this.recordDetails.artists,
            tracklist: this.recordDetails.tracklist,
            year: this.record.year,
            coverImg: this.record.cover_image
      }
      
    },

    methods: {
      saveToLibrary(newRecord) {
        
        this.$store.commit('SAVE_TO_LIBRARY', newRecord);

        recordService.saveToDB(newRecord);
        
        this.newRecord = {};
        
        this.$router.push({name: 'home'});
        location.reload();

        
      }
    }


    

}
</script>

<style>

</style>

    