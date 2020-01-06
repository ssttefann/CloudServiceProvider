
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
        orgs : orgsModule
    },

    state : {
        
    },

    mutations : {

        loadAllData() {
            if(this.getters['users/isLogged']){
                this.dispatch('vms/load');
                this.dispatch('users/load');
                this.dispatch('categories/load');
                this.dispatch('disc/load');
                this.dispatch('orgs/load');
            }
        },

    },

    actions : {
    }
})