<template>
  <div class="app-container">
    <div v-if="goalstore.goaldata && goalstore.goaldata.length > 0" class="goal-container">
      <br>
      <h2 style="font-weight: bold;">Goal : {{ goalstore.goaldata[0].goalContent }}</h2>
      <br>
      <div class="goalcontent-box" style="border: none; outline: none;">
          <input type="text" v-model="PostContent.postContent" style="border: none; outline: none;" placeholder="What's on your mind?"/>
      </div>
      <div class="todo-container">
        <h3 style="margin: 10px 0px 20px 50px; text-align: start;">{{ data.date }} To-do List</h3>
        <ul class="completed-todolists">
            <div class="completed-todos" v-for="todo in todostore.tododata" :key="todo.todoId" >
              <div v-if="todo.todoState" >
                <li>
                  <button class="todo-button">{{ todo.todoContent }}</button>
                </li>
              </div>
            </div>
        </ul>
      </div>
      <div>
        <h4>
          Add Photos/Videos
        </h4> 
        <input type="file" @change="handleFileUpload" />
        
      </div>
      <div v-if="imagePreview">
        <img :src="imagePreview" alt="미리보기" class="image-preview"/>
      </div>
      <br>
      <button @click="createPost" class="post-button">Post</button>    
    </div>
  </div>
</template>

<script setup>

import { ref } from 'vue';
import { useGoalStore } from '@/stores/goal';
import { useTodoStore } from '@/stores/todo';
import { usePostStore } from '@/stores/post';
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';

const goalstore = useGoalStore();
const todostore = useTodoStore();
const poststore = usePostStore();
const router = useRouter();
const id = sessionStorage.getItem('loginUserId');
const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + today.getDate()).slice(-2);
const dateString = year + '-' + month + '-' + day;

const data = ref({
  date: dateString
});

const file = ref(null);
const imagePreview = ref(null);

const PostContent = ref({
  postId: 0,
  postUserId: id,
  postGoalId: "",
  postContent: "",
  postLike: 0,
  postImg: "",
  postRegDate: data.value.date,
  user: {
    id: "",
    userid: "string",
    password: "string",
    birth: "string",
    name: "string",
    gender: "string",
    profileImg: "string",
    orgprofileImg: "string",
    mbti: "string"
  }
});

const handleFileUpload = (event) => {
  file.value = event.target.files[0];
  const reader = new FileReader();
  reader.onload = (e) => {
    imagePreview.value = e.target.result;
  };
  reader.readAsDataURL(file.value);
};

const createPost = async () => {
  try {
    PostContent.value.postGoalId = goalstore.goaldata[0].goalId;

    const formData = new FormData();
    formData.append("post", new Blob([JSON.stringify(PostContent.value)], { type: "application/json" }));
    if (file.value) {
      formData.append("file", file.value);
    }

    await poststore.createPostWithImage(formData,id);
    await router.push({ name: 'Main' });
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  check();
});

const check = async () => {
  try {
    await goalstore.getGoalData(id, data.value.date);
    await recheck(goalstore.goaldata[0].goalId);
  } catch (error) {
    console.error(error);
  }
};

const recheck = async (goalid) => {
  try {
    await todostore.getTodoData(goalid);
  } catch (error) {
    console.error(error);
  }
};
</script>


<style scoped>

.app-container{
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-right: 180px;
  margin-top: 20px;
  margin-bottom: 20px;
  height: 1200px;
  background-color: #fff;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: 900px;
}

.edit-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  font-family: 'Nanum Gothic', sans-serif;
}

.todo-button {
  background: none;
  border: none;
  cursor: pointer;
  font-family: 'Nanum Gothic', sans-serif;
  font-size: 1.2em; 
}

.todo-button-plus {
  color: #4CAF50;
  background: none;
  border: none;
  cursor: pointer;
  font-family: 'Nanum Gothic', sans-serif;
  font-size: 1.2em; 
}

.todo-button-fail {
  color: red;
  background: none;
  border: none;
  cursor: pointer;
  font-family: 'Nanum Gothic', sans-serif;
  font-size: 1.2em; 
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
  width: 800px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.completed-todos {
  align-items: start;

  padding-left: 0;
}

.completed-todos li {
  margin-left: 70px;
  text-align: start;
}

.todo-container {
  width: 80%;
  max-width: 800px;
  margin: 20px auto;
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

.todo-input {
  width: 100%;
  font-size: 1.2em;
}

.todo-state {
  flex: 1;
  text-align: right;
}


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


.post-button {
    width: 80%;
    padding: 15px;
    font-size: 1.2em;
    background-color: #5684fa;
    
    color: white;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    text-align: center;
    transition: background-color 0.3s;
}

.post-button:hover{
    background-color: #0866ff;
}


.image-preview {
  max-width: 100%;
  height: auto;
  margin-top: 10px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.goalcontent-box::before {
    content: '';
    position: absolute;
    top: -10px; 
    left: -13%;
    width: 125%;
    height: 1px;
    background-color: #818181; 

  
  }

.goalcontent-box{
  width: 600px;
  display: flex;
  justify-content: start;
  font-size: x-large;
  margin-bottom: 100px;
  margin-bottom: 100px;
  position: relative;
}

.goalcontent-box input {
  width: 100%;
  margin-top: 20px;
}
</style>