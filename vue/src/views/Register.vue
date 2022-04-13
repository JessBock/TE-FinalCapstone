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
      <label for="premium_user" class="premium_selector"> Premium user </label>
      <input 
        type="checkbox" 
        id="premium_user" 
        class="form-control"
        name="premium_user"
        v-model="user.user_premium" 
        vm.user.user_premium = 'true'
      
        />

      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </form>
    <router-link class="login-query" :to="{ name: 'login' }">Have an account?</router-link>
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
    width: 20vw;
    background-image: url('../assets/Led-Zeppelin-Clip.jpg'); 
    background-size: 100%;
    background-repeat: no-repeat;
    background-position: center;
    
}

.login-query {
  color: whitesmoke;
  text-align: center;
  padding: 10px;
}
body, h1 {
color: honeydew;
text-align: center;
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
    width:30vw;
    flex-basis: 45%;
    border-style: double;
    border-radius: 100px; 
    
}
</style>
