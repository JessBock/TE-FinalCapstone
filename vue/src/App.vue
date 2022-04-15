<template>
  <div id="app">
    <div id="nav">
      <router-link class="home" v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <!--<router-link v-bind:to="www.google.com">Search</router-link> |-->
      <router-link class="user" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">User</router-link>
      <!--<router-link v-bind:to="{ name: 'logout' }" v-else>Login</router-link> -->
      <search />
    </div>
    <router-view />
  </div>
</template>

<style scoped>
@font-face {
  /*font-family: 'vinyl-regular';
  src: local('vinyl-regular'), 
    url('./assets/Vinyl-Regular.ttf') format("truetype");
  font-family: 'limelight-regular';
  src: local('limelight-regular'), 
    url('./assets/Limelight-Regular.ttf') format("truetype");
  font-family: 'carosello-regular';
  src: local('carosello-regular'), 
    url('./assets/Carosello-Regular.ttf') format("truetype");*/
  font-family: 'frontage-condensed-outline';
  src: local('frontage-condensed-outline'), 
    url('./assets/FrontageCondensed-Outline.ttf') format("truetype");
}

.home, .user {
  color: honeydew;
}


</style>
<script>
import Search from '@/components/Search.vue';


export default {
  components: {
    Search
  }
 
}
</script>



<style>
button {
  font-family:'Franklin Gothic Medium', serif;
}

</style>

