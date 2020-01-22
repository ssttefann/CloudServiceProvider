<template>
  <v-card>
    <v-card-title class="headline">Activities for {{ this.vm.name }}</v-card-title>

    <v-card-text>
      <v-data-table :items-per-page="10" :headers="headers" :items="activities">
        <template v-slot:item.edit="{ item }">
          <v-icon small class="mr-2" @click="editActivity(item)">mdi-lead-pencil</v-icon>
        </template>
      </v-data-table>
    </v-card-text>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn v-if="vm.active" dark color="blue-grey darken-1 white--text" text @click="stopVm">Stop</v-btn>
      <v-btn v-else dark color="blue-grey darken-1 white--text" text @click="startVm">Start</v-btn>
      <v-btn color="blue darken-1" text @click="closeActivitiesDialog">Close</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions } from "vuex";
export default {
  name: "Activities",
  props: ["activities", "vm", "vmIndex"],
  data() {
    return {
      headers: [
        { text: "Start", align: "left", value: "start" },
        { text: "End", align: "left", value: "end" },
        { text: "Edit", align: "left", value: "edit", sortable: false }
      ],
      transformedActivities: []
    };
  },

  methods: {
    ...mapActions({
      turnOnAction: "vms/turnOnVirtualMachine",
      turnOffAction: "vms/turnOffVirtualMachine",
      showSnackbar: "snackbar/showSnackbar"
    }),

    editActivity(activity) {
      alert(activity.id);
    },

    closeActivitiesDialog() {
      this.$emit("close");
    },

    startVm() {
      this.turnOnAction([this.vmIndex, this.vm.name])
        .then(() => {
          this.showSnackbar(["Virtual machine turned on", "success", "bottom"]);
          this.closeActivitiesDialog();
        })
        .catch(error => {
          this.showSnackbar([error, "error", "bottom"]);
        });
    },

    stopVm() {
      this.turnOffAction([this.vmIndex, this.vm.name])
        .then(() => {
          this.showSnackbar([
            "Virtual machine turned off",
            "success",
            "bottom"
          ]);
          this.closeActivitiesDialog();
        })
        .catch(error => {
          this.showSnackbar([error, "error", "bottom"]);
        });
    }
  },
};
</script>

<style>
</style>