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

        ADD_DISC(state, category) {
            state.discs.push(category);
        },

        EDIT_DISC(state, tuple) {
            let index = tuple[0]
            let newDisc = tuple[1]
            state.discs[index] = newDisc
        },

        DELETE_DISC(state, index) {
            state.discs.splice(index, 1)
        }

    },

    actions : {

        async load({commit}) {
            axios.get('rest/discs/getDiscs/')
                .then(res => {
                    commit('SET_DISCS', res.data)
                })
                .catch(err => alert(err));
        },

        // add({commit}, disc) {
            
        // }
    },


}