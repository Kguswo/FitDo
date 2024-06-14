import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_USER_API = 'http://localhost:8080/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    loginUser: null, // 사용자 ID를 저장할 ref
    userdata: {},
    searchUserdata:{},
    recomendUser:{}
  }),
  actions: {
    createUser(user) {
      axios.post(`${REST_USER_API}/signup`, user)
        .then(() => {
          router.push({ name: 'login' })
        })
        .catch((err) => {
          alert("중복된 id입니다 바꿔주세요")
        })
    },
    async userlogin(id, password) {
      
      try {
        const response = await axios.post(`${REST_USER_API}/login?userid=${id}&password=${password}`, {
          userid: id,
          password: password
        });

        if (response.data === '로그인 실패!') {
          alert("로그인 실패하였습니다 id와 password를 다시 확인해 주세요")
        } else {
          sessionStorage.setItem('loginUserId', response.data.userid);
          this.loginUserId = response.data.userid;
          router.push('Home/Main')
        }
      } catch (err) {
        alert("로그인 실패하였습니다 id와 password를 다시 확인해 주세요")
      }
    },
    async getUserData(id) {
      
      try {
        this.userdata=""
        const response = await axios.get(`${REST_USER_API}/${id}`);
        this.userdata = response.data;
      } catch (error) {
        console.error(error);
      }
    },

    async updateUser(user) {
      try {
        await axios.put(`${REST_USER_API}/${sessionStorage.getItem('loginUserId')}`, user);
        // 회원 정보를 업데이트한 후에도 새로운 데이터를 다시 가져옵니다.
        await this.getUserData(sessionStorage.getItem('loginUserId'));
      } catch (err) {
        alert("모든 항목을 입력해 주세요.");
      }
    },

    async logout(id) {
      try {
        await axios.get(`${REST_USER_API}/logout`);
      } catch (err) {
        console.error(error);
      }
    },

   
    async searchUser(id) {
      
      try {
        this.searchUserdata={}
        const response = await axios.get(`${REST_USER_API}/search/${id}`);
        this.searchUserdata = response.data;
        
      } catch (err) {
        console.error(error);
      }
    },
    async updateUserProfileImage(id, formData) {
      try {
        await axios.post(`${REST_USER_API}/${id}/updateImage`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        // 프로필 사진 업데이트 후 사용자 데이터를 다시 가져옵니다.
        await this.getUserData(id);
      } catch (error) {
        console.error(error);
        alert('프로필 사진 업데이트에 실패했습니다.');
      }
    },
    
    async sameMbtiNotFollowing(id,mbti) {

      try {
       
        const response = await axios.get(`${REST_USER_API}/${id}/sameMbtiNotFollowing?mbti=${mbti}`);
        this.recomendUser = response.data;
        
      } catch (err) {
        console.error(error);
      }
    },
  },
  persist: {
    // 사용자 ID를 로컬 스토리지에 저장
    loginUserId: true,
    userdata: true,
  }
})




  
    
