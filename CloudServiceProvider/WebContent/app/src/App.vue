<template>
  <v-app>
    <Toolbar/>
    <v-content class="dob">
      <router-view> </router-view>
    </v-content>
    <Footer/>
  </v-app>
</template>

<script>
import Toolbar from './components/global/Toolbar'
import Footer from './components/global/Footer'

export default {
  name: 'App',
  components: {
    Toolbar, Footer
  },
  data: () => ({
    //
  }),

  created() {
    // ako se refreshuje stranica, ili ako se otvori u drugom tabu
    // pogledaj da li postojao vec ulogovani korisnik
    // this.$store.commit('logUser');

  },

  methods : {
    
  }
};
</script>


<style  scoped>

  .dob {
    margin: 15px;
  }

</style>