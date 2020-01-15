<template>
  <div class="about">
    <v-container>
    <h1  class="my-5 subheading grey--text">Admin Page</h1>

    <v-layout row wrap > 
        
        <v-flex xs12 sm6>
          <VMCard/> 
        </v-flex>

        <v-flex v-if="this.isSuper" xs12 sm6>
          <VMCategoryCard/> 
        </v-flex>

        <v-flex xs12 sm6>
          <DiskCard/> 
        </v-flex>

        <v-flex xs12 sm6>
          <UserCard/> 
        </v-flex>

        <v-flex v-if="this.isSuper" xs12 sm6>
          <OrganizationCard/> 
        </v-flex>

      </v-layout>

    <!-- <div class="grid-container">
      <VMCard class="item"/>
      <VMCategoryCard class="item"/>
      <DiskCard class="item"/>
      <UserCard class="item"/>
      <OrganizationCard class="item"/>
    </div> -->

    </v-container> 
  </div> 
</template>

<script>
import VMCard from "./cards/VMCard";
import DiskCard from "./cards/DiskCard";
import VMCategoryCard from "./cards/VMCategoryCard";
import OrganizationCard from "./cards/OrganizationCard";
import UserCard from "./cards/UserCard";
import { mapGetters } from 'vuex';

export default {
  components: {
    VMCard,
    VMCategoryCard,
    DiskCard,
    OrganizationCard,
    UserCard
  },

  created() {
    this.$store.dispatch("loadAllData");
  },

  computed: {
    ...mapGetters({
      isAdmin: "users/isAdmin",
      isSuper: "users/isSuper"
    })
  },
};
</script>

<style scoped>
.grid-container {
  column-count: 2;
  -moz-column-count: 2;
  -webkit-column-count: 2;
}

.item {
  display: inline-block;
  width:100%;
}
</style>
