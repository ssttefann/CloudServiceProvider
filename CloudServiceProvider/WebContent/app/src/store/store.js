
import Vue from'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";
import axios from 'axios';
import router from '../router/index'

Vue.use(Vuex)

export const store = new Vuex.Store({

    // plugins: [createPersistedState()],

    plugins: [createPersistedState({
        storage: window.sessionStorage,
    })],

    state : {

        loggedUser : {
            firstName : "", 
            lastName : "",
            role : ""
        },

        virtualMachines : [],
        VMCategories : [],
        discs : [],
        users : [],
        organizations : []

    },

    getters : {

        // vraca da li postoji ulogovan korisnik
        // isLogged(state) {
        //     return !state.loggedUser.firstName == "";
        // }

        isLogged : state => !state.loggedUser.firstName == "",

        isAdmin : state => state.loggedUser.role == "Admin" || state.loggedUser.role == "SuperAdmin"
    },

    mutations : {

        // brise ulogovanog usera
        logOut(state) {
            state.loggedUser = {
                firstName : "", 
                lastName : "",
                role : ""
            };
        },

        // iz resta ucitava ulogovanog korisnika
        logUser(state) {
        
            axios
            .get('rest/loggedUser/')
            .then(res => {
              state.loggedUser = res.data;
              if (this.getters.isAdmin) {
                    router.push('/admin')
              } else if (this.getters.isLogged){
                    router.push('/dashboard');
              }
            })
            .catch(err => alert(err));
            
            // ucitaj sve podatke potrebne za korisnika
            if(state.loggedUser.role != "" && state.loggedUser.role != undefined){
                store.commit('loadVMs');
                store.commit('loadDiscs');
                store.commit('loadUsers');
                store.commit('loadCategories');
                // store.commit('loadOrganizations');
            }
        },


        //dodaje novu VM 
        addVM(state, payload) {
            state.virtualMachines.push({
                payload
            });
        },

        //dodaje novi disk
        addDick(state, payload) {
            state.discs.push({
                name : payload.name,
                capacity : payload.capacity,
                type : payload.type,
                virtualMachine : payload.virtualMachine
            })
        },

        // Ucitava korisnike 
        loadUsers(state) {
            axios
                .get('rest/users/getAll/')
                .then(res => {
                    state.users = res.data;
                })
                .catch(err => alert(err));
        },

        // Ucitava sve virtuelne masine 
        loadVMs(state) {
            axios
                .get('rest/getVMs/')
                .then(res => {
                state.virtualMachines = res.data;
                })
                .catch(err => alert(err));
        },

        // Ucitava sve potrebne diskove 
        loadDiscs(state) {
            axios
                .get('rest/getDiscs/')
                .then(res => {
                    state.discs = res.data;
                })
                .catch(err => alert(err));
        },

        // Ucitava sve potrebne kategorije
        loadCategories(state) {
            axios
                .get('rest/getCategories/')
                .then(res => {
                    state.VMCategories = res.data;
                })
                .catch(err => alert(err));
        },

        // Ucitava sve potrebne organizacije
        loadOrganizations(state) {
            axios
                .get('rest/getOrganizations/')
                .then(res => {
                    state.organizations = res.data;
                })
                .catch(err => alert(err));
        },
    }
})