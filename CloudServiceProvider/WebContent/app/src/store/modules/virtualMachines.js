import axios from 'axios';


export default {

    namespaced : true,
    state: {
        virtualMachines : [],
    },

    getters: {
        getAll: state => state.virtualMachines,
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

        edit({commit}, tuple){
            const vm = tuple[1];
            return new Promise((resolve, reject) => {
                axios.post("/rest/VMs/edit/", vm)
                    .then((response) => {
                        if(response.status === 200){
                            commit("EDIT_VIRTUAL_MACHINE", tuple);
                            resolve();
                        }
                    })
                    .catch(error => reject(error));
            })
        },

        delete({commit}, tuple){
            const vmIndex = tuple[0];
            const vmName = tuple[1];
            return new Promise((resolve, reject) => {
                axios.delete("/rest/VMs/delete/" + vmName)
                    .then(response => {
                        if(response.status === 200){
                            commit("DELETE_VIRTUAL_MACHINE", vmIndex);
                            resolve();
                        }
                    }).catch(error => reject(error));
            })
        }



    },


}