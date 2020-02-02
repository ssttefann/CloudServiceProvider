<template>
  <v-card>
    <v-card-title class="headline">Activities for {{ this.vm.name }}</v-card-title>

    <v-card-text>
      <v-data-table :items-per-page="10" :headers="headers" :items="activities">

        <!-- editovanje pocetka aktivnosti -->
        <template v-if="isSuper" v-slot:item.start="prpr">
          <v-edit-dialog
            :return-value.sync="prpr.item.start"
            large
            persistent
            @save="save"
            @cancel="cancel"
            @open="open"
            @close="close" 
          > {{prpr.item.start }}
            <template v-slot:input>
              <v-text-field
                v-model="prpr.item.start"
                :rules="[validDate]"
                label="Edit"
                single-line 
                counter
                autofocus
              ></v-text-field>
            </template>
          </v-edit-dialog>
        </template>

        <!-- editovanje kraja aktivnosti -->
        <template v-if="isSuper" v-slot:item.end="prpr">
          <v-edit-dialog
            :return-value.sync="prpr.item.end"
            large
            persistent
            @save="save"
            @cancel="cancel"
            @open="open"
            @close="close" 
          > 
            <div>{{prpr.item.end }}</div>
            <template v-slot:input>
              <div class="mt-4 title">Update End</div>
            </template>
            <template v-slot:input>
              <v-text-field
                v-model="prpr.item.end"
                :rules="[validDate]"
                label="Edit"
                single-line
                counter 
                autofocus
              ></v-text-field>
            </template>
          </v-edit-dialog>
        </template>

      </v-data-table>
    </v-card-text>

  </v-card>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "Activities",
  props: ["activities", "vm", "vmIndex"],
  data() {
    return {

      validDate: v=> v.length <= 25 || 'Input too long!',
      headers: [
        { text: "Start", align: "left", value: "start" },
        { text: "End", align: "left", value: "end" },
        // { text: "Edit", align: "left", value: "edit", sortable: false }
      ],
    };
  },

  created(){
    console.log(this.vm);
    console.log(this.activities);
  },

  computed:{
    ...mapGetters({
      isAdmin: "users/isAdmin",
      isSuper: "users/isSuper",
    }),
  },

  methods: {
    ...mapActions({
      turnOnAction: "vms/turnOnVirtualMachine",
      turnOffAction: "vms/turnOffVirtualMachine",
      showSnackbar: "snackbar/showSnackbar"
    }),

    save() {
      this.showSnackbar(["Data saved", "success", "bottom"]);
      console.log(this.virt.activities);
    },

    cancel() {
      // this.showSnackbar(["Canceled", "error", "bottom"]);
    },

    open() {

    },

    close() {
      
    },

    editActivity(activity) {
      alert(activity.id);
    },

    closeActivitiesDialog() {
      this.$emit("close");
    },
    
  },
};
</script>

<style>
</style>