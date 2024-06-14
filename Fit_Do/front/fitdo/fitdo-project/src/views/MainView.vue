<template>
  <div class="app-container">
    <main class="content">
      <div v-for="postItem in postStore.postdata" :key="postItem.postId">
        <div class="instagram-post">
          <div class="post-info">
            <div class="post-header">
              <span class="author" @click="goMypage(postItem.postUserId)">
                <div class="postuser">
                  <div class="box">
                    <img class="profile" :src="postItem.user.profileImg" alt="프로필사진입니다." />
                  </div>
                  {{ postItem.postUserId }}
                 
                </div>
              </span>
              <div class="regdate">{{ postItem.postRegDate }}</div>
            </div>
            <div class="post-content" style="border: 1px solid #dcdcdd; border-radius: 10px;">
              <div class="todo-list">
                <div class="post-box">
                  <div class="content-wrapper">
                    <div class="insta-content">
                      <img class="post-image" :src="postItem.postImg" alt="post사진" />
                    </div>
                  </div>
                </div>
              </div>

              <div style="margin-left: 4%;">
                <span class="title" style="margin-right: 10px; font-size: 16px;">{{ postItem.postUserId }}</span>
                <span style="font-size: 16px;">{{ postItem.postContent }}</span>
              </div>
              <br>
              <div class="likes-comments-container">
                <div class="likes">
                  <div v-if="likes[postItem.postId]">
                    <span @click="minusLikeData(postItem.postId)" class="bi bi-heart-fill" :class="{ 'liked': likes[postItem.postId] }"></span>
                  </div>
                  <div v-else>
                    <div class="heart-icon">
                      <span @click="addLikeData(postItem.postId)" class="bi bi-heart"></span>
                    </div>
                  </div>
                  <div class="like-num">
                    <div @click="openLikeModal(postItem.postId)" class="like-num">{{ postItem.postLike }} likes</div>
                  </div>
                </div>
                <div class="post-actions">
                  <div v-if="comments[postItem.postId]">
                    <div class="likes" style="margin-bottom: 8px;">
                      <button @click="openCoModal(postItem.postId, postItem.postGoalId, postItem.postRegDate, postItem.postImg, postItem.postUserId)" class="comment-button">
                        <span class="bi bi-chat" style="font-size: 1.5rem;"></span>
                      </button>
                    </div>
                    <div @click="openCoModal(postItem.postId, postItem.postGoalId, postItem.postRegDate, postItem.postImg, postItem.postUserId)" class="like-count">{{ comments[postItem.postId].length }} comment</div>
                  </div>
                  <div v-else>
                    <div class="likes" style="margin-bottom: 8px;">
                      <button @click="openCoModal(postItem.postId, postItem.postGoalId, postItem.postRegDate, postItem.postImg, postItem.postUserId)" class="comment-button">
                        <span class="bi bi-chat" style="font-size: 1.5rem;"></span>
                      </button>
                    </div>
                    <div @click="openCoModal(postItem.postId, postItem.postGoalId, postItem.postRegDate, postItem.postImg, postItem.postUserId)" class="like-count" style="margin-right: 10px;">0 comment</div>
                  </div>
                </div>
              </div>
              <br>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
  <Modal v-if="isLikeModalOpen" @close="closeLikeModal">
    <div>
      <h3 style="display: flex; font-size: 16px; font-weight: bold; justify-content: center; margin-bottom: 30px">Likes</h3>
      <ul>
        <div v-for="user in likeStore.postlikeUser.data" :key="user.postlikeId">
          <div style="display: flex; justify-content: center; margin-right: 50px;" class="likeuser" @click="goMypage(user.postlikeUserId)">
            <div class="box">
              <img class="profile" :src="user.postlikeUserImg" alt="프로필사진입니다." />
            </div>
            <div style="font-size: 14px; font-weight: bold; display: flex; align-items: center; margin-left: 10px">
              {{ user.postlikeUserId }}
            </div>
          </div>
        </div>
      </ul>
    </div>
  </Modal>
  <bigModal v-if="isModalOpen" @close="closeModal">
  <div class="todo-comment">
    <div class="todo">
      <div class="image-wrapper">
        <img :src="nowImg" alt="포스트이미지" class="todo-img">
      </div>
      <div class="goal-box">
        <div class="success-todos">오늘의 목표: {{ goalStore.goaldata[0].goalContent }}</div>
      </div>
      
        <div v-for="todoItem in todoData[currentGoalId]" :key="todoItem.todoId">
          <div v-if="todoItem.todoState" class="todo-item"><div class="todo-list">{{ todoItem.todoContent }}</div>
          
        </div>
      </div>
    </div>
    <div class="comment-container">
      <div v-if="comments[currentPostId]" class="commentmany">
        <div v-for="comment in comments[currentPostId]" :key="comment.commentId" class="comment">
          <div class="comment-header">
            <span class="comment-author" @click="goMypage(comment.commentUserId)">
              <div class="box" style="margin-right: 25px;">
                <img class="profile" :src="comment.user.profileImg" alt="프로필사진입니다." />
              </div>
              <div style="font-size: 14px; display: flex; align-items: center; margin-right: 5px">{{ comment.commentUserId }}</div>
            </span>
            <div class="comment-content" style="font-size: 14px;">{{ comment.commentContent }}</div>
          </div>
          <div>
            {{ comment.commentRegDate }}
          </div>
          <div v-if="comment.commentUserId === id" class="comment-actions">
            <span class="options-toggle" @click.stop="toggleOptions(comment.commentId)">...</span>
            <div v-if="showOptions[comment.commentId]" class="options-menu">
              <button @click="editComment(comment.commentId)">수정</button>
              <button @click="deleteComment(comment.commentId)">삭제</button>
            </div>
            <div v-if="editCommentId === comment.commentId" class="edit-comment">
              <input type="text" v-model="recommentdata.commentContent" placeholder="수정할 내용을 입력하세요" />
              <button @click="updateComment(comment.commentId, recommentdata)">수정</button>
            </div>
          </div>
        </div>
      </div>
      <div v-else>No comments yet...</div>

      <div class="comment-input">
        <input type="text" v-model="commentdata.commentContent" placeholder="Add a comment..." />
        <button class="bi bi-send send-button" @click="createComment(commentdata)"></button>
      </div>
    </div>
  </div>
