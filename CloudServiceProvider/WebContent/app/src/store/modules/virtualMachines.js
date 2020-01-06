import axios from 'axios';


export default {

    namespaced : true,
    state: {
        virtualMachines : [],
    },

    mutations : {
        SET_VIRTUAL_MACHINES(state, vms) {
            state.virtualMachines = vms;
        },

    },

    actions : {

        load({commit}) {
            axios.get('rest/getVMs/')
                .then(res => {
                    commit('SET_VIRTUAL_MACHINES', res.data)
                })
                .catch(err => alert(err));
        },
    },


}