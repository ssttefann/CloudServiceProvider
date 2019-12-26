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
import Toolbar from './components/Toolbar'
import Footer from './components/Footer'

export default {
  name: 'App',
  components: {
    Toolbar, Footer
  },
  data: () => ({
    //
  }),

  mounted() {

    // ako se refreshuje stranica, ili ako se otvori u drugom tabu
    // pogledaj da li postojao vec ulogovani korisnik
    this.$store.commit('logUser');
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