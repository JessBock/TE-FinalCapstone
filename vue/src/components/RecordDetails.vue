<template>
  <div>
      <div>
       <h1>{{recordDetails.artists[0].name}} </h1>
       <h5 v-for="track in recordDetails.tracklist" v-bind:key="track.position"> {{track.title}} : {{track.duration}} - {{track.position}}</h5>
      </div>
  </div>
</template>

<script>



export default {
  
    name: "record-details",
    props: ["recordDetails", "record"]
    

}
</script>

<style>

</style>