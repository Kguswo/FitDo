<template>
    <div class="instagram-post">
      <div class="post-info">
        <div class="post-header">
          <span class="author" @click="goMypage(postItem.postUserId)">
            <div class="postuser">
              <div class="box">
                <img class="profile" :src="postItem.user.profileImg" alt="프로필사진입니다.">
              </div>
              {{ postItem.postUserId }}
            </div>
          </span>
          <div class="regdate">{{ postItem.postRegDate }}</div>
        </div>
        <div class="post-content">
          <div class="title">{{ postItem.postContent }}</div>
          <div class="todo-list">
            <div class="post-box">
              <div class="content-wrapper">
                <div class="insta-content">
                  <img :src="postItem.postImg" alt="post사진" />
                </div>
                <div>
                  <div class="success-todos">오늘 성공한 일</div>
                  <div v-for="todoItem in todoData[postItem.postGoalId]" :key="todoItem.todoId" class="todo-item">
                    <div v-if="todoItem.todoState">
                      {{ todoItem.todoContent }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="likes">
            <div @click="PostlikeUsers(postItem.postId)">좋아요 수: {{ postItem.postLike }}</div>
            <li v-for="user in likeStore.postlikeUser.data" :key="user.postlikeId">
              {{ user.postlikeUserId }}
            </li>
            <div v-if="likes[postItem.postId]">
              <span @click="minusLikeData(postItem.postId)" class="bi bi-heart-fill" style="color: red;">
              </span>
            </div>
            <div v-else>
              <span @click="addLikeData(postItem.postId)" class="bi bi-heart">
              </span>
            </div>
          </div>
        </div>
        <div class="post-actions">
          <div v-if="comments[postItem.postId]">
            <div>
              댓글수: {{ comments[postItem.postId].length }}
              <button @click="$emit('open-modal', postItem.postId)">
                댓글달러가기
              </button>
            </div>
          </div>
          <div v-else>
            <div>
              댓글수: 0
              <button @click="$emit('open-modal', postItem.postId)">
                댓글달러가기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useTodoStore } from '@/stores/todo';
  import { useCommentStore } from '@/stores/comment';
  import { useLikeStore } from '@/stores/like';
  import { useMoveStore } from '@/stores/pageMove';
  
  const todoStore = useTodoStore();
  const commentStore = useCommentStore();
  const likeStore = useLikeStore();
  const movestore = useMoveStore();
  const todoData = ref({});
  const comments = ref({});
  const likes = ref({});
  const { postItem } = defineProps();
  const id = sessionStorage.getItem('loginUserId');
  
  onMounted(async () => {
    await fetchTodoData(postItem.postGoalId);
    await getCommentData(postItem.postId);
    await getlikedata(postItem.postId);
  });
  
  const fetchTodoData = async (goalId) => {
    try {
      await todoStore.getTodoData(goalId);
      todoData.value[goalId] = todoStore.tododata;
    } catch (error) {
      console.error(error);
    }
  };
  
  const getCommentData = async (postId) => {
    try {
      await commentStore.getConmmentData(postId);
      comments.value[postId] = commentStore.conmmentdata;
    } catch (error) {
      console.error(error);
    }
  };
  
  const getlikedata = async (postId) => {
    try {
      await likeStore.getlikedata(id, postId);
      likes.value[postId] = likeStore.like;
    } catch (error) {
      console.error(error);
    }
  };
  
  const goMypage = async (gotoid) => {
    if (gotoid === id) {
      await movestore.pageMoveMy(gotoid);
    } else {
      await movestore.pageMove(gotoid);
    }
  };
  
  const addLikeData = async (postId) => {
    try {
      await likeStore.addLikeData(id, postId);
      await fetchTodoData(postItem.postGoalId);
      await getCommentData(postItem.postId);
    } catch (error) {
      console.error(error);
    }
  };
  
  const minusLikeData = async (postId) => {
    try {
      await likeStore.minusLikeData(id, postId);
      await fetchTodoData(postItem.postGoalId);
      await getCommentData(postItem.postId);
    } catch (error) {
      console.error(error);
    }
  };
  
  const PostlikeUsers = async (postId) => {
    try {
      await likeStore.PostlikeUsers(postId);
      await fetchTodoData(postItem.postGoalId);
      await getCommentData(postItem.postId);
    } catch (error) {
      console.error(error);
    }
  };
  </script>
  
  <style scoped>
  </style>
  