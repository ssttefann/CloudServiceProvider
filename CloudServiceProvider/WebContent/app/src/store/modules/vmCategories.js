import axios from 'axios';


export default {

    namespaced : true,
    state: {
        VMCategories : [],
    },

    getters: {
        getAll: state => state.VMCategories,
    },

    mutations : {
        SET_CATEGORIES(state, categories) {
            state.VMCategories = categories;
        },

        ADD_CATEGORY(state, category) {
            state.VMCategories.push(category);
        },

        EDIT_CATEGORY(state, tuple) {
            let index = tuple[0]
            let newCat = tuple[1]
            Object.assign(
                state.VMCategories[index],
                newCat
            );
        },

        DELETE_CATEGORY(state, index) {
            state.VMCategories.splice(index, 1)
        }

    },

    actions : {

        async load({commit}) {
            axios.get('rest/categories/getCategories/')
                .then(res => {
                    commit('SET_CATEGORIES', res.data)
                })
                .catch(err => alert(err));
        },

        add({commit}, category) {
            return new Promise((resolve,reject) => {
                axios.post('rest/categories/add/', category)
                .then( res => {
                    if(res.status == 200) {
                        commit('ADD_CATEGORY', category);
                        resolve();
                    }else{
                        reject(res.data);
                    }

                })
                .catch(err => reject(err));
            })

        },
    },


}