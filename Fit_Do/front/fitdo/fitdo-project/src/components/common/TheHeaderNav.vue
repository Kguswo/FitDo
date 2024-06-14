<template>
  <header class="header">
    <div class="head-box">
      <div class="followees">
        <div v-for="followee in randomFollowees" :key="followee.id">
          <span class="author" @click="goMypage(followee.toUserId)">
            <div class="postuser">
              <div class="box">
                <img class="profile" :src="followee.profileImg" alt="프로필사진입니다." />
              </div>
              <div class="story-userId">
                {{ followee.toUserId }}
              </div>
            </div>
          </span>
        </div>
      </div>
    </div>
  </header>
</template>


<script setup>
import { useMoveStore } from '@/stores/pageMove';
import { onMounted, ref } from 'vue';
import { useFollowStore } from '@/stores/follow';

const movestore = useMoveStore();
const followstore = useFollowStore();

const id = sessionStorage.getItem('loginUserId');
const randomFollowees = ref([]);

onMounted(async () => {
  await followstore.getFolloweedata(id);
  selectRandomFollowees();
});

const selectRandomFollowees = () => {
  const followees = followstore.Followeedata;
  const shuffled = followees.sort(() => 0.5 - Math.random());
  randomFollowees.value = shuffled.slice(0, 9);
};

const goMypage = async (gotoid) => {
  if (gotoid === id) {
    await movestore.pageMoveMy(gotoid);
  } else {
    await movestore.pageMove(gotoid);
  }
};
</script>


<style scoped>
.logo {
  width: auto;
  height: 50px;
}

.header {
  display: flex;
  z-index: 800;
  align-items: center; 
  margin-left: 370px;
  margin-top: 40px;
  left: 200px;
  right: 0;
  top: 10px;
  width: 53%;
  height: 100px;
  padding: 20px;
}

.head-box {
  width: auto;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.followees {
  display: flex;
  flex-direction: row;
  align-items: center; 
}

.box {
  width: 64px;
  height: 64px;
  margin-bottom: 2px;
  border-radius: 50%;
  overflow: hidden;
  transition: transform 0.3s; 
}

.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.postuser {
  margin: 15px;
  text-align: center; 
}

.story-userId {
  font-size: 13px;
}

.author {
  display: inline-flex;
  align-items: center;
  cursor: pointer; 
  transition: transform 0.3s; 
}

.author:hover {
  transform: scale(1.1); 
}

</style>