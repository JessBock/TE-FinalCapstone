<template>
  <div class="home">
    <h1>My Library</h1>
    <div class="library">
    <div class="record" v-for="record in library" v-bind:key="record.id">
      <img v-bind:src="record.coverImg" />
      <h2>{{record.title}}</h2>
      <h2 v-for="artist in record.artists" v-bind:key ="artist.name">{{artist.name}}</h2>
      <h3>{{record.year}}</h3>
      <h3>{{record.genre}}</h3>

    </div>
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
.home {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.library {
  display: flex;
  flex-wrap: wrap;
}
h1 {
  color: black;
}

.record{
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
}
</style>