</bigModal>
</template>




<script setup>

import bigModal from '@/components/common/bigModal.vue';
import Modal from '@/components/common/Modal.vue';
import { ref, onMounted } from 'vue';
import { usePostStore } from '@/stores/post';
import { useTodoStore } from '@/stores/todo';
import { useFollowStore } from '@/stores/follow';
import { useCommentStore } from '@/stores/comment';
import { RouterLink, useRouter } from 'vue-router';
import { useMoveStore } from '@/stores/pageMove';
import { useLikeStore } from '@/stores/like';
import { useGoalStore } from '@/stores/goal';

const likeStore = useLikeStore();
const goalStore = useGoalStore();
const movestore = useMoveStore();
const postStore = usePostStore();
const todoStore = useTodoStore();
const commentStore = useCommentStore();
const followstore = useFollowStore();
const todoData = ref({});
const comments = ref({});
const likes = ref({});
const isModalOpen = ref(false);
const currentPostId = ref(null);
const currentGoalId= ref(null)
const id = sessionStorage.getItem('loginUserId');
const showOptions = ref({});
const editCommentId = ref(null);
const deleteConfirm = ref({});

onMounted(async () => {
  await fetchAllData();
});

const getCommentData = async (postId) => {
  try {
      await commentStore.getConmmentData(postId);
      comments.value[postId] = commentStore.conmmentdata;
  } catch (error) {
      console.error(error);
  }
};

const fetchAllData = async () => {
  try {
      await postStore.getPostfolloweeData(id);
      for (const postItem of postStore.postdata) {
          await fetchTodoData(postItem.postGoalId);
          await getCommentData(postItem.postId);
          await getlikedata(postItem.postId)
      }
      await getFolloweedata(id);
      
  } catch (error) {
      console.error(error);
  }
};

const fetchTodoData = async (goalId) => {
  try {
      await todoStore.getTodoData(goalId);
      todoData.value[goalId] = todoStore.tododata;
  } catch (error) {
      console.error(error);
  }
};

const openModal = async (postId,goalId) => {
  currentPostId.value = postId;
  currentGoalId.value = goalId;
  
  await getCommentData(postId);
 
  isModalOpen.value = true;
};

