
import Vue from'vue'
import Vuex from 'vuex'
// import createPersistedState from "vuex-persistedstate";
import axios from 'axios';

Vue.use(Vuex)

export const store = new Vuex.Store({

    // plugins: [createPersistedState()],

    state : {

        loggedUser : {
            firstName : "", 
            lastName : "",
            role : ""
        },

        virtualMachines : [
            {
                name : "VM1",
                category : {name : "KAT1",cores : 6,RAM : 16,GPU : 1}
            },

            {
                name : "VM2",
                category : {name : "KAT2",cores : 12,RAM : 64,GPU : 3}
            }
        ],


        disks : [
            { name : "Disk1", capacity : 512, type : "SSD"},
            { name : "Disk2", capacity : 256, type : "SSD"},
            { name : "Disk3", capacity : 1024, type : "HDD"},
        ]

    },

    getters : {

        // vraca da li postoji ulogovan korisnik
        isLogged(state) {
            return !state.loggedUser.firstName == "";
        }
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
            })
            //.catch(err => alert(err));
        }
    }
})