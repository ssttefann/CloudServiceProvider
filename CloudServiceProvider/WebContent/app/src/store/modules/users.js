import router from '../../router/index'
import axios from 'axios';


export default {

    namespaced : true,
    state: {

        loggedUser : {firstName : "", lastName : "",role : ""},
        users : []
    },

    mutations : {
        SET_USER(state, user) {
            state.loggedUser = user;
        },

        SET_USERS(state, users) {
            state.users = users;
        }

    },

    actions : {

        logUser({commit}) {
            if(this.getters['users/isLogged'])
                return;
            
            axios.get('rest/loggedUser/')
            .then(res => {
                commit('SET_USER', res.data);
                  
                if (this.getters['users/isAdmin']) {
                    router.push('/admin')
                }
                else if (this.getters['users/isLogged'])
                    router.push('/dashboard');
                })
            .catch(err => alert(err));      
        },

        logOut({commit}) {
            commit('SET_USER', { firstName : "", lastName : "",role : ""});
            router.push('/');
        },

        load({commit}) {
            axios.get('rest/users/getAll/')
                .then(res => {
                    commit('SET_USERS', res.data)
                })
                .catch(err => alert(err));
        },


    },
    getters : {
        isLogged : state => state.loggedUser.firstName != "",

        isAdmin : state => state.loggedUser.role == "Admin" || state.loggedUser.role == "SuperAdmin",
    }

}