
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
      class="ma-6"
      :search="search"
      :headers="headers"
      :items="this.$store.state.vms.virtualMachines"
    >
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-if="isAdmin" v-slot:activator="{ on }">
            <v-btn color="blue-grey darken-1 white--text" dark class="mb-2" v-on="on">New VM</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      :disabled="nameDisabled"
                      v-model="editedItem.name"
                      label="VM name"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select :items="catNames" v-model="editedItem.category.name" label="Category"></v-select>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select
                      :items="orgNames"
                      :disabled="nameDisabled"
                      v-model="editedItem.organizationName"
                      label="Organization"
                    ></v-select>
                  </v-col>
                  <!-- <v-row cols="12" sm="6" md="4">
                    <DiskCard/>
                  </v-row>-->
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
      </template>

      <!-- Template za brisanje -->
      <template v-if="isAdmin" v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-lead-pencil</v-icon>
        <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>


<script>
import { mapActions, mapGetters } from "vuex";

export default {
  components: {
    // DiskCard
  },

  data() {
    return {
      isAdmin: this.$store.getters["users/isAdmin"],
      hidden: false,
      headers: [
        { text: "Name", align: "left", value: "name" },
        { text: "Cores", value: "category.cores" },
        { text: "RAM", value: "category.RAM" },
        { text: "GPU", value: "category.GPU" },
        { text: "Organization", value: "organizationName" },
        { text: "Actions", value: "action", sortable: false }
      ],

      search: "",
      dialog: false,
      editedIndex: -1,
      editedItem: {
        name: "",
        organizationName: "",
        category: {
          name: "",
          cores: 0,
          RAM: 0,
          GPU: 0
        }
      },
      defaultItem: {
        name: "",
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
      vmsGetter: "vms/getAll",
    }),

    formTitle() {
      return this.editedIndex === -1 ? "New VM" : "Edit VM";
    },

    nameDisabled() {
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
    }
  },

  watch: {
    dialog(val) {
      val || this.close();
    }
  },

  created() {
    this.initialize();
  },

  methods: {
    ...mapActions({
      addVmAction: "vms/add",
      editVmAction: "vms/edit",
      deleteVmAction: "vms/delete"
    }),

    initialize() {},

    editItem(item) {
      this.editedIndex = this.getIndexOfVm(item.name);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    
    getIndexOfVm(vmName) {
      return this.vmsGetter.findIndex(x => x.name === vmName);
    },

    deleteItem(item) {
      const index = this.$store.state.vms.virtualMachines.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.$store.state.vms.virtualMachines.splice(index, 1);
    },

    // korisnik odustao od izmene
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    save() {
      if (!this.validateForm()) {
        alert("Sva polja moraju biti popunjena");
        return;
      }

      this.editedItem.category = this.getCategoryByName(
        this.editedItem.category.name
      );

      if (this.editedIndex > -1) {
        this.editVm();
      } else {
        this.addVm();
      }
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

    editVm() {
      this.editVmAction([this.editedIndex, this.editedItem])
        .then(() => {
          alert("Virtuelna masina uspesno izmenjena");
          this.close();
        })
        .catch(error => alert(error));
    },

    addVm() {
      this.addVmAction(this.editedItem)
        .then(() => {
          this.close();
          alert("Virtuelna masina uspesno dodata");
        })
        .catch(err => alert("Greska " + err));
    },

    hide() {
      this.hidden = !this.hidden;
    }
  }
};
</script>