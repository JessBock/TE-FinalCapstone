<template>
  <div>
      <div>
       <h1>{{recordDetails.artists[0].name}} </h1>
       <img v-bind:src="record.cover_image"/> 
       <h5 v-for="track in recordDetails.tracklist" v-bind:key="track.position"> {{track.title}} : {{track.duration}} - {{track.position}}</h5>
       
       <button v-show="record.type == 'master'" type="submit" v-on:click.prevent="saveToLibrary(recordDetails, record)">Add To Library</button>
       
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
          genre: '',
          title: '',
          artists: [],
          tracklist: '',
          year: '',
          coverImg: '' 

        }
      }
    },

    methods: {
      saveToLibrary(recordDetails, record) {
        this.newRecord = {
            genre: record.genre,
            title: recordDetails.title,
            artists: recordDetails.artists,
            tracklist: recordDetails.tracklist,
            year: record.year,
            coverImg: record.cover_image
        }
        this.$store.commit('SAVE_TO_LIBRARY', this.newRecord);

        recordService.saveToDB(this.newRecord);
      }
    }
    

}
</script>

<style>

</style>

    