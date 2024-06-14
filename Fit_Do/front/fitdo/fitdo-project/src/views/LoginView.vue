<template>
  <div class="login-page">
    <div class="login-container">
      <div class="logo-container">
        <img src="/src/resources/imgs/FitDo_Login_LogoImg.jpg" alt="로고이미지" class="logo-image">
      </div>
      <div class="form-container">
        <img src="/src/resources/imgs/Fit Do Text Logo.png" alt="로고이미지" class="text-logo">
        <div class="input-wrapper">
          <div class="login-wrapper flex-center">
            <input
              class="login-input"
              type="text"
              v-model="data.user_id"
              @input="updatePlaceholder('user_id')"
            />
            <span class="placeholder-text" :class="{ 'active': placeholders.user_id || data.user_id }">ID</span>
          </div>
          <div class="login-wrapper flex-center">
            <input
              class="login-input"
              type="password"
              v-model="data.user_pw"
              @input="updatePlaceholder('user_pw')"
            />
            <span class="placeholder-text" :class="{ 'active': placeholders.user_pw || data.user_pw }">Password</span>
          </div>
          <div class="flex-center button-wrapper">
            <button class="login-button" @click="fnLogin">Login</button>
          </div>
        </div>
        <div class="signup-text">
          <p>Don't have an account? 
            <RouterLink to="/SignUp" class="id" style="font-weight: bold;">Sign Up</RouterLink>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';
import router from '@/router';

const store = useUserStore();
const data = ref({
  user_id: '',
  user_pw: ''
});

const login = function() {
  store.userlogin(data.value.user_id, data.value.user_pw);
  store.getUserData(data.value.user_id);
};

const fnLogin = async () => {
  if (data.value.user_id === '') {
    alert('Please enter your ID.');
    return;
  }

  if (data.value.user_pw === '') {
    alert('Please enter your password.');
    return;
  }
  login();
};

const placeholders = ref({
  user_id: false,
  user_pw: false
});

const updatePlaceholder = (field) => {
  placeholders.value[field] = !!data.value[field];
};
</script>

<style scoped>
/* CSS */
html, body {
  height: 100%;
  margin: 0;
  font-family: Arial, sans-serif;
}

body {
  background-color: #f0f0f0;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #fdfce0;
  backdrop-filter: blur(4px);
}

.login-container {
  display: flex;
  background-color: white;
  width: 1100px;
  height: 700px;
  border: 1px solid lightgray;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.logo-container {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo-image {
  margin-left: 30px;
  height: 360px;
  border-radius: 10px;
}

.text-logo {
  width: 130px;
  height: 60px;
  max-width: 400px;
  display: block;
  margin-bottom: 20px;
  border-radius: 10px;
}


.form-container {
  flex: 1;
  padding-left : 15px;
  padding-right : 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  max-width: calc(100% - 20px);
  width: 100%;
  margin-left: 20px;
}

.input-wrapper {
  margin-top: 30px;
  margin-bottom: 20px;
  width: 83%;
}

.login-wrapper {
  margin-bottom: 20px;
  width: 100%;
  position: relative;
}

.login-input {
  font-size: 14px;
  width: 100%;
  height: 40px;
  background-color: rgba(0, 0, 0, 0.035);
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  padding-left: 10px;
}

.login-button {
  width: 100%;
  font-size: 15px;
  color: white;
  font-weight: bold;
  background-color: #3c9aff;
  border: none;
  border-radius: 5px;
  padding: 9px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #007bff;
}

.button-wrapper {
  border: none;
  margin-top: 20px;
  width: 100%;
}

.signup-text {
  margin-top: 20px;
  font-size: 14px;
  text-align: center;
}

.signup-text a {
  color: #28a745;
  cursor: pointer;
  text-decoration: none;
}

.signup-text a:hover {
  text-decoration: underline;
}

.placeholder-text {
  position: absolute;
  top: 50%;
  left: 12px;
  transform: translateY(-50%);
  pointer-events: none;
  transition: all 0.3s ease;
  color: #999;
  font-size: 0.9em;
}

.active {
  transform: translateY(-120%);
  font-size: 0.7em;
}
</style>
