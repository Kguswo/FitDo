<template>
  <div class="app-container">
    <div class="info-box">
      <div class="big-box">
        <img class="big-profile" :src="visitinfo.profileImg" alt="프로필사진입니다." />
      </div>
      <div class="text-box">
        <div class="top-box">
          <div style="font-weight: bold;">{{ visitinfo.userid }}</div>
          <div @click="goMypage" class="bi bi-gear"></div>
        </div>
        <div class="second-box">
          <div style="font-weight: bold;" >POSTS {{ postStore.postmydata.length }}</div>
         
          <div class =tmp style="font-weight: bold;" @click="openFollowerModal">FOLLOW &nbsp; 
            <div v-if="visitFollower.length>0">
            {{ visitFollower.length }}
          </div>
          <div v-else>
            0
          </div>
          </div>
          <div class =tmp  style="font-weight: bold;" @click="openFollowingModal">FOLLOWING &nbsp;
            <div v-if=" visitFollowee.length">
            {{ visitFollowee.length }}
          </div>
          <div v-else>
          0
          </div>
          </div>
        </div>
        <div class="name-mbti">
        <div style="font-weight: bold;" class="name">{{ visitinfo.name }}</div>
        <div style="font-weight: bold; color: brown;" class="name">{{ visitinfo.mbti }}</div>      
      </div>
      </div>
    </div>
    <hr>
    <div class="landing-info">
      <div class="post-container">

          <div v-for="postItem in postStore.postmydata" :key="postItem.id" class="card-container">
          <div class="card">
            <div class="image-container">
              <img :src="postItem.postImg" alt="게시물 이미지" @click="datechange(postItem.postRegDate)" />
            </div>
            <div class="description-container">
              <p style="display: flex;">{{ postItem.postRegDate }} Post &emsp;&emsp;&emsp;&emsp;&emsp;&emsp; ❤ {{ postItem.postLike }} </p>
            </div>
          </div>

        </div>
      </div>
      <div class="todo-info">
        <h4>To-do List</h4>
        <hr />
        <div v-if="goalstore.goaldata && goalstore.goaldata.length > 0" class="goal-container">
          <h3><button class="goal-button">{{ goalstore.goaldata[0].goalContent }}</button></h3>
          <div class="todo-container" style="width: 400px;">
            <h5>{{ data.date }} To-dos</h5>
            <div v-for="todo in todostore.tododata" :key="todo.todoId" class="todo-item">
              <button class="todo-button">{{ todo.todoContent }}</button>
              <div class="todo-state">
                <button v-if="todo.todoState" class="bi bi-check-circle-fill todo-button-plus"></button>
                <button v-else class="bi bi-x-circle-fill todo-button-fail"></button>
              </div>
            </div>
          </div>
        </div>
        <div v-else style="margin-left: 20%;">
          <img src="/src/resources/imgs/mypage-empty.jpg" alt="투두페이지이미지" class="notodo-img"><br>
          <h2>No todos today...</h2>
        </div>
      </div>
    </div>
    <Modal v-if="followerModalOpen || followingModalOpen" class="modal" @close="closeModal">
      <div class="modal-content">
        <div v-if="followerModalOpen" class="follow-box">
          <div v-for="follower in visitFollower" :key="follower.id">
            <span @click="goUserpage(follower.fromUserId)" class="info">
              <div class="box">
            <img class="profile" :src="follower.profileImg" alt="프로필사진입니다." />
          </div>
          <div>
              {{ follower.fromUserId }}
            </div></span>
          </div>
        </div>
        <div v-else class="follow-box">
          
          <div v-for="followee in visitFollowee" :key="followee.id">

            <div class="folloing-box">
            <span @click="goUserpage(followee.toUserId)" class="big-info">
              <div class="info">
              <div class="box">
            <img class="profile" :src="followee.profileImg" alt="프로필사진입니다." />
          </div>
          <div>
              {{ followee.toUserId }}
            </div>
          </div>
        </span>
              <div class="follow-by">
              <button v-if="checkStatus[followee.toUserId]" @click="undoFollow(followee.toUserId)" class="followee-btn">팔로잉</button>
              <button v-else @click="doFollow(followee.toUserId)" class="follow-btn">팔로우</button>
            </div>
          </div>
             
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

  
<script setup>
import Modal from '@/components/common/Modal.vue';
import { onMounted, ref } from 'vue';
import { useGoalStore } from '@/stores/goal';
import { useTodoStore } from '@/stores/todo';
import { usePostStore } from '@/stores/post';
import router from '@/router'
import axios from 'axios';
import { useMoveStore } from '@/stores/pageMove';
import { useFollowStore } from '@/stores/follow';
import { useUserStore } from '@/stores/user';


