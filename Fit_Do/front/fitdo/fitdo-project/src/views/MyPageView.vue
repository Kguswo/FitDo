<template>
  <div>
    
    <div v-if="store.userdata[0]" class="total-info-box">
      <div class="info-wrapper">
        <div class="my-info-box">
          <div class="container">
            <div class="my-page-title">
              My Profile
              <button @click="logout" class="logout-button">Log Out</button>
            </div>
            <hr class="divider">
            <!-- 내 정보 보기 -->
            <div class="info-items">
              <div>
            <div class="info-item">
              <div class="info-label">ID :</div>
              <div class="info-value">{{ store.userdata[0].userid }}</div>
            </div>
            <!-- 생년월일, 성별, MBTI -->
            
              
              <div class="info-item">

                <div class="info-label">Name :</div>
                <div class="info-value">{{ store.userdata[0].name }}</div>
              </div>
              <div  class="info-item">
                <div class="info-label">Birth :</div>
                <div class="info-value">{{ store.userdata[0].birth }}</div>
              </div>
              <div  class="info-item">
                <div class="info-label">Gender :</div>
                <div class="info-value">{{ store.userdata[0].gender }}</div>
              </div>
              <div  class="info-item">
                <div class="info-label">Workout Experience Level :</div>

                <div class="info-value">{{ store.userdata[0].mbti }}</div>
              </div>
            </div>
            <div class="profile-box">
              <div class="info-item profile-item">
                <!-- <div class="info-label">프로필사진 :</div> -->
                <div class="box">
                  <img class="profile" :src="store.userdata[0].profileImg" alt="프로필사진입니다.">
                </div>
              </div>
              <div class="profile-upload-box">
                <button @click="openModal" class="profile-upload-button">+</button>
              </div>
            </div>
          </div>
          <div>
            <div v-if="showModal">
              <div class="modal-content">
                <!-- 이미지 업로드 폼 -->
                <input type="file" @change="handleFileChange">
                <!-- 이미지 미리보기 -->

                <div class="modal-content-box">
                  <img class="profile" v-if="imagePreview" :src="imagePreview" alt="미리보기">
                </div>

                <!-- 프로필 사진 업데이트 버튼 -->
                <button @click="updateProfileImage" class="update-button">Update Profile Image</button>
                <!-- 모달 닫기 버튼 -->
                <button @click="closeModal" class="close-button">Cancel</button>
              </div>
            </div>
          </div >
            <!-- 내 정보 수정하기 -->
            <button v-if="checkchage" @click="chageimpo" class="edit-button">Submit Updates</button>
          </div>
        </div>

        <div v-if="!checkchage" class="my-info-box">
          <div class="container">
            <div class="my-page-title">
              Edit Profile
            </div>
            <hr class="divider">
            <div class="info-item">
              <div class="info-label">ID :</div>
              <div class="info-value">{{ store.userdata[0].userid }}</div>
            </div>
            <!-- 이름, 생년월일, 성별, MBTI 수정 폼 -->
            <div class="info-item">

              <label for="name">Name :</label>

              <input type="text" id="name" v-model="newuserdata.name" :placeholder="store.userdata[0].name">
              <span v-if="!newuserdata.name" class="error"></span>
            </div>
            <div class="info-item">
              <label for="birthday">Birth :</label>
              <input type="date" id="birthday" max="2024-05-20" min="1900-01-01" v-model="newuserdata.birth">
              <span v-if="!newuserdata.birth" class="error"></span>
            </div>
            <div class="info-item">
              <label for="gender">Gender :</label>
              <select id="gender" v-model="newuserdata.gender">
                <option value="">Gender</option>
                <option value="male">Male</option>
                <option value="female">Female</option>

              </select>
              <span v-if="!newuserdata.gender" class="error"></span>
            </div>
            <div class="info-item">

              <label for="mbti">Workout Experience Level :</label>
              <select id="mbti" v-model="newuserdata.mbti">
                <option value="">Workout Experience Level</option>

                <option v-for="type in mbtiTypes" :key="type" :value="type">{{ type }}</option>
              </select>
              <span v-if="!newuserdata.mbti" class="error"></span>
            </div>
            <!-- 내 정보 수정 버튼 -->
            <button @click="changMyInfo" class="save-button">Submit</button>
            <button v-if="!checkchage" @click="chageimpocn" class="cancel-button">Cancel</button>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      데이터를 불러오는 중입니다...
    </div>
  </div>
</template>

<script setup>

