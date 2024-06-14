import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import SignUpView from '../views/SignUpView.vue'
import HomeView from '../views/HomeView.vue'
import { useUserStore } from '@/stores/user';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/SignUp',
      name: 'SignUp',
      component: SignUpView
    },
    {
      path: '/Home',
      name: 'Home',
      component: HomeView,
      meta: { requiresAuth: true }, // 이 라우트는 인증이 필요함
      children: [
        {
          path: 'Search',
          name: 'Search',
          component: () => import('../views/SearchView.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'Main',
          name: 'Main',
          component: () => import('../views/MainView.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'Todo',
          name: 'Todo',
          component: () => import('../views/TodoView.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'MyPage',
          name: 'MyPage',
          component: () => import('../views/MyPageView.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'Post',
          name: 'Post',
          component: () => import('../views/PostView.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'User',
          name: 'User',
          component: () => import('../views/UserView.vue'),
          meta: { requiresAuth: true }
        },
        {
          path: 'UserTo/:id',
          name: 'UserTo',
          component: () => import('../views/UserOtherView.vue'),
          meta: { requiresAuth: true }
        },
      
      ]
    },
  ]
});

// Navigation Guards
router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  const isAuthenticated = sessionStorage.getItem('loginUserId');

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!isAuthenticated) {
      // 사용자가 로그인하지 않은 경우 로그인 페이지로 리디렉션
      next({ name: 'login' });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;