const followstore = useFollowStore();
const checkStatus = ref({});
const movestore = useMoveStore();
const postStore = usePostStore();
const store = useUserStore();

const goalstore = useGoalStore();
const todostore = useTodoStore();
const id = sessionStorage.getItem('loginUserId');

let visitinfo = '';
let visitFollower = {};
let visitFollowee = {};

const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + today.getDate()).slice(-2);
const dateString = year + '-' + month + '-' + day;

onMounted(async () => {
  await getPostsListByUserId(); 
  await store.getUserData(id);
  await getFollowerdata();
  await getFolloweedata();
  await getUserData();
  await check();
  await populateCheckStatus();
});

const data = ref({
  date: dateString
});

const getPostsListByUserId = async () => {
  try {
    await postStore.getPostsListByUserId(id);
  } catch (error) {
    console.error(error);
  }
};

const goMypage = function() {
  router.push({ name: 'MyPage' });
}

const check = async () => {
  try {
    await goalstore.getGoalData(id, data.value.date);
    await recheck(goalstore.goaldata[0].goalId);
  } catch (error) {
    // console.error(error);
  }
};

const recheck = async (goalid) => {
  try {
    await todostore.getTodoData(goalid);
  } catch (error) {
    console.error(error);
  }
};

const datechange = async (changedate) => {
  data.value.date = changedate;
  await check();
};

const getUserData = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/user/${id}`);
    visitinfo = response.data[0];
  } catch (error) {
    console.error(error);
  }
};

const getFollowerdata = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/follow/follower?toUserId=${id}`);
    visitFollower = response.data;
  } catch (error) {
    console.error(error);
  }
};

