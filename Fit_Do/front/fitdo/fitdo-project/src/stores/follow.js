import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_Follow_API = 'http://localhost:8080/follow'

export const useFollowStore = defineStore('follow', {
  state: () => ({
    loginUserId: null, // 사용자 ID를 저장할 ref
    goaldata: {},
    Followerdata: {},
    Followeedata: {},
    check:{},
  }),
  actions: {

     async getFollowerdata(id) {
      try {
        
        const response = await axios.get(`${REST_Follow_API}/follower?toUserId=${id}`);
        this.Followerdata = response.data;
        
      } catch (error) {
        console.error(error);
      }
    },

    async getFolloweedata(id) {
      try {     
        this.Followeedata={}
        const response = await axios.get(`${REST_Follow_API}/followee?fromUserId=${id}`);
        this.Followeedata = response.data;
        
      } catch (error) {
        console.error(error);
      }
    },
    async checkFollow(myid,otherid) {
      try {    
        const response = await axios.post(`${REST_Follow_API}/checkFollow?fromUserId=${myid}&toUserId=${otherid}`);
        this.check = response.data;
        
      } catch (error) {
        console.error(error);
      }
    },
    async doFollow(myid,otherid) {
      try {    
        const response = await axios.post(`${REST_Follow_API}/doFollow?fromUserId=${myid}&toUserId=${otherid}`);
      } catch (error) {
        console.error(error);
      }
    },

    async undoFollow(myid,otherid) {
      try {   

        const response = await axios.post(`${REST_Follow_API}/doUnfollow?fromUserId=${myid}&toUserId=${otherid}`);
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