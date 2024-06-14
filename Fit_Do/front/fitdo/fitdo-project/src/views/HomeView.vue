<template>
  <div class="full-box">
    <div>
      <TheHeaderNav />
    </div>
    <div class="box justify-content-center">
      <div class="left">
        <TheLeftSidebar />

      </div>
      <div class="middle">
        <router-view class="overflow-auto" />
      </div>
      <div class="right">
         <TheRightSidebar /> 
    </div>
  </div>
  </div>
 

</template>

<script setup>
import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
import TheLeftSidebar from '@/components/common/TheLeftSidebar.vue';
import TheRightSidebar from '@/components/common/TheRightSidebar.vue';
</script>

<style scoped>

.full-box {
  display: flex;
  flex-direction: column;
}

.box {
  display: flex;
  margin-top: 10px;
}

.vertical-view {
  height: 90vh;
  
}

.middle {
  /* flex: 1; */
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0px auto;
  flex-direction: column;
  /* margin-right: 180px; */
}

.middle > * {
  max-width: 100%;
}

.left, .right {
  flex: 0 0 auto; 
}

</style>

