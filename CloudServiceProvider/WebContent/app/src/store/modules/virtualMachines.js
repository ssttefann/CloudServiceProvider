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
            Object.assign(
                state.virtualMachines[index],
                newVM
            );
        },

        DELETE_VIRTUAL_MACHINE(state, index) {
            state.virtualMachines.splice(index, 1)
        }

    },

    actions : {

        async load({commit}) {
            axios.get('rest/VMs/getAll/')
                .then(res => {
                    commit('SET_VIRTUAL_MACHINES', res.data)
                })
                .catch(err => alert(err));
        },

        add({commit}, vm) {
            return new Promise((resolve,reject) => {
                axios.post('rest/VMs/add/', vm)
                .then( res => {
                    if(res.status == 200) {
                        commit('ADD_VIRTUAL_MACHINE', vm);
                        resolve();
                    }else{
                        reject(res.data);
                    }

                })
                .catch(err => reject(err));
            })

        },

        edit({commit}, vm){
            alert(commit);
            alert(vm);
        },

        delete({commit}, vmName){
            alert(commit);
            alert(vmName);
        }



    },


}