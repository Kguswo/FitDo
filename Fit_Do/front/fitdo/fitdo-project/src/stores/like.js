import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_Like_API = 'http://localhost:8080/postlike'

export const useLikeStore = defineStore('like', {
  state: () => ({
    loginUserId: null, 
    like: "",
    postlikeUser: {},
 
  }),
  actions: {

     async getlikedata(userid,postid) {
      try {
        const response = await axios.get(`${REST_Like_API}/postlikeState?userId=${userid}&postId=${postid}`);
        this.like = response.data;
      } catch (error) {
        console.error(error);
      }
    },

    async addLikeData(userid,postid) {
      try {
        await axios.post(`${REST_Like_API}/addPostlike?userId=${userid}&postId=${postid}`);
      } catch (error) {
        console.error(error);
      }
    },
    
    async minusLikeData(userid,postid) {
      
      try { 
        await axios.delete(`${REST_Like_API}/cancelPostlike?userId=${userid}&postId=${postid}`);
        
      } catch (error) {
        console.error(error);
      }
    },

    async PostlikeUsers(postid) {
      
      try { 
        this.postlikeUser = await axios.get(`${REST_Like_API}/PostlikeUsers?postId=${postid}`);
        
      } catch (error) {
        console.error(error);
      }
    },





   
    
  },
  persist: {
    // 사용자 ID를 로컬 스토리지에 저장
    loginUserId: true,
    goaldata: true,
  }
})