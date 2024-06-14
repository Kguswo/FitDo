import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_Todo_API = 'http://localhost:8080/todo'

export const useTodoStore = defineStore('todo', {
  state: () => ({
    loginUserId: null, // 사용자 ID를 저장할 ref
    tododata: null,
  }),
  actions: {
     async getTodoData(goalid) {
      try {
       
        const response = await axios.get(`${REST_Todo_API}?todoGoalId=${goalid}`);
        this.tododata = response.data;
       
        
      } catch (error) {
        this.tododata=""
        // console.error(error);
      }
    },
    async addTodoData(newtodo) {
      try {
        const response = await axios.post(`${REST_Todo_API}`,newtodo);
      } catch (error) {
        console.error(error);
      }
    },
    async DeleteTodoData(todoid) {
      try {
          await axios.delete(`${REST_Todo_API}/${todoid}`);
          // 삭제 요청 성공 후 새로운 할일 목록을 불러옴
          await this.getTodoData(todoid); // 혹은 goalid로 전달해야할듯
      } catch (err) {
          console.error(err);
      }
  },

  async UpdateTodoData(todoid,todo) {
    try {
        await axios.put(`${REST_Todo_API}/${todoid}`,todo);
       
    } catch (err) {
        console.error(err);
    }
},
  async UpdateState(todoid) {
    
    try {
      await axios.put(`${REST_Todo_API}/${todoid}/changeState`);
     
  } catch (err) {
      console.error(err);
  }
},






  },
  persist: {
    // 사용자 ID를 로컬 스토리지에 저장
    loginUserId: true,
    tododata: true,
  }
})