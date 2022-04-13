<template>

  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
   
      <div
        class="alert alert-danger"
        role="alert"
        v-if="invalidCredentials"
      >Invalid username and password!</div>
      <div
        class="alert alert-success"
        role="alert"
        v-if="this.$route.query.registration"
      >Thank you for registering, please sign in.</div>
      <label for="username" class="sr-only">Username</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      <label for="password" class="sr-only">Password</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <router-link class="routerlink" :to="{ name: 'register' }">Need an account?</router-link>
      <button type="submit">Sign in</button>
    </form>
    <record-album></record-album>
  </div>
</template>

<script>
import RecordAlbum from '../components/RecordAlbum.vue';
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {RecordAlbum},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  },
};
</script>
<style scoped>

#login {
  display: grid;
  margin-top: 200px;
  justify-items: end;
  text-decoration-color: honeydew;
  font-family: vinyl-regular;

  
}

label, .routerlink, button {
  padding: 10px;
}


form {
  display:flex;
    flex-direction: column;
    width:10vw;
    justify-content: right;
    align-content: center;
    flex-basis: 45%; 
    animation: fadeInAnimation ease 15s;
    animation-iteration-count: 1;
    animation-fill-mode: forwards;
    
}
 
@keyframes fadeInAnimation {
    0% {
        opacity: 0;
    }
    100% {
        opacity: 1;
     }
}


</style>
