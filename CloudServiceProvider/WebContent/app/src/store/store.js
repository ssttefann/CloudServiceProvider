
import Vue from'vue'
import Vuex from 'vuex'
import axios from 'axios';

Vue.use(Vuex)

export const store = new Vuex.Store({
    state : {

        loggedUser : {
            firstName : "", 
            lastName : ""
        }
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
                lastName : ""
            };
        },

        // iz resta ucitava ulogovanog korisnika
        logUser(state) {
            axios
            .get('rest/loggedUser/')
            .then(res => {
              state.loggedUser = res.data;
            })
            .catch(err => alert(err));
        }
    }
})