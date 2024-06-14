<template>
  <div class="modal" v-if="show">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <input type="text" @input="handleSearch" v-model="searchId" class="search-input" placeholder="Search">
      <hr>
      <div v-for="searchItem in store.searchUserdata" :key="searchItem.id">
        <span @click="goUserpage(searchItem.userid)">
          <div class="searchid">
            <div class="box"  style="margin: 5px;">
              <img class="profile" :src="searchItem.profileImg" alt="검색프로필사진">
            </div>
            <div style="margin-left: 15px; font-weight: 600;">
              {{ searchItem.userid }}
            </div>
          </div>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useMoveStore } from '@/stores/pageMove';
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  show: Boolean,
});

const emit = defineEmits(['close']);

const store = useUserStore();
const movestore = useMoveStore();
const searchId = ref('');

const closeModal = () => {
  emit('close');
};

const handleSearch = async () => {
  await store.searchUser(searchId.value);
};

const goUserpage = async (gotoid) => {
  const id = sessionStorage.getItem('loginUserId');
  if (gotoid === id) {
    await movestore.pageMoveMy(gotoid);
  } else {
    await movestore.pageMove(gotoid);
  }
  closeModal();
};
</script>

<style scoped>
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1001;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  height: 500px;
  width: 500px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  cursor: pointer;
}

.search-input {
  font-weight: bold;
  padding: 10px;
  border: none;
  outline: none;
  background-color: #f0f0f0;
  border-radius: 15px;
  width: calc(100% - 40px);
}

.box {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;
}

.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.searchid {
  margin-left: 20px;
  display: flex;
  align-items: center;
  margin-top: 10px;
}

</style>
