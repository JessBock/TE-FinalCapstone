<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
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
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <div class= "premium_checkbox">
        <input 
          type="checkbox" 
          id="premium_user" 
          class="form-control"
          name="premium_user"
          v-model="user.user_premium"     
        />
        <label for="premium_user" class="premium_selector"> Premium user </label>

      </div>

      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
      <router-link class="login-query" :to="{ name: 'login' }">Have an account?</router-link>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
        user_premium: false,
        user_description: '',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    premium() {
      this.user.user_premium = !this.user.user_premium;
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>

.form-register {
    padding-top: 153px;
    width: 600px;
    height: 600px;
    background-image: url('../assets/logo.png');
    background-size: 70%;
    background-repeat: no-repeat;
    background-position: center;
    font-family: "limelight-regular", serif;
}


.login-query {
  margin-top: 30px;
  color: honeydew;
  text-align: center;
  padding: 10px;
  font-family: "limelight-regular", serif;
  text-decoration: none;
}

.login-query:hover {
  color: #CC5500;
  text-decoration: underline;
}

body, h1 {
color: honeydew;
text-align: center;
font-family: "limelight-regular", serif;
}

#register {
  display: grid;
  margin-top: 200px;
  justify-items: center;

}

label, .routerlink {
  padding: 10px;
  color: honeydew;
}

button {
  margin-top: 5px;
}

form {
  display:flex;
    flex-direction: column;
    height: 35vh;
    width:auto;
    flex-basis: 45%;
    border-style: double;
    border-radius: 50%; 
    background-color: black;
    font-family: "vinyl-regular", "alt-retro-light", "carosello-regular", "frontage-condensed-outline", serif;
    
}
.premium_checkbox {
  padding: 5px;
  color: honeydew;
}

input {
  background-color: honeydew;
}
</style>
