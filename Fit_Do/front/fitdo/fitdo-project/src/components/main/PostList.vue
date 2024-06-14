<template>
    <TheHeaderNav />
    <div v-for="postItem in postStore.postdata" :key="postItem.postId">
      <PostItem :postItem="postItem" @open-modal="openModal" />
    </div>
    <CommentModal v-if="isModalOpen" @close="closeModal" :currentPostId="currentPostId" />
  </template>
  
  <script setup>
  import TheHeaderNav from '@/components/common/TheSideNav.vue';
  import PostItem from '@/components/main/PostItem.vue';
  import CommentModal from '@/components/main/CommentModal.vue';
  import { ref, onMounted } from 'vue';
  import { usePostStore } from '@/stores/post';
  import { useCommentStore } from '@/stores/comment';
  
  const postStore = usePostStore();
  const commentStore = useCommentStore();
  const isModalOpen = ref(false);
  const currentPostId = ref(null);
  const id = sessionStorage.getItem('loginUserId');
  
  onMounted(async () => {
    await fetchAllData();
  });
  
  const fetchAllData = async () => {
    try {
      await postStore.getPostfolloweeData(id);
    } catch (error) {
      console.error(error);
    }
  };
  
  const openModal = async (postId) => {
    currentPostId.value = postId;
    isModalOpen.value = true;
  };
  
  const closeModal = () => {
    isModalOpen.value = false;
    currentPostId.value = null;
  };
  </script>
  