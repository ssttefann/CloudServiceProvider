
<template>
  <v-card outline block class="ma-3 pa-6" hover>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-search-web"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>

    <!-- Tabela za prikaz svih elemenata -->
    <v-data-table :search="search" :headers="headers" :items="this.$store.state.users.users">

      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
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
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field :disabled=emailDisabled v-model="editedItem.email" label="Email" type="email"></v-text-field>
                  </v-col>
                  
                 <v-col cols="12" sm="6" md="4">
                    <v-text-field :disabled=emailDisabled v-model="editedItem.organizationName" label="Organization" ></v-text-field>
                  </v-col>

                  <v-col :hidden=emailDisabled cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.password" label="Password" type="password"></v-text-field>
                  </v-col>

                  <v-col cols="12" sm="6" md="4">
                    <v-combobox append-icon="mdi-account-tie" :items="options" v-model="editedItem.role" label="Role"> 
                    </v-combobox>
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

      search : "",
      options : ["User", "Admin"],
      dialog: false,
      editedIndex: -1,
      editedItem: {
          firstName : "",
          lastName : "",
          email : "",
          password : "",
          role : "",
          organizationName : ""
      },
      defaultItem: {
          firstName : "",
          lastName : "",
          email : "",
          password : "",
          role : "",
          organizationName : ""
      }
    };
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New User" : "Edit User";
    },

    emailDisabled() {
      return this.editedIndex != -1;
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
    
    // za sada nista ne radi
    initialize() {},

    // korisnik menja nekog usera
    editItem(item) {
      this.editedIndex = this.$store.state.users.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    // korisnik brise usera
    deleteItem(item) {
      const index = this.$store.state.users.users.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.$store.state.users.users.splice(index, 1);
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
      if (this.editedIndex > -1) {
        Object.assign(
          this.$store.state.users.users[this.editedIndex],
          this.editedItem
        );
      } else {
          this.$store.state.users.users.push(this.editedItem);
        // this.$store.commit('addVM',this.editedItem);
      }
      this.close();
    }
  }
};
</script>