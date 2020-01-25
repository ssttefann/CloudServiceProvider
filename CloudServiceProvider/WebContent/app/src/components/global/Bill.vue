<template>
  <div class="cont">

    <h1 class="my-5 subheading grey--text">Billing</h1>
    
    <v-layout row > 

        <v-flex xs12 sm12 md12 lg12>
          <BillCard/> 
        </v-flex>

      </v-layout>

  </div> 
</template>

<script>
import BillCard from "../cards/BillCard";
import { mapGetters } from 'vuex';

export default {
  components: {
    BillCard
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