const nowImg= ref("")
const openCoModal = async (postId,goalId,date,img,poid) => {
  
  currentPostId.value = postId;
  currentGoalId.value = goalId;
  nowImg.value=img;
  await goalStore.getGoalData(poid,date)
  await getCommentData(postId);
  
  isModalOpen.value = true;
};

const closeModal = () => {
  isModalOpen.value = false;
  currentPostId.value = null;
  currentGoalId.value = null;
  nowImg.valuevalue = null;
};

const commentdata = ref({
  "commentId": 0,
  "commentContent": "",
  "commentRegDate": "",
  "commentUserId": id,
  "commentPostId": ""
});

const recommentdata = ref({
  "commentId": "",
  "commentContent": "",
  "commentRegDate": "",
  "commentUserId": "",
  "commentPostId": ""
});

const createComment = async (commentdata) => {
  try {
      commentdata.commentPostId = currentPostId.value;
      await commentStore.createConmment(commentdata);
      await fetchAllData();
      commentdata.commentContent = "";
      commentdata.commentPostId = "";
  } catch (error) {
      console.error(error);
  }
};

const updateComment = async (commentid, comment) => {
  try {
      await commentStore.updateConmment(commentid, comment);
      recommentdata.commentContent = "";
      editCommentId.value = null;
      showOptions.value = {};
      await fetchAllData();
  } catch (error) {
      console.error(error);
  }
};

const deleteComment = async (commentId) => {
  try {
      await commentStore.deleteConmment(commentId);
      deleteConfirm.value[commentId] = false;
      showOptions.value = {};
      await fetchAllData();
  } catch (error) {
      console.error(error);
  }
};

const toggleOptions = (commentId) => {
  showOptions.value = { [commentId]: !showOptions.value[commentId] };
};

const editComment = (commentId) => {
  editCommentId.value = commentId;
  recommentdata.value.commentContent = comments.value[currentPostId.value].find(comment => comment.commentId === commentId).commentContent;
  showOptions.value = {};
};

const goMypage = async (gotoid) => {
  if (gotoid === id) {
      await movestore.pageMoveMy(gotoid);
  } else {
      await movestore.pageMove(gotoid);
  }
};

