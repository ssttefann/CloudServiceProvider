import axios from 'axios';


export default {

    namespaced : true,
    state: {
        VMCategories : [],
    },

    mutations : {
        SET_CATEGORIES(state, categories) {
            state.VMCategories = categories;
        },

        ADD_CATEGORY(state, category) {
            state.VMCategories.push(category);
        }

    },

    actions : {

        load({commit}) {
            axios.get('rest/getCategories/')
                .then(res => {
                    commit('SET_CATEGORIES', res.data)
                })
                .catch(err => alert(err));
        },
    },


}