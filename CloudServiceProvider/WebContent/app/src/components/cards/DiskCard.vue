
<template>
  <v-card outline block class="ma-3" hover>
    <v-card-title class="blue-grey white--text">
      Discs
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
    <v-data-table
      :hidden="isHidden"
      class="ma-6"
      :search="search"
      :headers="headers"
      :items="discsGetter"
    >
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-if="isSuper || isAdmin" v-slot:activator="{ on }">
            <v-btn color="blue-grey darken-1 white--text" dark class="mb-2" v-on="on">New Disc</v-btn>
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
                      label="Disc name"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.capacity"
                      type="number"
                      min="1"
                      label="Capacity"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-combobox
                      append-icon="mdi-minidisc"
                      :items="options"
                      v-model="editedItem.type"
                      label="Type"
                    ></v-combobox>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select
                      :items="vmNames"
                      v-model="editedItem.virtualMachineName"
                      label="Virtual Machine"
                    ></v-select>
                  </v-col>
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
      <template v-if="isSuper || isAdmin" v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-lead-pencil</v-icon>
        <v-icon small @click="deleteDisc(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>



<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      hidden: false,
      headers: [
        { text: "Name", align: "left", value: "name" },
        { text: "Capacity", value: "capacity" },
        { text: "Type", value: "type" },
        { text: "Virtual Machine", value: "virtualMachineName" },
        { text: "Actions", value: "action", sortable: false }
      ],

      options: ["SSD", "HDD"],

      search: "",
      dialog: false,
      editedIndex: -1,
      editedItem: {
        name: "",
        capacity: 0,
        type: "",
        virtualMachineName: ""
      },
      defaultItem: {
        name: "",
        capacity: 0,
        type: "",
        virtualMachineName: ""
      }
    };
  },

  computed: {
    ...mapGetters({
      discsGetter: "disc/getDiscs",
      isAdmin: "users/isAdmin",
      isSuper: "users/isSuper",
    }),

    formTitle() {
      return this.editedIndex === -1 ? "New Disc" : "Edit Disc";
    },

    nameDisabled() {
      return this.editedIndex != -1;
    },

    vmNames() {
      return this.$store.state.vms.virtualMachines.map(i => i.name);
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
    initialize() {},

    ...mapActions({
      addDiscAction: "disc/add",
      editDiscAction: "disc/edit",
      deleteDiscAction: "disc/delete"
    }),

    editItem(item) {
      this.editedIndex = this.getIndexOfDisc(item.name);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    getIndexOfDisc(discName) {
      return this.discsGetter.findIndex(x => x.name === discName);
    },

    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    deleteDisc(disc) {
      const discIndex = this.getIndexOfDisc(disc.name);
      if (confirm("Da li ste sigurni da zelite da obrisete ovaj disk")) {
        this.deleteDiscAction([discIndex, disc.name])
          .then(() => {
            this.close();
            alert("Disk je uspesno obrisan");
          })
          .catch(error => alert(error));
      }
    },

    save() {
      if (!this.validate()) {
        alert("Sva polja moraju da budu popunjena");
        return;
      }

      if (this.editedIndex > -1) {
        this.editDisc();
      } else {
        this.addDisc();
      }
    },

    validate() {
      if (
        this.editedItem.name.trim() === "" ||
        this.editedItem.capacity === 0 ||
        (this.editedItem.type !== "SSD" && this.editedItem.type !== "HDD")
      ) {
        return false;
      }
      return true;
    },

    editDisc() {
      this.editDiscAction([this.editedIndex, this.editedItem])
        .then(() => {
          this.close();
          alert("Disk uspesno izmenjen");
        })
        .catch(error => {
          alert(error);
        });
    },

    addDisc() {
      this.addDiscAction(this.editedItem)
        .then(() => {
          this.close();
          alert("Disk uspesno dodat");
        })
        .catch(error => {
          alert(error);
        });
    },

    hide() {
      this.hidden = !this.hidden;
    }
  }
};
</script>