
<template>
  <v-card outline block class="ma-3" hover>
    <v-card-title class="blue-grey white--text">
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        :disabled="isHidden"
        append-icon="mdi-search-web"
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
      :items="getAllUsers.filter(x => x.role != 'SuperAdmin')"
      :items-per-page="5"
    >
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-dialog v-model="dialog"  width="50%">
          <template v-slot:activator="{ on }">
            <v-btn color="blue-grey darken-1 white--text" dark class="mb-2" v-on="on">New User</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.firstName" label="First name"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.lastName" label="Last Name"></v-text-field>
                  </v-col>
                      <!-- :disabled="emailDisabled" -->
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      v-model="editedItem.email"
                      label="Email"
                      type="email"
                    ></v-text-field>
                  </v-col>

                  <v-col v-if="isSuper" cols="12" sm="6" md="4">
                    <v-select
                      :items="orgNames"
                      :disabled="emailDisabled"
                      v-model="editedItem.organizationName"
                      label="Organization"
                    ></v-select>
                  </v-col>

                  <v-col :hidden="emailDisabled" cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.password" label="Password" type="password"></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-combobox
                      append-icon="mdi-account-tie"
                      :items="options"
                      v-model="editedItem.role"
                      label="Role"
                    ></v-combobox>
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
      <template v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-lead-pencil</v-icon>
        <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>



<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      headers: [
        { text: "First Name", align: "left", value: "firstName" },
        { text: "Last Name", value: "lastName" },
        { text: "Email", value: "email" },
        { text: "Role", value: "role" },
        { text: "Organization", value: "organizationName" },
        { text: "Actions", value: "action", sortable: false }
      ],

      search: "",
      hidden: false,
      options: ["User", "Admin"],
      dialog: false,
      editedIndex: -1,
      editedItem: {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        role: "",
        organizationName: "",
        id : "",
      },
      defaultItem: {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        role: "",
        organizationName: "",
        id : "",
      }
    };
  },

  computed: {
    ...mapGetters({
      isSuper: "users/isSuper",
      isAdmin: "users/isAdmin",
      getAllUsers : "users/getAllUsers",
      getUser: "users/getUser"
    }),

    formTitle() {
      return this.editedIndex === -1 ? "New User" : "Edit User";
    },

    emailDisabled() {
      return this.editedIndex != -1;
    },

    orgNames() {
      return this.$store.state.orgs.organizations.map(i => i.name);
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
      showSnackbar: "snackbar/showSnackbar"
    }),

    // korisnik menja nekog usera
    editItem(item) {
      this.editedIndex = this.$store.state.users.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    // korisnik brise usera
    deleteItem(item) {
      const index = this.$store.state.users.users.indexOf(item);

      if (this.$store.state.users.loggedUser.email == item.email) {
        this.showSnackbar(["You can't delete yourself!", "info", "bottom"]);
        return;
      }

      if (confirm("Are you sure you want to delete this item?")) {
        this.$store
          .dispatch("users/delete", [index, item.email])
          .then(() => {
            this.showSnackbar([
              "User successfully deleted!",
              "success",
              "bottom"
            ]);
            this.close();
          })
          .catch(err =>
            this.showSnackbar([err.response.data,"error", "bottom"])
          );
      }
    },

    // korisnik odustao od izmene
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    // izmena/dodavanje novog usera
    save() {
      if (this.isAdmin) {
        this.editedItem.organizationName = this.getUser.organizationName;
      }
      
      if (!this.validate()) {
        this.showSnackbar([
          "All input fields must be filled out!",
          "info",
          "bottom"
        ]);
        return;
      }

      if (this.editedIndex > -1) {
        this.$store
          .dispatch("users/edit", [this.editedIndex, this.editedItem])
          .then(() => {
            this.close();
            this.showSnackbar([
              "User successfully edited!",
              "success",
              "bottom"
            ]);
            
          })
          .catch(err =>
            this.showSnackbar([err.response.data,"error", "bottom"])
          );
      } else {
        this.$store
          .dispatch("users/add", this.editedItem)
          .then(() => {
            this.$store.dispatch('users/load')
            .then( () => {
              this.close();
              this.showSnackbar([
                "User successfully added!",
                "success",
                "bottom"
              ]);
            })
          })
          .catch(err =>
            this.showSnackbar([err.response.data,"error", "bottom"])
          );
      }
    },

    validate() {
      if (
        this.editedItem.firstName.trim() === "" ||
        this.editedItem.lastName.trim() === "" ||
        this.editedItem.email.trim() === "" ||
        this.editedItem.password.trim() === "" ||
        this.editedItem.role.trim() === "" ||
        this.editedItem.organizationName.trim() === "" ||
        (this.editedItem.role != "Admin" && this.editedItem.role != "User")
      ) {
        return false;
      }

      return true;
    },

    hide() {
      this.hidden = !this.hidden;
    }
  }
};
</script>