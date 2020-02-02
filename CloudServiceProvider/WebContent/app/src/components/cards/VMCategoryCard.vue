
<template>
  <v-card outline block class="ma-3" hover>
    <v-card-title class="blue-grey white--text">
      VM Categories
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
      :items-per-page = 5
      :headers="headers"
      :items="this.$store.state.categories.VMCategories"
    >
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-dialog v-model="dialog" width="50%">
          <template v-slot:activator="{ on }">
            <v-btn v-if="isSuper" color="blue-grey darken-1 white--text" dark class="mb-2" v-on="on">New Category</v-btn>
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
                      v-model="editedItem.name"
                      label="Category"
                      :disabled="editDisabled"
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.cores" label="Cores" type="number" min="1"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.RAM" label="RAM" type="number" min="1"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.GPU" label="GPU" type="number" min="1"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions >
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>

      <!-- Template za brisanje -->
      <template v-if="isSuper" v-slot:item.action="{ item }">
        <v-icon small class="mr-2" @click="editItem(item)">mdi-lead-pencil</v-icon>
        <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>



<script>
import { mapActions, mapGetters } from 'vuex';
export default {
  data() {
    return {
      hidden: false,
      headers: [
        { text: "Name", value: "name" },
        { text: "Cores", value: "cores" },
        { text: "RAM", value: "RAM" },
        { text: "GPU", value: "GPU" },
        { text: "Actions", value: "action", sortable: false }
      ],

      search: "",
      dialog: false,
      editedIndex: -1,
      editedItem: {
        name: "",
        cores: 1,
        RAM: 16,
        GPU: 1
      },
      defaultItem: {
        name: "",
        cores: 1,
        RAM: 16,
        GPU: 1
      }
    };
  },

  computed: {
    ...mapGetters({
      categoriesGetter: "categories/getAll",
      isAdmin: "users/isAdmin",
      isSuper: "users/isSuper"
    }),

    formTitle() {
      return this.editedIndex === -1 ? "New Category" : "Edit Category";
    },

    editDisabled() {
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
      addCategoryAction: "categories/add",
      editCategoryAction: "categories/edit",
      deleteCategoryAction: "categories/delete",
      showSnackbar : "snackbar/showSnackbar",
    }),

    // za sada nista ne radi
    initialize() {
      if (!this.isSuper){
        let last = this.headers[this.headers.length - 1];
        if (last.text == "Actions")
          this.headers.splice(-1,1);
      }
    },

    // korisnik menja neku VM
    editItem(item) {
      this.editedIndex = this.getIndexOfCategory(item.name);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    getIndexOfCategory(categoryName) {
      return this.categoriesGetter.findIndex(x => x.name === categoryName);
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
        this.showSnackbar(["All fields must be filled out!", "info", "bottom"])
        return;
      }

      if(this.editedItem.cores <= 0 || this.editedItem.RAM <= 0 || this.editedItem.GPU < 0){
        this.showSnackbar([
          "Category must have positive values!",
          "error",
          "bottom"
        ]);
        return;    
      }

      if (this.editedIndex > -1) {
        this.editCategory();
      } else {
        this.addCategory();
      }
    },

    validateForm() {
      if (this.editedItem.name.trim() === "") {
        return false;
      }
      return true;
    },

    addCategory() {
      this.addCategoryAction(this.editedItem)
        .then(() => {
          this.close();
          this.showSnackbar(["Category successfully added!", "success", "bottom"])
        })
        .catch(err => this.showSnackbar([err.response.data,"error", "bottom"]));
    },

    editCategory() {
      this.editCategoryAction([this.editedIndex, this.editedItem])
        .then(() => {
          this.close();
          this.showSnackbar(["Category successfully edited!", "success", "bottom"]);
        })
        .catch(err => this.showSnackbar([err.response.data, "error", "bottom"]));
    },    

    deleteItem(category) {
      const categoryIndex = this.getIndexOfCategory(category.name);
      if(confirm("Are you sure you wnat to delete this Category ? ")){
        this.deleteCategoryAction([categoryIndex, category.name])
          .then(() => {
            this.close();
            this.showSnackbar(["Category successfully deleted!", "success", "bottom"]);
          })
        .catch(err => this.showSnackbar([err.response.data, "error", "bottom"]));
      }

    },

    hide() {
      this.hidden = !this.hidden;
    }
  }
};
</script>