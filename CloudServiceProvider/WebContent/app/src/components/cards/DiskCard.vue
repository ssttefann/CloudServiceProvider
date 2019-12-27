
<template>
  <v-card outline block class="ma-3 pa-6" hover>
    <v-card-title>
      Disks
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-search-web"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table :search="search" :headers="headers" :items="this.$store.state.disks">
        
      <!-- Template za editovanje/dodavanje nove -->
      <template v-slot:top>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on }">
            <v-btn color="blue-grey darken-1 white--text" dark class="mb-2" v-on="on">New Disk</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field :disabled=nameDisabled v-model="editedItem.name" label="Disk name"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.capacity" label="Capacity"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-combobox append-icon="mdi-minidisc" :items="options" v-model="editedItem.type" label="Type"> 
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
        { text: "Name", align: "left", value: "name" },
        { text: "Capacity", value: "capacity" },
        { text: "Type", value: "type" },
        { text: "Actions", value: "action", sortable: false }
      ],

      options : ["SSD", "HDD"],

      search : "",
      dialog: false,
      editedIndex: -1,
      editedItem: {
        name: "", capacity : 0, type : ""
      },
      defaultItem: {
        name: "", capacity : 0, type : ""
      }
    };
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "New VM" : "Edit VM";
    },

    nameDisabled() {
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
    initialize() {},

    editItem(item) {
      this.editedIndex = this.$store.state.disks.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem(item) {
      const index = this.$store.state.disks.indexOf(item);
      confirm("Are you sure you want to delete this item?") &&
        this.$store.state.disks.splice(index, 1);
    },

    close() {
      this.dialog = false;
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300);
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(
          this.$store.state.disks[this.editedIndex],
          this.editedItem
        );
      } else {
        this.$store.commit('addDisk',this.editedItem);
      }
      this.close();
    }
  }
};
</script>