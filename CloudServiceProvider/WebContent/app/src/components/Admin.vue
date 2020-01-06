<template>
  <div class="about">
    <v-container>
    <h1 class="my-5 subheading grey--text">Admin Page</h1>

    <!-- <v-layout row wrap > 
        
        <v-col xs12 sm6 md6 >
          <VMCard/> 
        </v-col>

        <v-col xs12 sm6 md6 >
          <VMCategoryCard/> 
        </v-col>

        <v-col xs12 sm6 md6 >
          <DiskCard/> 
        </v-col>

        <v-col xs12 sm6 md6 >
          <UserCard/> 
        </v-col>

        <v-col xs12 sm6 md6 >
          <OrganizationCard/> 
        </v-col>

      </v-layout> -->

    <div class="grid-container">
      <VMCard class="item"/>
      <VMCategoryCard class="item"/>
      <DiskCard class="item"/>
      <UserCard class="item"/>
      <OrganizationCard class="item"/>
    </div>

    </v-container> 
  </div> 
</template>

<script>
import VMCard from "./cards/VMCard";
import DiskCard from "./cards/DiskCard";
import VMCategoryCard from "./cards/VMCategoryCard";
import OrganizationCard from "./cards/OrganizationCard";
import UserCard from "./cards/UserCard";

export default {
  components: {
    VMCard,
    VMCategoryCard,
    DiskCard,
    OrganizationCard,
    UserCard
  },

  //trebalo bi ovde da se ucitaju sve kolekcije za prikaz
  created() {
    // this.$store.commit('logUser');
    this.$store.dispatch("loadAllData");
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