import { useUserStore } from '@/stores/user';
import { onMounted } from 'vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const store = useUserStore();
const router = useRouter();
const id = sessionStorage.getItem('loginUserId');

const checkchage = ref(true);
const showModal = ref(false); // 모달 상태 변수
const file = ref(null);
const imagePreview = ref(null);

const openModal = () => {
  showModal.value = true;
  
};

const closeModal = () => {
  showModal.value = false;
};

const handleFileChange = (event) => {
  file.value = event.target.files[0];
  const reader = new FileReader();
  reader.onload = (e) => {
    imagePreview.value = e.target.result;
  };
  reader.readAsDataURL(file.value);
};

const updateProfileImage = async () => {
  try {
    const id = sessionStorage.getItem('loginUserId');
    const formData = new FormData();
    formData.append('file', file.value);
    await store.updateUserProfileImage(id, formData);
    closeModal(); // 업로드 후 모달 닫기
  } catch (error) {
    console.error(error);
  }
};



const chageimpo = () => {
  checkchage.value = !checkchage.value;
};

const chageimpocn = () => {
  checkchage.value = !checkchage.value;
};

const mbtiTypes = [
'Beginner', 'Intermediate', 'Expert'
];

const newuserdata = ref({
  birth: '',
  name: '',
  gender: '',
  profileImg: '',
  mbti: ''
});

const changMyInfo = async function() {
  try {
    newuserdata.value.profileImg = store.userdata[0].profileImg
    checkchage.value = !checkchage.value;
    await store.updateUser(newuserdata.value);
  } catch (error) {
    console.error(error);
  }
};

const logout = async () => {
  try {
    await store.logout();
    await sessionStorage.clear();
    
    alert("로그아웃되었습니다.");
    router.push('/');  // 로그아웃 후 로그인 페이지로 리디렉션
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  store.getUserData(id);
});
</script>

<style scoped>
.container {
  margin-top: 20px;
}

.info-wrapper {
  display: flex;
  gap: 20px; 
}

.my-info-box {
  /* border: 1px solid #ccc; */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border-radius: 10px; /* 각 깍기 */
  background-color: #fafafa;
  width: 600px; 
  height: 600px;
}

.my-page-title {
  font-size: 22px;
  font-weight: bold;
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.total-info-box {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
}

.divider {
  margin-top: 20px;
  margin-bottom: 30px; 
}

.info-item {
  display: flex;
  align-items: center; 
  margin-bottom: 15px;
}

.info-label {
  font-weight: bold;
  margin-right: 10px;
  color: #333;
  flex-shrink: 0;
}

.info-value {
  flex-grow: 1;
  color: #666;
}
.info-items{
  display: flex;
  font-size: 18px;
}

.error {
  color: red;
}

.edit-button, .save-button, .cancel-button {
  background-color: #3897f0; 
  color: #fff; 
  border: none; 
  padding: 10px 20px; 
  border-radius: 5px; 
  cursor: pointer;
  font-size: 18px;
  margin-right: 10px; 
}

.logout-button{
  background-color: #fafafa;
  color: #000000;
  border: none; 
  padding: 10px 20px; 
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px;
  border: 1px solid #ccc;
}

.edit-button:hover, .save-button:hover, .cancel-button:hover {
  background-color: #2684e1; 
}

.logout-button:hover{
  background-color: #efefef;
}

.cancel-button {
  background-color: #efefef;
  color: #333;
}

.update-button, .close-button{
  display: flex;
  justify-content: center;
  align-items: center;
  margin : 10px;
  width: 100%;
  background-color: #efefef; 
  color: #333;
  border-radius: 5px;;
}

.cancel-button:hover {
  background-color: #ddd;
}

.profile-item {
  justify-content: space-between;
}

.box {
  width: 160px;
  height: 160px; 
  border-radius: 50%; 
  overflow: hidden;
  margin-right: 10px;
}

.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.profile-box{
  display: flex;
  justify-content: end;
  align-items: end;
  position: relative;
}

.profile-upload-box{
  position: absolute;
  bottom: 0;
  right: 0;
}

.profile-upload-button {
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 50%;
  width: 38px;
  height: 38px;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  font-size: 24px;
  cursor: pointer;
  outline: none;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.profile-upload-button:hover {
    background-color: #0056b3;
}

.modal-content{
  font-size: 20px;
}
.modal-content-box{
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
}

.submitupdate-button{
  width: 300px;
  display: flex;
  justify-content: end;
  align-items: end;
}

.container input, .container select {
  border-radius: 5px;
  background-color: #efe8e8;
  border: none;
}
</style>


