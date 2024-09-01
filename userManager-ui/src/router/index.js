import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Index from "../views/Index.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Index,
    redirect: '/user',
    // 子路由
    children: [
      {
        path: '/user',
        name: 'user',
        component: () => import(('@/components/user'))
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  }
]

const router = new VueRouter({
  routes
})

export default router
