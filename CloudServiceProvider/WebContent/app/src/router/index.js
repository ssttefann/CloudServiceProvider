import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Admin from '../components/Admin'
import Login from '../components/Login'
import Register from '../components/Register'
import NotFound from '../components/NotFound'
import Dashboard from '../components/Dashboard'

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    name: 'admin',
    component: Admin
  },
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: Dashboard
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '*',
    name: 'notfound',
    component: NotFound
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../components/About.vue')
  }
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
