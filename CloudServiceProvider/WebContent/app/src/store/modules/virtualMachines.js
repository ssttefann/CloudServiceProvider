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

        ADD_VIRTUAL_MACHINE(state, vm) {
            state.virtualMachines.push(vm)
        },

        EDIT_VIRTUAL_MACHINE(state, tuple) {
            let index = tuple[0]
            let newVM = tuple[1]
            state.virtualMachines[index] = newVM
        },

        DELETE_VIRTUAL_MACHINE(state, index) {
            state.virtualMachines.splice(index, 1)
        }

    },

    actions : {

        async load({commit}) {
            axios.get('rest/VMs/getVMs/')
                .then(res => {
                    commit('SET_VIRTUAL_MACHINES', res.data)
                })
                .catch(err => alert(err));
        },

        add({commit}, vm) {
            return new Promise((resolve,reject) => {
                axios.post('rest/VMs/addVM/', JSON.stringify(vm))
                .then( res => {
                    if(res.data == "SUCCESS") {
                        commit('ADD_USER', vm);
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