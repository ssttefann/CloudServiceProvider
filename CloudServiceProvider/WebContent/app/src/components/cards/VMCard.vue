
<template>
  <v-card outline block class="ma-3" hover>
    <v-card-title class="blue-grey white--text">
      Virtual Machines
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-search-web"
        :disabled="isHidden"
        dark
        color="white"
        label="Search"
        single-line
        hide-details
      ></v-text-field>

      <v-menu bottom left offset-y>
        <template v-slot:activator="{ on }">
          <v-btn dark icon v-on="on">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>

        <v-btn v-if="isHidden" @click="hide" dark class="white blue-grey--text">
          <v-icon>mdi-eye</v-icon>Show
        </v-btn>

        <v-btn v-else @click="hide" dark class="white blue-grey--text">
          <v-icon>mdi-eye-off</v-icon>Hide
        </v-btn>
      </v-menu>
    </v-card-title>

    <!-- Tabela za prikaz svih elemenata -->
    <v-data-table
      :hidden="isHidden"
      :items-per-page="5"
      class="ma-6"
      :search="search"
      :headers="headers"
      :items="this.$store.state.vms.virtualMachines"
    >
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-btn
          v-if="isAdmin || isSuper"
          color="blue-grey darken-1 white--text"
          dark
          class="mb-2"
          @click="show"
        >New VM</v-btn>

        <v-dialog v-model="dialog" width="50%">
          <!-- <template  v-slot:activator="{ on }"></template> -->

          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      :disabled="editDisabled"
                      v-model="editedItem.name"
                      label="VM name"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select :items="catNames" v-model="editedItem.category.name" label="Category"></v-select>
                  </v-col>
                  <v-col v-if="isSuper" cols="12" sm="6" md="4">
                    <v-select
                      :items="orgNames"
                      :disabled="editDisabled"
                      v-model="editedItem.organizationName"
                      label="Organization"
                    ></v-select>
                  </v-col>

                  <v-col v-if="editDisabled" cols="12" sm="6" md="4">
                    <v-switch
                      v-model="editedItem.active"
                      :label="isTurnedOn"
                    >
                  </v-col>

                  <v-col cols="12" sm="12" md="12">
                    
                    <!-- tabela za biranje diskova -->
                    <v-card width="100%" :disabled="disksForVms.length > 0">
                      <v-card-title class="grey white--text">
                        Select disks
                        <v-spacer></v-spacer>
                        <v-text-field
                          v-model="searchDiscs"
                          dark
                          append-icon="mdi-search-web"
                          :disabled="isHidden"
                          label="Search"
                          single-line
                          hide-details
                        ></v-text-field>
                      </v-card-title>

                      <v-data-table
                        v-model="selectedDiscs"
                        :items-per-page="5"
                        class="ma-6"
                        :search="searchDiscs"
                        item-key="name"
                        dense
                        show-select
                        :headers="diskHeaders"
                        :items="disksForVms"
                      ></v-data-table>
                    </v-card>
                  </v-col>
                  <!-- <h2 v-else>No disks available.</h2> -->
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>

        <v-dialog v-model="activitiesDialog" width="50%">
          <Activities
            v-on:close="closeActivitiesDialog"
            :vm="vmToShowActivitiesFor"
            :activities="activities"
            :vmIndex="vmToShowActivitiesForIndex"
          />
        </v-dialog>
      </template>

      <!-- Template za brisanje i editovanje -->
      <template v-if="isSuper || isAdmin" v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-lead-pencil</v-icon>
        <v-icon small class="mr-2" @click="showActivities(item)">mdi-clock</v-icon>
        <v-icon small class="mr-2" @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>


<script>
import { mapActions, mapGetters } from "vuex";
import Activities from "./Activities";

