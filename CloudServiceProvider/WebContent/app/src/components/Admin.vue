<template>
  <div class="about">
    <v-container>
          <h1 class="my-5 subheading grey--text">Admin Page</h1>

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
    this.$store.commit("loadAllData");
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
