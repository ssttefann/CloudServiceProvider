import axios from 'axios';


export default {

    namespaced : true,
    state: {
        organizations : [],
    },

    mutations : {
        SET_ORGANIZATIONS(state, data) {
            state.organizations = data;
        },

    },

    actions : {

        async load({commit}) {
            axios.get('/rest/getOrganizations/')
                .then(res => {
                    commit('SET_ORGANIZATIONS', res.data)
                })
                .catch(err => alert(err));
        },
    },


}