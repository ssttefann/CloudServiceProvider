
<template>
  <v-card outline block class="ma-3" hover>
    <v-card-title class="blue-grey white--text">
      Organizations
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        :disabled="isHidden"
        dark
        color="white"
        append-icon="mdi-search-web"
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
      :items="this.orgGetter"
    >
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on }">
            <v-btn
              color="blue-grey darken-1 white--text"
              dark
              class="mb-2"
              v-on="on"
            >New Organization</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.name" label="Name" :disabled="nameDisabled"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.description" label="Description"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-file-input ref="iconUpload" prepend-icon="mdi-photo" @change="fileSubmited" placeholder="Ikonica organizacije"></v-file-input>
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
      </template>
    </v-data-table>
  </v-card>
</template>



<script>
import { mapGetters, mapActions } from 'vuex';
export default {
  data() {
    return {
      hidden : false,
      headers: [
        { text: "Name", value: "name" },
        { text: "Description", value: "description" },
        { text: "Logo", value: "logo" },
        { text: "Actions", value: "action", sortable: false }
      ],
      
      search : '',
      dialog: false,
      editedIndex: -1,
      editedItem: {
          name: "",
          description: "",
          logo : "",
      },
      defaultItem: {
          name: "",
          description: "",
          logo : "",
      }
    };
  },

  computed: {

    ...mapGetters({
      orgGetter: "orgs/orgGetter",
    }),

    formTitle() {
      return this.editedIndex === -1 ? "New Organization" : "Edit Organization";
    },

    nameDisabled() {
      return this.editedIndex != -1;
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
      addOrgAction: "orgs/add",
      editOrgAction: "orgs/edit",
    }),

    // za sada nista ne radi
    initialize() {},

    // korisnik menja neku VM
    editItem(item) {
      this.editedIndex = this.getIndexOfOrg(item.name);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    getIndexOfOrg(orgName) {
      return this.orgGetter.findIndex(x => x.name === orgName);
    },

    fileSubmited(file){
      this.editedItem.logo = file.name;
    },

    // korisnik odustao od izmene
    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    // izmenjena/dodata nova VM
    save() {
      if (!this.validateForm()) {
        alert("Sva polja moraju biti popunjena");
        return;
      }

      if (this.editedIndex > -1) {
        this.editOrg();
      } else {
        this.addOrg();
      }

      this.$refs.iconUpload.value = undefined;
      this.close();
    },

    validateForm(){
      if (
        this.editedItem.name.trim() === ""
      ) {
        return false;
      }

      return true;
    },

    editOrg() {
      this.editOrgAction([this.editedIndex, this.editedItem])
        .then(() => {
          alert("Organizacija uspesno izmenjena");
          this.close();
        })
        .catch(error => alert(error));
    },

    addOrg() {
      this.addOrgAction(this.editedItem)
        .then(() => {
          this.close();
          alert("Organizacija uspesno dodata");
        })
        .catch(err => alert("Greska " + err));
    },

    hide() {
      this.hidden = !this.hidden;
    }
  }
};
</script>