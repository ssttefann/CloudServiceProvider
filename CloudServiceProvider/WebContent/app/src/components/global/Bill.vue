<template>
  <v-card>
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
            :close-on-content-click="true"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="startDate"
                label="Start date"
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
            :close-on-content-click="true"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on }">
              <v-text-field
                v-model="endDate"
                label="End date"
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
          <v-btn @click="calculatePrice">Calculate</v-btn>
        </v-col>
      </v-row>
      <v-spacer></v-spacer>
      Total: {{calculatedPrice}} &euro;
    </v-footer>
  </v-card>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";
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
        { text: "Active hours", value: "activeHours" },
        { text: "Calculated price", value: "price" }
      ],
      items: [],
      startDate: null,
      endDate: null
    };
  },
  methods: {
    ...mapActions({
      showSnackbar: "snackbar/showSnackbar"
    }),

    validateInputs() {
      const date1 = Date.parse(this.startDate);
      const date2 = Date.parse(this.endDate);
      if (!date1 || !date2 ||  date1 >= date2) {
        return false;
      }

      return true;
    },

    calculatePrice() {
      if (!this.validateInputs()) {
        this.showSnackbar(["pa ne moz to tako bokte", "error", "bottom"]);
        return;
      }

      axios
        .get("/rest/bill/" + Date.parse(this.startDate) + "/" + Date.parse(this.endDate))
        .then(response => {
          this.items = response.data;
            if(this.items.length === 0){
                this.showSnackbar(["No bills for this period", "info", "bottom"]);
            }

          this.calculatedPrice = 0;
          for(let item of this.items){
              this.calculatedPrice += item.price;
          }

        })
        .catch(error => alert(error));
    }
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