const getFolloweedata = async (userid) => {
  try {
      await followstore.getFolloweedata(userid);
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

const addLikeData = async (postId) => {
  try {
      await likeStore.addLikeData(id, postId);
      await fetchAllData();
  } catch (error) {
      console.error(error);
  }
};

const minusLikeData = async (postId) => {
  try {
      await likeStore.minusLikeData(id, postId);
      await fetchAllData();
  } catch (error) {
      console.error(error);
  }
};


const PostlikeUsers = async (postId) => {
  try {
      await likeStore.PostlikeUsers(postId);
      await fetchAllData();
  } catch (error) {
      console.error(error);
  }
};

const isLikeModalOpen = ref(false);

const openLikeModal = async (postId) => {
  try {
    await likeStore.PostlikeUsers(postId);
    isLikeModalOpen.value = true;
  } catch (error) {
    console.error(error);
  }
};

const closeLikeModal = () => {
  isLikeModalOpen.value = false;
};



</script>


<style scoped>
.todo-comment {
  display: flex;
  height: 100%;
  width: 100%;
}

.todo {
  margin-top: 5%;
  margin-right: 2%;
  width: 500px;
}

.image-wrapper {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.todo-img {
  width: 300px;
  height: 300px;
  border-radius: 10px;


  margin-bottom: 50px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: 900px;
}

.postuser{
  display: inline-flex;
  align-items: center;
  font-size: 14px;
  font-weight: 600px;
}

.post-image {
  width: 800px;
  object-fit: cover;
  /* border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  overflow: hidden; */
}

.post-image img {
  width: 800px;
  height: 100px;
  display: block;
}

.post-info {
  width: auto;
  padding: 20px;
}





.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}



.insta-conten{
  /* width: 400px;
  height: 1000px; */
    width: 800px;
  overflow: hidden;
}
.author {
  font-weight: bold;

}

.goal-box {
  margin-bottom: 20px;
  padding: 10px;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.success-todos {
  font-weight: bold;
  margin-bottom: 5px;
  margin-left: 10px;
}

.todo-list {
  background-color: #fff;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
}

.todo-item {
  padding: 5px 0;
  border-bottom: 1px solid #ddd;
}

.comment-container {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  width: 500px;
  overflow-y: auto;
  justify-content: space-between;
  height: 750px;
}

.commentmany {
  overflow-y: auto;
}

.comment {
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}

.comment-author {
  display: flex;
  flex-direction: row;
  font-weight: bold;
  margin-right: 5px;
}

.comment-actions {
  display: flex;
  align-items: center;
}

.options-toggle {
  cursor: pointer;
  margin-right: 10px;
}

.options-menu {
  display: flex;
  align-items: center;
  border-radius: 5px;
  border: none;
}

.comment-input {
  display: flex;
  margin-top: 10px;
  width: 400px;
  height: 45px;
  border: #333;
}

.comment-input input {
  width: 93%;
  border-radius: 5px;
}

.options-menu button {
  background-color: #f0f0f0;
  color: #333;
  padding: 5px 10px;
  margin-right: 5px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.options-menu button:hover {
  background-color: #ddd;
}

.edit-comment input {
  flex: 1;
  margin-right: 10px;
}

.send-button {
  background-color: #6aaff7;
  color: white;
  border: none;
  margin-left: 5px;
  /* margin-top: 2px; */
  /* margin-bottom: 2px; */
  padding-left: 13px;
  padding-right: 13px;
  /* padding-top: 3px; */
  /* padding-bottom: 13px; */
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button, .author, .comment-author, .options-toggle, .send-button {
  cursor: pointer;
  transition: background-color 0.3s;
}

.likes {
  font-weight: 700;
  color: #050000;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-decoration: none;
}

.likes span {
  font-size: 1.5rem;
  cursor: pointer;
  transition: transform 0.3s, color 0.3s;
}

.like-num {
  margin-right: 3px;
  margin-top: 4px;
}

.likes span:hover {
  transform: scale(1.2);
}

.likes span.liked {
  color: red;
}

.comments .bi-chat {
  margin-bottom: 5px;
  font-size: 1.5rem;
  cursor: pointer;
  transition: transform 0.3s, color 0.3s;
}

.heart-icon .bi-heart {
  font-size: 1.5rem;
  cursor: pointer;
  transition: transform 0.3s, color 0.3s;
}

.heart-icon .bi-heart:hover {
  transform: scale(1.2);
}

.heart-icon .bi-heart.hovered {
  color: #999;
  transition: transform 0.3s;
  color: red;
}

.like-count {
  font-weight: 700;
  color: #050000;
  cursor: pointer;
  transition: transform 10s;
  margin-bottom: 2px;
}

.app-container {
  display: flex;
  margin-right: 150px;
}

.content {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 20px;
}

.instagram-post {
  border: 1px solid #f1ebeb;
  border-radius: 10px;
  margin-bottom: 50px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: 900px;
}

.postuser {
  display: inline-flex;
  align-items: center;
  font-size: 14px;
  font-weight: 600;
}

.post-image {
  width: 800px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  margin-left: 35px;
  overflow: hidden;
}

.post-image img {
  width: 850px;
  display: block;
}

.post-info {
  width: auto;
  padding: 20px;
}

.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.insta-conten {
  width: 400px;
  height: 1000px;
}

.regdate {
  font-weight: 600;
  color: #7a7a7a;
}

.title {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.likeuser {
  display: flex;
  margin: 10px;
}

img {
  height: 500px;
  width: auto;
}

.likes-comments-container {
  margin-left: 30px;
  display: flex;
  align-items: center;
}

.post-actions {
  color: #000000;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.todo-img {
  width: 450px;
  height: auto;
}

.post-actions button {
  background-color: transparent;
  font-weight: 700;
  border: none;
  color: #000000;
  cursor: pointer;
}

.post-actions button:hover {
  text-decoration: underline;
}

.post-box {
  width: 800px;
  display: flex;
  justify-content: center;
  padding: 10px;
  margin-bottom: 20px;
  background-color: #ffffff;
  border-color: #dcdcdd;
  border-radius: 5px;
}

.content-wrapper {
  display: flex;
  align-items: center;
}

.box {
  margin-right: 10px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
}

.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>



