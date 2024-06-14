import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_Comment_API = 'http://localhost:8080/comment'

export const useCommentStore = defineStore('comment', {
  state: () => ({
    loginUserId: null, // 사용자 ID를 저장할 ref
    conmmentdata: {},
  }),
  actions: {

     async getConmmentData(postId) {
      
      try {
        this.conmmentdata=null
        const response = await axios.get(`${REST_Comment_API}/${postId}`);
        this.conmmentdata = response.data;
        
      } catch (error) {
        
        // console.error(error);
      }
    },
    async createConmment(comment) {
      try {
        await axios.post(`${REST_Comment_API}/addcomment`,comment);
      } catch (error) {
        console.error(error);
      }
    },
    async deleteConmment(commentid) {
      try {
        await axios.delete(`${REST_Comment_API}/${commentid}`);
      } catch (error) {
        console.error(error);
      }
    },
    async updateConmment(commentid,comment) {
      
      try {
        await axios.put(`${REST_Comment_API}/${commentid}`,comment);
      } catch (error) {
        console.error(error);
      }
    },
    
   





  },
  persist: {
    // 사용자 ID를 로컬 스토리지에 저장
    loginUserId: true,
    conmmentdata: true,
  }
})