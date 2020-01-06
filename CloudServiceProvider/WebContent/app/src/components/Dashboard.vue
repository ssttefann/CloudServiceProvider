<template>
  <div class="about">
    <v-container class="my-5">
      <h1 class="subheading grey--text">Dashboard</h1>

      <v-layout row wrap>
        <v-flex xs12 sm6 md6>
          <VMCard />
        </v-flex>

        <v-spacer></v-spacer>

        <v-flex xs12 sm6 md6>
          <DiskCard />
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import VMCard from "./cards/VMCard";
import DiskCard from "./cards/DiskCard";

export default {
  components: {
    VMCard,
    DiskCard
  },

  //trebalo bi ovde da se ucitaju sve kolekcije za prikaz
  created() {
    // this.$store.commit('logUser');
    this.$store.dispatch("loadAllData");
  },


};
</script>

