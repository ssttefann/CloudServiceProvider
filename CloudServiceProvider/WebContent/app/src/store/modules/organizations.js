import axios from 'axios';


export default {

    namespaced: true,
    state: {
        organizations: [],
    },

    getters: {
        orgGetter: state => state.organizations,
    },

    mutations: {
        SET_ORGANIZATIONS(state, data) {
            state.organizations = data;
        },

        ADD_ORGANIZATION(state, user) {
            state.organizations.push(user);
        },

        EDIT_ORGANIZATION(state, tuple) {
            let index = tuple[0]
            let newOrg = tuple[1]
            Object.assign(
                state.organizations[index],
                newOrg
            );
        },

        DELETE_ORGANIZATION(state, index) {
            state.organizations.splice(index, 1)
        }
    },

    actions: {

        async load({ commit }) {
            axios.get('/rest/organizations/getOrganizations/')
                .then(res => {
                    commit('SET_ORGANIZATIONS', res.data)
                })
                .catch(err => alert(err));
        },

        add({ commit }, org) {
            return new Promise((resolve, reject) => {
                axios.post('/rest/organizations/addOrganization', org)
                    .then(res => {
                        if (res.status == 200) {
                            commit('ADD_ORGANIZATION', org);
                            resolve();
                        } else {
                            reject(res.data);
                        }

                    })
                    .catch(err => reject(err));
            });
        },

        edit({ commit }, tuple) {
            const org = tuple[1];
            return new Promise((resolve, reject) => {
                axios.post("/rest/organizations/edit", org)
                    .then((response) => {
                        if (response.status === 200) {
                            commit("EDIT_ORGANIZATION", tuple);
                            resolve();
                        }
                    })
                    .catch(error => reject(error));
            })
        },

    }
}