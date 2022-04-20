<template>
  <div id="app">
    <div id="nav">
      <div id="homeCollectionBundle">
        <router-link class="home_route" v-bind:to="{ name: 'home' }">Home</router-link>  
        &nbsp;|&nbsp;
        <router-link class="public-collections" v-bind:to="{ name: 'public-collections' }" > Browse Collections</router-link>
      </div>
      <router-link class="logout" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout </router-link>
      <div class="searchContainer">
        <search class="searchBar"/>
      </div>
    </div>
    <router-view />
  </div>
</template>

<script>
import Search from '@/components/Search.vue';


export default {
  components: {
    Search
  }
}
</script>



<style >



@font-face {   
  font-family: 'limelight-regular';
  src: local('limelight-regular'),
  url('./assets/Limelight-Regular.ttf') format("truetype");
}


.home_route, .logout, .public-collections{
  color: honeydew;
  text-decoration: none;
}

.home_route:hover, .logout:hover, .public-collections:hover {
  color: #CC5500;
  text-decoration: underline;
}



.homeCollectionBundle {
  grid-area: homeCollectionBundle;
  
}

.logout {
  grid-area: logout;
  display: flex;
  justify-content: flex-end;
}

.searchBar {
  grid-area: searchBar;
}

.searchContainer {
  display: flex;
  justify-content: center;
}

#nav {
  padding: 10px;
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  grid-template-areas: 
    "homeCollectionBundle searchBar logout";
  font-family: "limelight-regular", serif;
  background-color: rgba(0,0,0,.5);
  align-items: center;
}

button {
  font-family: 'limelight-regular', serif;
  background: honeydew;
  color: black;
  font-weight: bold;
  padding: 8px;
  border-radius: 5px;
}

#app {
  font-family: 'limelight-regular', serif;
}

input {
  background-color:honeydew;
  border-radius: 5px;
}

</style>