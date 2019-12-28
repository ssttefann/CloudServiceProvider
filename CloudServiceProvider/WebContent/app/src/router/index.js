import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Admin from '../components/Admin'
import Login from '../components/auth/Login'
import Register from '../components/auth/Register'
import NotFound from '../components/global/NotFound'
import Dashboard from '../components/Dashboard'
import {store} from '../store/store.js'

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    name: 'admin',
    component: Admin,
    meta : {
      requiresAuth : true,
      is_admin : true
    }
  },
  {
    path: '/',
    name: 'home',
    component: Home,
    meta : {
      guest : true
    }
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: Dashboard,
    meta : {
      requiresAuth : true
    }
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    meta : {
      guest : true
    }
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '*',
    name: 'notfound',
    component: NotFound,
    meta : {
      guest : true
    }
  },
  {
    path: '/about',
    name: 'about',
    meta : {
      guest : true
    },
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../components/global/About.vue')
  }
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
      // if (store.state.loggedUser.name != "") {
        if(store.getters.isLogged){
          next()
          return
      }
      next('/login')
  } else {
      next()
  }
})

export default router
