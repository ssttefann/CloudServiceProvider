import axios from 'axios';

export default {

    namespaced: true,
    state: {
        discs: [],
    },



    mutations: {
        SET_DISCS(state, data) {
            state.discs = data;
        },

        ADD_DISC(state, disc) {
            state.discs.push(disc);
        },

        EDIT_DISC(state, tuple) {
            let index = tuple[0]
            let newDisc = tuple[1]
            
            Object.assign(
                state.discs[index],
                newDisc
            );
        },

        DELETE_DISC(state, index) {
            state.discs.splice(index, 1)
        }

    },

    actions: {

        async load({ commit }) {
            axios.get('rest/discs/getDiscs/')
                .then(res => {
                    commit('SET_DISCS', res.data)
                })
                .catch(err => alert(err));
        },

        add({ commit }, disc) {
            return new Promise((resolve, reject) => {
                axios.post("/rest/discs/add/", JSON.stringify(disc))
                    .then((response) => {
                        alert(response.status);
                        if (response.status === 200) {
                            commit('ADD_DISC', response.data);
                            resolve();
                        }
                    })
                    .catch(error => reject(error));
            });
        },

        edit({ commit }, tuple) {
            const disc = tuple[1];
            return new Promise((resolve, reject) => {
                axios.post("/rest/discs/edit/", disc)
                    .then((response) => {
                        if (response.status === 200) {
                            commit('EDIT_DISC', tuple);
                            resolve();
                        }
                    })
                    .catch(error => {
                        reject(error);
                    })
            })
        },

        delete({ commit }, tuple) {
            const discIndex = tuple[0];
            const discName = tuple[1];
            return new Promise((resolve, reject) => {
                axios.delete("/rest/discs/delete/" + discName)
                    .then(response => {
                        if (response.status === 200) {
                            commit('DELETE_DISC', discIndex);
                            resolve();
                        }
                    })
                    .catch(error => reject(error));
            });
        }
    },

    getters: {
        getDiscs: state => state.discs,
    }


}