import { defineStore } from 'pinia'

import router from '@/router'

export const useMoveStore = defineStore('move', {
  state: () => ({
    movetouserid: null, // 사용자 ID를 저장할 ref
    
  }),
  actions: {
    pageMove(id) {
       
        
        sessionStorage.setItem('lastId', id)
        router.push({ name: 'UserTo', params: { id } });
        
    },
  

    pageMoveMy(id) {
        router.push({ name: 'User' });
    },
   

  },
  persist: {
    // 사용자 ID를 로컬 스토리지에 저장
    movetouserid: true,
    
  }
})