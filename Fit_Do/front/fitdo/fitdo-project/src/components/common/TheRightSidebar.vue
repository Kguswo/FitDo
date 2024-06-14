<template>
  <aside class="right-sidebar">
    <!-- 여기에 오른쪽 사이드바의 내용을 추가하세요 -->
    <div v-if="store.userdata[0]" @click="gouserpage(id)" class="nav-item user-profile">
      <div class="nav-icon-container">
        <div class="box">
          <img class="profile" :src="store.userdata[0].profileImg" alt="프로필사진입니다." />
        </div>
      </div>
      <span class="nav-text">
        <div style="font-weight: 600">
          {{ store.userdata[0].userid }}
        </div>
        <div>
          {{ store.userdata[0].name }}
        </div>
      </span>
    </div>
    <br>
    <p style="color: gray; font-weight: 600;">Suggested for you</p>
    <div class="recomend-box">
      <div v-for="reuser in store.recomendUser" :key="reuser.userid" class="recomend-smallbox">
        <span class="author" @click="goMypage(reuser.userid)">
          <div class="box">
            <img class="profile" :src="reuser.profileImg" alt="프로필사진입니다." />
          </div>
          {{ reuser.userid }}
        </span>
        <button v-if="checkStatus[reuser.userid]" @click="doFollow(reuser.userid)" class="follow-btn">Follow</button>
        <button v-else @click="undoFollow(reuser.userid)" class="followee-btn ">Following</button>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { useMoveStore } from '@/stores/pageMove';
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { usePostStore } from '@/stores/post';
import { useFollowStore } from '@/stores/follow';

const followstore = useFollowStore();
const postStore = usePostStore();
const store = useUserStore();
const movestore = useMoveStore();

const id = sessionStorage.getItem('loginUserId');
const checkStatus = ref({}); // 개별 팔로우 상태를 저장하는 객체

onMounted(async () => {
  await store.getUserData(id);
  await sameMbtiNotFollowing(id, store.userdata[0].mbti);
  store.recomendUser = getRandomRecomendUsers(store.recomendUser, 6);
  store.recomendUser.forEach(user => {
    checkStatus.value[user.userid] = true; // 초기 상태는 모두 팔로우 가능한 상태로 설정
  });
});

const goMypage = async (gotoid) => {
  await movestore.pageMove(gotoid);
};

const gouserpage = async (gotoid) => {
  await movestore.pageMoveMy(gotoid);
};

const sameMbtiNotFollowing = async (id, mbti) => {
  await store.sameMbtiNotFollowing(id, mbti);
};

const getRandomRecomendUsers = (users, num) => {
  const shuffled = [...users].sort(() => 0.5 - Math.random());
  return shuffled.slice(0, num);
};

const doFollow = async (otherid) => {
  try {
    await followstore.doFollow(id, otherid);
    checkStatus.value[otherid] = false;
  } catch (error) {
    console.error(error);
  }
};

const undoFollow = async (otherid) => {
  try {
    await followstore.undoFollow(id, otherid);
    checkStatus.value[otherid] = true;
  } catch (error) {
    console.error(error);
  }
};
</script>

<style scoped>
.nav-text {
  display: flex;
  flex-direction: column;
  font-size: 14px;
}

.user-profile {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.user-profile:hover {
  transform: scale(1.02);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.right-sidebar {
  position: fixed;
  top: 0;
  right: 0px;
  width: 400px;
  /* width: 20%; */
  height: 100%;
  padding: 20px;
  margin-top: 50px;
  /* margin-right: 1%; */
}

.recomend-box {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.recomend-smallbox { 
  width: 85%;
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  justify-content: space-between; /* 이 줄을 추가 */
}

.recomend-smallbox:hover {
  transform: scale(1.02);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
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

.box:hover .profile {
  transform: scale(1.1);
}

.author {
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 600;
  color: #333;
  cursor: pointer;
}

.author:hover {
  text-decoration: underline;
}

.follow-btn {
  margin-left: auto; /* 이 줄을 추가 */
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
  margin-left: auto; /* 이 줄을 추가 */
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

