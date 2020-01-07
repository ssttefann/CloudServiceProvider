import axios from 'axios';


export default {

    namespaced : true,
    state: {
        discs : [],
    },

    mutations : {
        SET_DISCS(state, data) {
            state.discs = data;
        },

    },

    actions : {

        async load({commit}) {
            axios.get('rest/getDiscs/')
                .then(res => {
                    commit('SET_DISCS', res.data)
                })
                .catch(err => alert(err));
        },

        add({commit}, disc) {
            
        }
    },


}