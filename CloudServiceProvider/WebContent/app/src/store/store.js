
import Vue from'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
// import axios from 'axios';
// import router from '../router/index'
import usersModule from './modules/users'
import vmModule from './modules/virtualMachines'
import categoriesModule from './modules/vmCategories'
import discsModule from './modules/discs'
import orgsModule from './modules/organizations'
import snackBarModule from "./modules/snackbar"

Vue.use(Vuex)



export const store = new Vuex.Store({

    // plugins: [createPersistedState()],

    plugins: [createPersistedState({
        storage: window.sessionStorage ,
    })],

    modules: {
        users : usersModule,
        vms : vmModule,
        categories : categoriesModule,
        disc : discsModule,
        orgs : orgsModule,
        snackbar: snackBarModule
    },

    state : {
        
    },

    mutations : {

    },

    actions : {

        async loadAllData({dispatch}) {
            if(this.getters['users/isLogged']){
                dispatch('categories/load');
                dispatch('vms/load');
                dispatch('users/load');
                dispatch('disc/load');
                dispatch('orgs/load');
            }
        },
    }
})