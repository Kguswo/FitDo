<template>
  <div class="app-container">
    <div v-for="postItem in shuffledPosts" :key="postItem.postId" class="post-item">
      <button @click="goUserpage(postItem.user.userid)" class="post-button">
        <img :src="postItem.postImg" alt="포스트이미지" class="post-image">
        <div class="like-count">❤️ {{ postItem.postLike }} &nbsp; 💬 {{ postItem.commentNum }}</div>
        <!-- <div class="bi bi-chat comment-count"> {{ postItem.commentNum }}</div> -->
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { usePostStore } from '@/stores/post';
import { useMoveStore } from '@/stores/pageMove';

const id = sessionStorage.getItem('loginUserId');
const movestore = useMoveStore();
const postStore = usePostStore();

const shuffledPosts = ref([]);

const shuffleArray = (array) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
};

onMounted(async () => {
  await postStore.getPostTotalData();
  shuffledPosts.value = shuffleArray([...postStore.posttotal]);
});

const goUserpage = async(gotoid) => {
  if (gotoid === id) {
    await movestore.pageMoveMy(gotoid);
  } else {
    await movestore.pageMove(gotoid);
  }
};
</script>

<style scoped>
.app-container {
  display: flex;
  flex-wrap: wrap;
  border-radius: 10px;
  margin-bottom: 20px;
  width: 900px;
  margin-top: 30px;
  margin-right: 170px;
}

.post-item {
  position: relative;
  margin: 10px;
}

.post-button {
  position: relative;
  border: none;
  background: none;
  padding: 0;
  cursor: pointer;
  overflow: hidden;
}

.post-image {
  width: auto;
  height: 300px;
  transition: transform 0.3s ease-in-out;
}

.post-button:hover .post-image {
  transform: scale(1.1);
}

.like-count {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: white;
  background-color: rgba(0, 0, 0, 0.5);
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 1.2rem;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}

.post-button:hover .like-count {
  opacity: 1;
}
</style>

