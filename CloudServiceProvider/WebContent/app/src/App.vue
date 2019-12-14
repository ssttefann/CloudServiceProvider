<template>
  <v-app>
    <Toolbar/>
    <v-content class="dob">
      <router-view> </router-view>
    </v-content>
  </v-app>
</template>

<script>
import Toolbar from './components/Toolbar'

export default {
  name: 'App',
  components: {
    Toolbar
  },
  data: () => ({
    //
  }),
};
</script>


<style  scoped>

  .dob {
    margin: 15px;
  }

</style>