<template>
  <div class="app-container">
    <div class="info-box">
      <div class="big-box">
        <img class="big-profile" :src="visitinfo.profileImg" alt="프로필사진입니다." />
      </div>
      <div class="text-box">
        <div class="top-box">
          <div style="font-weight: bold;" >{{ visitinfo.userid }}</div>
          <!-- 팔로우라면 -->
          <div style="font-weight: bold;" v-if="followstore.check">
            <button @click="undoFollow(id, visitId)"  class="followee-btn ">Following</button>
          </div>
          <div v-else>
            <button @click="doFollow(id, visitId)" class="follow-btn">Follow</button>

          </div>
        </div>
        <div class="second-box">
          <div style="font-weight: bold;">POSTS {{ postStore.postmydata.length }}</div>
          

            <div class="tmp" style="font-weight: bold;"  @click="openFollowerModal">FOLLOW &nbsp; 
              <div v-if="visitFollower.length>0">
              {{ visitFollower.length }}
            </div>
            <div v-else>
              0
            </div>
            </div>
          
          <div class="tmp" style="font-weight: bold;" @click="openFollowingModal">FOLLOWING &nbsp;
            
          
            <div v-if=" visitFollowee.length>0">
              {{  visitFollowee.length }}
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
          <!-- 할일 목록 표시 -->
          <div class="todo-container">
            <h5>{{ data.date }}To-dos</h5>
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
          <!-- 목표가 없을 때 표시될 내용 -->
          <img src="/src/resources/imgs/mypage-empty.jpg" alt="noPost">
          <h2>No todos today...</h2>
        </div>
     
      
    </div>

</div>
    <!-- 팔로워 모달 -->
    <Modal v-if="followerModalOpen || followingModalOpen" class="modal" @close="closeModal">
      <div class="modal-content">
        <div v-if="followerModalOpen">
          
          <div v-for="follower in visitFollower" :key="follower.id">
            
            <span @click="goUserpage(follower.fromUserId)" class="info">
              
              <div class="box">
            <img class="profile" :src="follower.profileImg" alt="프로필사진입니다." />
          </div>
          <div>

          {{ follower.fromUserId }}
        </div>
        </span>
          </div>
       
       
        </div>
        <div v-else>
         
          <div v-for="followee in visitFollowee" :key="followee.id">
            <span @click="goUserpage(followee.toUserId)" class="info">
              <div class="box">
            <img class="profile" :src="followee.profileImg" alt="프로필사진입니다." />
          </div>
          <div>{{ followee.toUserId }}</div>
              </span>
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>
  
  <script setup>
  
  import Modal from '@/components/common/Modal.vue';
import { useMoveStore } from '@/stores/pageMove';
import { onMounted, ref } from 'vue';
import { useFollowStore } from '@/stores/follow';
import { useGoalStore } from '@/stores/goal';
import { useTodoStore } from '@/stores/todo';
import { usePostStore } from '@/stores/post';
import axios from 'axios';

const postStore = usePostStore();
const goalstore = useGoalStore();
const todostore = useTodoStore();
const followstore = useFollowStore();
const movestore = useMoveStore();
const id = sessionStorage.getItem('loginUserId');
const visitId = sessionStorage.getItem('lastId');
let visitinfo = '';
let visitFollower = {};
let visitFollowee = {};


const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + today.getDate()).slice(-2);
const dateString = year + '-' + month + '-' + day;

onMounted(async () => {
  await checkFollow(id, visitId);
  await getPostsListByUserId(); 
   await getFollowerdata();
  await getFolloweedata();
  await getUserData();
  await check();
  
  
});

const data = ref({
  date: dateString
});

const getPostsListByUserId = async () => {
  try {
    await postStore.getPostsListByUserId(visitId);
  } catch (error) {
    console.error(error);
  }
};



const checkFollow = async (myid, otherid) => {
  try {
    await followstore.checkFollow(myid, otherid);
  } catch (error) {
    console.error(error);
  }
};

const doFollow = async (myid, otherid) => {
  try {
    await followstore.doFollow(myid, otherid);
    await checkFollow(id, visitId);
  } catch (error) {
    console.error(error);
  }
};

const undoFollow = async (myid, otherid) => {
  try {
    await followstore.undoFollow(myid, otherid);
    await checkFollow(id, visitId);
  } catch (error) {
    console.error(error);
  }
};

const goUserpage = async (gotoid) => {
  if (gotoid === id) {
      await movestore.pageMoveMy(gotoid);
  } else {
      await movestore.pageMove(gotoid);
      
  }
 
};

const check = async () => {
  try {
    await goalstore.getGoalData(visitId, data.value.date);
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
    const response = await axios.get(`http://localhost:8080/user/${visitId}`);
    visitinfo = response.data[0];
  } catch (error) {
    console.error(error);
  }
};

const getFollowerdata = async () => {
  try {
        const response = await axios.get(`http://localhost:8080/follow/follower?toUserId=${visitId}`);
        
       visitFollower = response.data;
     
      } catch (error) {
        console.error(error);
      }
};

const getFolloweedata = async () => {
  try {     
       
        const response = await axios.get(`http://localhost:8080/follow/followee?fromUserId=${visitId}`);
        visitFollowee = response.data;
        
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
</script>





<style scoped>


.app-container{
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-bottom: 20px;
  margin-right:160px ;
  height: 1200px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: 890px;
}
.post-container{
  width: 45%;
  /* margin-right:180px ; */
}

.name{
  margin: 10px;
}
.edit-button {
    background: none;
    border: none;
    cursor: pointer;
    padding: 0;
    font-family: 'Nanum Gothic', sans-serif;
}
.todo-info{
  margin-left: 50px;
  width: 45%;
}


.landing-info{
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 10px;
}


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

.goal-input {
    font-size: 1.5em; 
    width: 100%; 
}


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
.tmp{
  display: flex;
  
}
.todo-container {
    
    max-width: 90%;
    
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.todo-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    border-bottom: 1px solid #ddd;
}

.todo-content {
    flex: 3;
    text-align: left;
}

.name-mbti{
  display: flex;
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

.top-box> div{
  margin: 10px;
}
.second-box{
  display: flex;
  max-width: 5;
}

.second-box > div {
  margin: 10px; 
}

.card-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin: 20px;
}

.card {
  width: 300px;
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
  margin-top: 20px
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
</style>