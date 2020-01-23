<template>
  <v-card height="100%">
    <v-card-title>Bill</v-card-title>
    <v-card-text>
      <v-data-table :items-per-page="10" width="100%" :items="items" :headers="headers"></v-data-table>
    </v-card-text>

    <v-spacer></v-spacer>
    <v-footer>
      <v-row>
        <v-col>
          <v-menu
            ref="menu1"
            v-model="menu1"
            :close-on-content-click="false"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="startDate"
                label="Pick date"
                placeholder="Click to pick the start date"
                prepend-icon="mdi-event"
                readonly
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="startDate" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu1 = false">Cancel</v-btn>
              <v-btn text color="primary" @click="$refs.menu1.save(startDate)">OK</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>
        <v-col>
          <v-menu
            ref="menu2"
            v-model="menu2"
            :close-on-content-click="false"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="endDate"
                label="Pick date"
                placeholder="Click to pick the end date"
                prepend-icon="mdi-event"
                readonly
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker v-model="endDate" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="menu2 = false">Cancel</v-btn>
              <v-btn text color="primary" @click="$refs.menu2.save(endDate)">OK</v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>
        <v-col class="center">
          <v-btn>Calculate</v-btn>
        </v-col>
      </v-row>
      <v-spacer></v-spacer>
      Total: {{calculatedPrice}}
    </v-footer>
  </v-card>
</template>

<script>
export default {
  name: "Bill",
  data() {
    return {
      menu1: false,
      menu2: false,
      datePickerDialog: false,
      calculatedPrice: 0,
      headers: [
        { text: "Type", value: "type" },
        { text: "Name", value: "name" },
        { text: "Deleted", value: "deleted" },
        { text: "Calculated price", value: "price" }
      ],
      items:[
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
          {type: "type", name: "name", deleted: "yes", price: "12"},
      ],
      startDate: null,
      endDate: null,
    };
  },
  methods: {
  }
};
</script>

<style>
.grey-backgroud {
  background-color: lightgray;
}

.white-backgound {
  background-color: white;
}

.center {
  display: flex;
  align-items: center;
  justify-content: left;
}
</style>