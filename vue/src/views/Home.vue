<template>
  <div class="home">
    <h1>User logged in page</h1>
    <div v-for="record in library" v-bind:key="record.id">
      <img v-bind:src="record.coverImg" />
      <h1>{{record.title}}</h1>
      <h2 v-for="artist in artists" v-bind:key ="artist.name">{{artist}}</h2>
    </div>
  </div>
</template>

<script>

import recordService from "@/services/RecordService.js"

export default {
  name: "home",
  data() {
    return {
      library: []
    }
  },
  created() {
    recordService.getLibrary()
      .then(
        response => {
          this.library = response.data;
        }
      )
  }
 
};
</script>
<style scoped>

h1 {
  color: red;
}
</style>
