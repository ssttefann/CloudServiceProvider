import axios from 'axios';
import { stringify } from 'querystring';

export default {

    namespaced: true,
    state: {

        loggedUser: { firstName: "", lastName: "", role: "", email: "", organizationName: ""},
        users: []
    },

    mutations: {
        SET_LOGGED_USER(state, user) {
            state.loggedUser = user;
        },

        SET_USERS(state, users) {
            state.users = users;
        },

        ADD_USER(state, user) {
            state.users.push(user);
        },

        EDIT_USER(state, tuple) {
            let index = tuple[0]
            let newUser = tuple[1]
            Object.assign(
                state.users[index],
                newUser
            );
        },

        DELETE_USER(state, index) {
            state.users.splice(index, 1)
        }

    },

    actions: {

        getLoggedUser({ commit }) {
            return new Promise((resolve, reject) => {
                axios.get("/rest/loggedUser/")
                    .then((response) => {
                        commit("SET_LOGGED_USER", response.data);
                        resolve(response.data);
                    })
                    .catch(error => {
                        alert(error);
                        reject(error);
                    });
            });
        },

        logUser({ commit }, credentials) {
            return new Promise((resolve, reject) => {
                axios.post("/rest/login/", stringify(credentials))
                    .then((response) => {
                        commit("SET_LOGGED_USER", response.data);
                        resolve(response.data);
                    }).catch((error) => {
                        reject(error);
                    });
            });
        },

        logout({ commit }) {
            return new Promise((resolve, reject) => {
                axios.post("/rest/logout/")
                    .then(response => {
                        commit('SET_LOGGED_USER', response.data);
                        resolve();
                    })
                    .catch(error => reject(error));
            });
        },

        async load({ commit }) {
            axios.get('rest/users/getAll/')
                .then(res => {
                    commit('SET_USERS', res.data)
                })
                .catch(err => alert(err));
        },


        add({ commit }, user) {
            return new Promise((resolve, reject) => {
                axios.post('rest/users/add/', JSON.stringify(user))
                    .then(res => {
                        if (res.data == "SUCCESS") {
                            commit('ADD_USER', user);
                            resolve();
                        } else {
                            reject(res.data);
                        }

                    })
                    .catch(err => reject(err));
            })

        },


        edit({ commit }, tuple) {
            let user = tuple[1]

            return new Promise((resolve, reject) => {
                axios.post('rest/users/edit/', JSON.stringify(user))
                    .then(res => {
                        if (res.data == "SUCCESS") {
                            commit('EDIT_USER', tuple);
                            resolve();
                        } else {
                            reject(res.data);
                        }
                    })
                    .catch(err => reject(err));
            })
        },

        updateAccount({commit}, user){
            commit;
            return new Promise((resolve, reject) => {
                axios.post('rest/users/update/', user)
                    .then(res => {
                        if (res.status == 200) {
                            commit("SET_LOGGED_USER", res.data);
                            resolve();
                        } else {
                            reject(res.data);
                        }
                    })
                    .catch(err => reject(err));
            });
        },

        delete({ commit }, tuple) {
            let index = tuple[0]
            let email = tuple[1]

            return new Promise((resolve, reject) => {
                axios.delete('rest/users/delete/' + email)
                    .then(res => {
                        if (res.status === 200) {
                            commit('DELETE_USER', index);
                            resolve();
                        } else {
                            reject(res);
                        }
                    })
                    .catch(err => reject(err));
            })
        }


    },
    getters: {
        getUser: state => state.loggedUser,

        isLogged: state => state.loggedUser.firstName != "",

        isUser: state => state.loggedUser.role == "User",

        isAdmin: state => state.loggedUser.role == "Admin",

        isSuper: state => state.loggedUser.role == "SuperAdmin",

    }

}