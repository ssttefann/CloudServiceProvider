<template>
  <div class="cont">

    <h1 v-if="isAdmin" class="my-5 subheading grey--text">Admin Page</h1>
    <h1 v-if="isSuper" class="my-5 subheading grey--text">Super Admin Page</h1>
    
    <!-- <v-container class="my-5"> -->

    <v-layout row > 

        <v-flex xs12 sm12 md12 lg6>
          <VMCard/> 
        </v-flex>

        <v-flex xs12 sm12 md12 lg6>
          <VMCategoryCard/> 
        </v-flex>

        <v-flex xs12 sm12 md12 lg6>
          <DiskCard/> 
        </v-flex>

        <v-flex xs12 sm12 md12 lg6>
          <UserCard/> 
        </v-flex>

        <v-flex v-if="this.isSuper" xs12 sm12 md12 lg6>
          <OrganizationCard/> 
        </v-flex>

      </v-layout>

    <!-- </v-container>  -->
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
  .cont {
    padding: 15px;
  }
</style>
