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

    actions : {

        async load({commit}) {
            axios.get('/rest/organizations/getOrganizations/')
                .then(res => {
                    commit('SET_ORGANIZATIONS', res.data)
                })
                .catch(err => alert(err));
        },
    },


}