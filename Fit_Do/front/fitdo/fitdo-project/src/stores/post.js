import { defineStore } from 'pinia';
import axios from 'axios';

const REST_Post_API = 'http://localhost:8080/post';

export const usePostStore = defineStore('post', {
  state: () => ({
    loginUserId: null,
    postdata: {},
    postmydata:{},
    posttotal:{}
  }),
  actions: {
    async getPostfolloweeData(id) {
      try {  
        const response = await axios.get(`${REST_Post_API}/followee/${id}`);
        this.postdata = response.data;
        this.postdata.sort((a, b) => b.postId - a.postId);
      } catch (error) {
        console.error(error);
      }
    },
    
    async getPostsListByUserId(id) {
      try {  
        const response = await axios.get(`${REST_Post_API}/getPostsListByUserId/${id}`);
        this.postmydata = response.data;
        
      } catch (error) {
        console.error(error);
      }
    },
    async getPostTotalData() {
      try {
        const response = await axios.get(`${REST_Post_API}/withImg`);
        this.posttotal = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async createPostWithImage(formData,id) {
      try {
        await axios.post(`${REST_Post_API}/addpostWithImg/${id}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
      } catch (error) {
        console.error(error);
      }
    }
  },
  persist: {
    loginUserId: true,
    postdata: true,
  }
});