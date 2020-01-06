import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Admin from '../components/Admin'
import Login from '../components/auth/Login'
import Register from '../components/auth/Register'
import NotFound from '../components/errors/NotFound'
import Dashboard from '../components/Dashboard'
import Account from '../components/global/Account'
import {store} from '../store/store.js'

Vue.use(VueRouter)

/**
 * guest :  svako moze da pristupi ruti
 * requiresAuth : treba biti ulogovan za pristup
 * is_admin : samo administratori mogu videti
 */
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
    path: '/account',
    name: 'account',
    component: Account,
    meta : {
      requiresAuth : true,
      is_admin : false
    }
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
    component: () => import(/* webpackChunkName: "about" */ '../components/global/About.vue')
  }
]

const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// metoda koja se poziva pre svakog rutiranja u aplikaciji
router.beforeEach((to, from, next) => {
  
  store.commit('logUser');

  // da li je potrebna administratorksa privilegija
  if (to.matched.some(record => record.meta.is_admin)) {
    
    // ako je admin
    if(store.getters['users/isAdmin']){
      next();
      return;
    }
    next('/login');
  }

  // ako je potrebno biti ulogovan za pristup
  else if (to.matched.some(record => record.meta.requiresAuth)) {
      
    // da li postoji ulogovani korinik?
    if (store.getters['users/isLogged']) {
      next()
      return
    }
    // ako ne postoji vrati ga na login
    next('/login')

  } 
  // dozvoljen pristup svima
  else {
    next()
  }
})

export default router
