<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <!--<router-link v-bind:to="www.google.com">Search</router-link> |-->
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">User</router-link>
      <!--<router-link v-bind:to="{ name: 'logout' }" v-else>Login</router-link>-->
    </div>
    <router-view />
  </div>
</template>
