<template>
  
 <aside class="left-sidebar">
      
    <nav>
     
      <div>
        <img class="logo" src="/src/resources/imgs/Fit Do Text Logo.png" alt="로고">
      </div>
      <br>
      <div class="nav-item" @click="showModal = true">
        <div class="nav-icon-container">
          <span class="bi bi-search nav-icon"></span>
        </div>
        <span class="nav-text">Search</span>
      </div>
      <RouterLink to="/Home/Main" class="nav-item">
        <div class="nav-icon-container">
          <div class="bi bi-house-fill fs-1 nav-icon"></div>
        </div>
        <div class="nav-text">Home</div>
      </RouterLink>
      <RouterLink to="/Home/Todo" class="nav-item">
        <div class="nav-icon-container">
          <div class="bi bi-pencil-square fs-1 nav-icon"></div>
        </div>
        <span class="nav-text">To-do</span>
      </RouterLink>
      <RouterLink to="/Home/Search" class="nav-item">
        <div class="nav-icon-container">
          <div class="bi bi-compass fs-1 nav-icon"></div>
        </div>
        <span class="nav-text">Explore</span>
      </RouterLink>
      <div v-if="store.userdata[0]" @click="goMypage(id)" class="nav-item user-profile">
        <div class="nav-icon-container">
          <div class="box">
            <img class="profile" :src="store.userdata[0].profileImg" alt="프로필사진입니다." />
          </div>
        </div>
        <span class="nav-text">Profile</span>
      </div>
    </nav>
  </aside>
  <div class="content">
    <hr />
    <SearchModal :show="showModal" @close="showModal = false" />
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import { RouterLink, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useMoveStore } from '@/stores/pageMove';
import SearchModal from '@/components/modal/SearchModal.vue';

const store = useUserStore();
const movestore = useMoveStore();
const id = sessionStorage.getItem('loginUserId');
const router = useRouter();
const showModal = ref(false);

onMounted(() => {
  store.getUserData(id);
});

const goMypage = async (gotoid) => {
  await movestore.pageMoveMy(gotoid);
};
</script>


<style scoped>
.logo {
  margin-top: 30px;
  margin-left: 20px;
  width: auto;
  height: 50px;
  margin-top: 30px;
  margin-left: 20px;
}
.left-sidebar {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 250px;
  height: 100%;
  background-color: #fefefe;
  padding: 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);

}

nav {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  width: 100%;
}

.nav-item {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  text-decoration: none;
}

.nav-item:hover {
  background-color: #e9ecef;
  transform: scale(1.05);
}

.nav-icon-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
}

.nav-icon {
  font-size: 1.5rem;
  color: #333;
  transition: color 0.3s;
}

.nav-text {
  margin-left: 10px;
  font-size: 1rem;
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  color: #333;
}

.user-profile {
  flex-direction: row;
}

.box {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
}

.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

</style>
<style>
.instagram-home-button {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    background-color: #ffffff;
    border: 1px solid #dbdbdb;
    border-radius: 50%; 
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.instagram-home-button:hover {
    background-color: #f0f0f0;
}

.instagram-home-button svg {
    fill: #262626;
}
</style>