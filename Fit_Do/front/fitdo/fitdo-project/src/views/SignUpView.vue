<template>
    <div class="signup-form">
      <div class="logo-img">
        <img src="/src/resources/imgs/Fit Do Text Logo.png" alt="로고이미지" class="text-logo">
      </div>
      <div class="signup-detail">
        <p>Sign up to freely view other people's photos and workout to-dos!</p>
      </div>
      <fieldset>
        <div class="form-group">
          <label for="id"></label>
          <div class="input-container">
            <input
              type="text"
              id="id"
              v-model="user.userid"
              class="input-field"
              @input="updatePlaceholder('id')"
            >
            <span class="placeholder-text" :class="{ 'active': placeholders.id || user.userid }">ID</span>
          </div>
          <!-- <span v-if="!user.userid" class="error">Id를 입력하세요.</span> -->
        </div>
        <div class="form-group">
          <label for="password"></label>
          <div class="input-container">
            <input
              :type="showPassword && user.password !== '' ? 'text' : 'password'"
              id="password"
              v-model="user.password"
              class="input-field"
              @input="updatePlaceholder('password')"
            >
            <span class="placeholder-text" :class="{ 'active': placeholders.password }">Password</span>
            <button v-if="user.password !== ''" class="show-password-btn" @click="toggleShowButtonVisibility"> <!-- 수정 -->
          <span v-if="!showPassword">Show</span>
          <span v-else>Hide</span>
        </button>        
          </div>
          <!-- <span v-if="!user.password" class="error">Password를 입력하세요.</span> -->
        </div>
        <div class="form-group">
          <label for="confirmPassword"></label>
          <div class="input-container">
            <input
              type="password"
              id="confirmPassword"
              v-model="confirmPassword"
              class="input-field"
              @input="updatePlaceholder('confirmPassword')"
            >
            <span class="placeholder-text" :class="{ 'active': placeholders.confirmPassword }">Confirm Password</span>
          </div>
          <!-- <span v-if="!confirmPassword" class="error">비밀번호를 확인하세요.</span> -->
          <span v-if="user.password !== confirmPassword" class="error">비밀번호가 일치하지 않습니다.</span>
        </div>
        <div class="form-group">
          <label for="name"></label>
          <div class="input-container">
            <input
              type="text"
              id="name"
              v-model="user.name"
              class="input-field"
              @input="updatePlaceholder('name')"
            >
            <span class="placeholder-text" :class="{ 'active': placeholders.name }">Name</span>
          </div>
          <!-- <span v-if="!user.name" class="error">이름을 입력하세요.</span> -->
        </div>
        <div class="form-group">
          <label for="gender"></label>
          <select id="gender" v-model="user.gender" class="input-field">
            <option value="">Gender</option>
            <option value="male">Male</option>
            <option value="female">Female</option>
          </select>
          <!-- <span v-if="!user.gender" class="error">성별을 선택하세요.</span> -->
        </div>
        <div class="form-group">
          <label for="birthday"></label>
          <input type="date" id="birthday" max="2024-05-24" min="1900-01-01" v-model="user.birth" class="input-field">
          <!-- <span v-if="!user.birth" class="error">생년월일을 입력하세요.</span> -->
        </div>
        <div class="form-group">
          <label for="mbti"></label>
          <select id="mbti" v-model="user.mbti" class="input-field">
            <option value="">Workout Experience Level</option>
            <option v-for="type in mbtiTypes" :key="type" :value="type">{{ type }}</option>
          </select>
          <!-- <span v-if="!user.mbti" class="error">MBTI를 선택하세요.</span> -->
        </div>
        <div class="form-group">
          <button @click="validateAndSignup" class="submit-btn">Sign Up</button>
        </div>
      </fieldset>
    </div>
  </template>
  
  <style scoped>
  /* CSS */
  .logo-img {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
  }
  
  .text-logo {
    margin-top: 20px;
    margin-bottom: 15px;
    max-width: 33%;
    height: auto; 
  }
  
  .signup-form {
    max-width: 450px;
    margin: 20px auto;
    margin-top: 5%;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    background-color: white;
  }

  .signup-detail {
    margin-left: 40px;
    margin-right: 40px;
    margin-bottom: 30px;
    text-align: center;
    font-weight: 600;
    color: #696969;
  }
  
  .form-group {
    margin-left: 25px;
    margin-right: 25px;
    margin-bottom: 7px;
  }
  
  label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
  }
  
  input[type="text"],
  input[type="password"],
  input[type="date"],
  select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
  }
  
  .error {
    color: red;
    font-size: 12px;
    margin-left: 10px;
    display: block;
  }
  
  .submit-btn {
    margin-top : 30px;
    margin-bottom: 40px;
    padding: 10px 0;
    width : 100%;
    background-color: #99ccff;
    color: #fff;
    font-weight: bold;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .submit-btn:hover {
    background-color: #007bff;
  }
  
  .input-container {
    position: relative;
    margin-bottom: 10px;
  }
  .show-password-btn {
    position: absolute;
    top: 0;
    right: 0;
    padding: 10px 10px;
    background-color: transparent;
    border: none;
    cursor: pointer;
    font-weight: bold;
  }
  .show-password-btn:hover {
    color: #868585;
  }

  .input-field {
    background-color: rgba(0, 0, 0, 0.035);
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
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

  .select-field {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    color: #999;
  }   
  .select-field option {
    color: #333;
  }

  </style>
  
  <script setup>
  import { ref, watch } from 'vue';
  import { useUserStore } from '@/stores/user';
  
  const store = useUserStore();
  
  const today = new Date();
  const year = today.getFullYear();
  const month = ('0' + (today.getMonth() + 1)).slice(-2);
  const day = ('0' + today.getDate()).slice(-2);
  const dateString = `${year}-${month}-${day}`;


  const user = ref({
    userid: '',
    password: '',
    birth: dateString,
    name: '',
    gender: '',
    profileImg: 'https://fit-do.s3.ap-southeast-2.amazonaws.com/Nomal_profile.jpg',
    mbti: ''
  });
  
  const confirmPassword = ref('');
  
  const mbtiTypes = [
    'Beginner', 'Intermediate', 'Expert'
  ];
  
  const placeholders = ref({
    id: false,
    password: false,
    confirmPassword: false,
    name: false
  });
  
  const updatePlaceholder = (field) => {
    placeholders.value[field] = !!user.value[field] || (field === 'confirmPassword' && !!confirmPassword.value);
  };
  
  watch(user.value, (newVal) => {
    for (const key in placeholders.value) {
      if (Object.prototype.hasOwnProperty.call(newVal, key)) {
        placeholders.value[key] = !!newVal[key];
      }
    }
  });
  
  watch(confirmPassword, (newVal) => {
    placeholders.value.confirmPassword = !!newVal;
  });
  
  const validateAndSignup = () => {
    const errors = [];
  
    if (!user.value.userid) errors.push('Id');
    if (!user.value.password) errors.push('Password');
    if (!confirmPassword.value) errors.push('Confirm Password');
    if (user.value.password !== confirmPassword.value) errors.push('비밀번호 확인');
    if (!user.value.name) errors.push('Name');
    if (!user.value.gender) errors.push('Gender');
    if (!user.value.birth) errors.push('Birthday');
    if (!user.value.mbti) errors.push('MBTI');
  
    if (errors.length > 0) {
      alert(errors.join('을(를) 입력하세요.\n'));
      return;
    }
  
    store.createUser(user.value);
  
    // 중복확인 하는 코드 근데 아직 안됨
    const userIdExists = ref(false);
    
    const resetUserIdExists = () => {
      userIdExists.value = false;
    };
  };
  const toggleShowButtonVisibility = () => {
    showPassword.value = !showPassword.value;
  };

  const showPassword = ref(false);
  
  </script>
  