const getFolloweedata = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/follow/followee?fromUserId=${id}`);
    visitFollowee = response.data;
  } catch (error) {
    console.error(error);
  }
};

const populateCheckStatus = () => {
  visitFollowee.forEach(followee => {
    checkStatus.value[followee.toUserId] = true; // Assuming true means the user is following
  });
};

const doFollow = async (userId) => {
 
  try {
    await followstore.doFollow(id, userId);
    checkStatus.value[userId] = true;
  } catch (error) {
    console.error(error);
  }
};

const undoFollow = async (userId) => {
  console.log(userId)
  try {
    await followstore.undoFollow(id, userId);
    checkStatus.value[userId] = false;
    
  } catch (error) {
    console.error(error);
  }
};

// 모달 상태를 저장할 변수
const followerModalOpen = ref(false);
const followingModalOpen = ref(false);

// 모달 열기 함수
const openFollowerModal = () => {
  followerModalOpen.value = true;
};

const openFollowingModal = () => {
  followingModalOpen.value = true;
};

const closeModal = () => {
  followerModalOpen.value = false;
  followingModalOpen.value = false;
};

const goUserpage = async (gotoid) => {
  if (gotoid === id) {
      await movestore.pageMoveMy(gotoid);
  } else {
      await movestore.pageMove(gotoid);
  }
};
</script>







<style scoped>
/* 수정 버튼 스타일 */

.app-container{
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-bottom: 20px;
  margin-right: 150px;
  height: 900px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: 900px;
  /* margin-left: 60px; */
}

.folloing-box{
  justify-content: space-between;
  display: flex;
}
.edit-button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 0;
    font-family: 'Nanum Gothic', sans-serif;
}
.follow-by{
  margin-right: 150px;
}
/* 할일 버튼 스타일 */
.todo-button {
    
    background: none;
    border: none;
    cursor: pointer;
    font-family: 'Nanum Gothic', sans-serif;
    font-size: 1.2em; 
}

.todo-button-plus{
    color: #4CAF50;
    background: none;
    border: none;
    cursor: pointer;
    font-family: 'Nanum Gothic', sans-serif;
    font-size: 1.2em; 
}

.todo-button-fail{
    color: red;
    background: none;
    border: none;
    cursor: pointer;
    font-family: 'Nanum Gothic', sans-serif;
    font-size: 1.2em; 
}

.info{
  display: flex;
  
}

/* 삭제 버튼 스타일 */
.delete-button {
    background: none;
    border: none;
    cursor: pointer;
    font-family: 'Nanum Gothic', sans-serif;
    color: red; 
}

.goal-button {
    background: none;
    border: none;
    cursor: pointer;
    font-family: 'Nanum Gothic', sans-serif;
    color: inherit;
}
.big-info{
  display: flex;
  justify-content: space-between;
}
.goal-input {
    font-size: 1.5em;
    width: 100%; 
}

/* 할일 목록 박스 스타일 */
.goal-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 20px auto;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}


.todo-container {
    
    max-width: 90%;
    
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.landing-info{
  display: flex;
  justify-content: space-between;
}

.todo-item {
  
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}


.content {
    flex: 3;
    text-align: left;
}
.todo-info{
  margin-left: 50px;
  width: 45%;
  /* margin-right:40px ; */
}
.todo-input {
    width: 100%;
    font-size: 1.2em;
}

.todo-state {
    flex: 1;
    text-align: right;
}

/* 할일 추가 양식 스타일 */
.add-todo-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 20px;
}

.new-todo-input {
    width: 100%;
    max-width: 800px;
    font-size: 1.2em;
    margin-bottom: 10px;
}

.tmp{
  display: flex;
}



.post-container{
  width: 45%;
  /* margin-left:60px ; */
}
.add-button {
    font-size: 1.2em;
    cursor: pointer;
    background-color: #4CAF50;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
}

.cancle-button {
    font-size: 1.2em;
    cursor: pointer;
    background-color: red;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
}

/* 포스트 가능 여부 버튼 스타일 */
.post-button {
    width: 100%;
    max-width: 600px;
    padding: 15px;
    font-size: 1.2em;
    background-color: #0616eb;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    text-align: center;
    margin-top: 20px;
}

.landing-info{
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 10px;
}
img{
  width: 200px;
  height: 200px;
}
.name{
  margin: 10px;
}
.info-box{
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin:10px
}

.text-box{
  display: flex;
  flex-direction: column;
}
.top-box{
  font-size: 26px;
  display: flex;
  justify-content: space-between;
}
.second-box{
  display: flex;
  max-width: 5;
}

.card-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin: 20px;
}

.card {
  width: 300px; /* 카드의 너비 조정 */
  border: 1px solid #ccc;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.image-container {
  height: 200px;
  overflow: hidden;
}

.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.description-container {
  padding: 10px;
}
.modal-content{
  margin-top: 20px;
  display: flex;
  
}

.box {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 10px;
  transition: transform 0.2s;
}

.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.second-box > div {
  margin: 10px; 
}
.top-box> div{
  margin: 10px;
}

.follow-btn {
  margin-left: auto; 
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

.follow-btn:hover {
  background-color: #0056b3;
}
.name-mbti{
  display: flex;
}

.followee-btn {
  margin-left: auto;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: #9ba1a3;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s;
}

.followee-btnn:hover {
  background-color: #97989a;
}
.big-box {
  margin-right: 30px;
  margin-left: 30px;
  margin-top: 10px;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
}

.big-profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>



