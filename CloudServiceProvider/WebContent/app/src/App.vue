<template>
  <v-app>
    <Toolbar />
    <v-content class="dob">
      <v-snackbar
        v-model="getSnackBar"
        :color="getSnackBarColor"
        :timeout="3000"
        :top="true"
        :vertical="mode === 'vertical'"
      >
        {{ getSnackBarText }}
        <v-btn dark text @click="hideSnackBarAction">Close</v-btn>
      </v-snackbar>
      <router-view></router-view>
    </v-content>
    <Footer />
  </v-app>
</template>

<script>
import Toolbar from "./components/global/Toolbar";
import Footer from "./components/global/Footer";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "App",
  components: {
    Toolbar,
    Footer
  },
  computed: {
    ...mapGetters({
      getSnackBar: "snackBar/getSnackBar",
      getSnackBarColor: "snackBar/getSnackBarColor",
      getSnackBarText: "snackBar/getSnackBarText"
    })
  },

  methods: {
    ...mapActions({
      hideSnackBarAction: "snackBar/hide"
    })
  }
};
</script>


<style  scoped>
.dob {
  margin: 15px;
}
</style>