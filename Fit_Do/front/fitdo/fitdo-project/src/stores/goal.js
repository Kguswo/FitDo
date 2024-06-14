import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_Goal_API = 'http://localhost:8080/goal'

export const useGoalStore = defineStore('goal', {
  state: () => ({
    loginUserId: null, // 사용자 ID를 저장할 ref
    goaldata: {},
  }),
  actions: {

     async getGoalData(id, date) {
      console.log(id,date)
      try {
        const response = await axios.get(`${REST_Goal_API}/${id}/${date}`);
        this.goaldata = response.data;
        
      } catch (error) {
        this.goaldata={}
        
        // console.error(error);
      }
    },
    async createGoal(goal) {
      
      try {
        await axios.post(`${REST_Goal_API}`,goal);
      } catch (error) {
        console.error(error);
      }
    },
    async updateGoalData(goal) {
      try {
        await axios.put(`${REST_Goal_API}/${goal.goalId}`,goal);
       
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