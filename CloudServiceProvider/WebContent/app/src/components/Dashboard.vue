<template>
  <div class="about">
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
  </div>
</template>

<script>
import VMCard from "./cards/VMCard";
import DiskCard from "./cards/DiskCard";
import { mapActions } from 'vuex';

export default {
  components: {
    VMCard,
    DiskCard
  },

  methods: {
    ...mapActions({
      loadCategories: "categories/load",
      loadVms: "vms/load",
      loadDiscs: "disc/load"
    })
  },

  async created() {
    await this.loadCategories();
    await this.loadDiscs();
    await this.loadVms();
  },


};
</script>

<style scoped>
  .about {
    padding: 15px;
  }
</style>