export default {
  components: {
    Activities
  },

  data() {
    return {
      hidden: false,
      headers: [
        { text: "Name", align: "left", value: "name" },
        { text: "Cores", value: "category.cores" },
        { text: "RAM", value: "category.RAM" },
        { text: "GPU", value: "category.GPU" },
        { text: "Organization", value: "organizationName" },
        { text: "Actions", value: "action", sortable: false }
      ],

      diskHeaders: [
        { text: "Name", align: "left", value: "name" },
        { text: "Capacity", value: "capacity" },
        { text: "Type", value: "type" }
      ],
      dialog: false,

      // za dijalog sa aktivnostima
      vmToShowActivitiesFor: null,
      vmToShowActivitiesForIndex: null,
      activitiesDialog: false,
      activities: [],

      searchDiscs: "",
      selectedDiscs: [],

      search: "",
      editedIndex: -1,
      editedItem: {
        name: "",
        organizationName: "",
        active: false,
        category: {
          name: "",
          cores: 0,
          RAM: 0,
          GPU: 0
        }
      },
      defaultItem: {
        name: "",
        active: true,
        organizationName: "",
        category: {
          name: "",
          cores: 0,
          RAM: 0,
          GPU: 0
        }
      }
    };
  },

  computed: {
    ...mapGetters({
      categoriesGetter: "categories/getAll",
      diskGetter: "disc/getDiscs",
      vmsGetter: "vms/getAll",
      isSuper: "users/isSuper",
      isAdmin: "users/isAdmin",
      getUser: "users/getUser"
    }),

    /**
     * Za biranje diskova pri dodavanju/editovanju vmova
     */
    disksForVms() {
      let orgName;

      if (this.isAdmin) {
        orgName = this.getUser.organizationName;
      } else {
        orgName = this.editedItem.organizationName;
      }
      return this.diskGetter.filter(
        disk =>
          disk.virtualMachineName === "" && disk.organizationName === orgName
      );
    },

    formTitle() {
      return this.editedIndex === -1 ? "New VM" : "Edit VM";
    },

    editDisabled() {
      return this.editedIndex != -1;
    },

    orgNames() {
      return this.$store.state.orgs.organizations.map(i => i.name);
    },

    catNames() {
      return this.$store.state.categories.VMCategories.map(i => i.name);
    },

    isHidden() {
      return this.hidden;
    },

    isTurnedOn() {
      return this.editedItem.active ? "Turned on" : "Turned off";
    }

  },

  watch: {
    dialog(val) {
      val || this.close();
    }
  },

  methods: {
    ...mapActions({
      addVmAction: "vms/add",
      editVmAction: "vms/edit",
      deleteVmAction: "vms/delete",
      loadDiscs: "disc/load",
      showSnackbar: "snackbar/showSnackbar",
      getActivitiesForVm: "vms/getActivitiesForVm",
      editDiscAction: "disc/edit"
    }),

    /**
     * prikazuje dijalog za add/edit
     */
    show() {
      this.dialog = true;
    },

    /**
     * Zatvara dijalog za add/edit
     */
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    /**
     * Prikazuje dijalog sa aktivnostima
     */
    showActivities(item) {
      this.getActivitiesForVm(item.name)
        .then(activities => {
          this.activities = this.transformDates(activities);
          this.vmToShowActivitiesFor = item;
          this.vmToShowActivitiesForIndex = this.getIndexOfVm(item.name);
          this.activitiesDialog = true;
        })
        .catch(error => {
          this.showSnackbar(["Error occured: " + error, "error", "bottom"]);
        });
    },

    /**
     * Zatvara dijalog sa aktivnostima
     */
    closeActivitiesDialog() {
      this.activitiesDialog = false;
      Object.assign(this.activities, []);
    },

    /**
     * Pretvara datume u format dd/mm/yyyy hh:MM
     * Treba za prikaz u Activities komponenti
     */
    transformDates(newActivities) {
      let res = [];
      newActivities.forEach(a => {
        let start =
          a.startTime.date.day +
          "/" +
          a.startTime.date.month +
          "/" +
          a.startTime.date.year +
          " " +
          a.startTime.time.hour +
          ":" +
          a.startTime.time.minute;

        let end = "";
        if (a.endTime) {
          end =
            a.endTime.date.day +
            "/" +
            a.endTime.date.month +
            "/" +
            a.endTime.date.year +
            " " +
            a.endTime.time.hour +
            ":" +
            a.endTime.time.minute;
        }
        res.push({ id: a.id, start, end });
      });

      return res;
    },

    editItem(item) {
      this.editedIndex = this.getIndexOfVm(item.name);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    getIndexOfVm(vmName) {
      return this.vmsGetter.findIndex(x => x.name === vmName);
    },

    save() {
      if (this.isAdmin) {
        this.editedItem.organizationName = this.getUser.organizationName;
      }

      if (!this.validateForm()) {
        this.showSnackbar([
          "All input fields must be filled out!",
          "info",
          "bottom"
        ]);
        return;
      }

      // kad izabere kategoriju iz dropdown-a postavi se samo ime
      // kategorije, pa ovde dodeljujem celu kategoriju vm-u
      this.editedItem.category = this.getCategoryByName(
        this.editedItem.category.name
      );
      this.editedItem.categoryName = this.editedItem.category.name;

      if (this.editedIndex > -1) {
        this.editVm();
      } else {
        this.addVm();
      }

      this.close();
    },

    addSelectedDiscsToVm() {
      this.selectedDiscs.forEach(disc => {
        let discIndex = this.diskGetter.findIndex(d => d.name === disc.name);
        disc.virtualMachineName = this.editedItem.name;
        this.editDiscAction([discIndex, disc]);
      });

      this.selectedDiscs = [];
    },

    validateForm() {
      if (
        this.editedItem.organizationName.trim() === "" ||
        this.editedItem.name.trim() === "" ||
        this.editedItem.category.name.trim() === ""
      ) {
        return false;
      }

      return true;
    },

    getCategoryByName(categoryName) {
      return this.categoriesGetter.find(
        category => category.name === categoryName
      );
    },

    addVm() {
      this.addVmAction(this.editedItem)
        .then(() => {
          this.addSelectedDiscsToVm();
          this.showSnackbar([
            "Virtual Machine successfully added!",
            "success",
            "bottom"
          ]);
        })
        .catch(err => this.showSnackbar(["Error: " + err, "error", "bottom"]));
    },

    editVm() {
      this.editVmAction([this.editedIndex, this.editedItem])
        .then(() => {
          this.addSelectedDiscsToVm();
          this.showSnackbar([
            "Virtual Machine successfully edited!",
            "success",
            "bottom"
          ]);
        })
        .catch(err => this.showSnackbar(["Error: " + err, "error", "bottom"]));
    },

    deleteItem(vm) {
      const vmIndex = this.getIndexOfVm(vm.name);
      if (confirm("Are you sure you want to delete this Virtual Machine? ")) {
        this.deleteVmAction([vmIndex, vm.name])
          .then(() => {
            this.loadDiscs();
            this.showSnackbar([
              "Virtual Machine successfully deleted!",
              "success",
              "bottom"
            ]);
            this.close();
          })
          .catch(err =>
            this.showSnackbar(["Error: " + err, "error", "bottom"])
          );
      }
    },

    hide() {
      this.hidden = !this.hidden;
    }
  }
};
</script>