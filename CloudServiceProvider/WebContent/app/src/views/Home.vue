<template>
  <div class="home">
    
    <v-content>

      <v-container fluid fill-height>

        <v-layout align-start justify-center>

          <v-flex xs12 sm8  md4>

              <v-row> 
                <v-col><span class="naslov">Dajte. Nam. Vase. Pare</span></v-col>
              </v-row>

              <v-row>
                <v-col align-center><span class="podnaslov"> Nemamo za leba, jedemo u menzi </span></v-col>
              </v-row>
              <br><br>

              <v-row> 
                <v-col>
                  <router-link to="/login"> 
                  <v-btn class="blue-grey darken-1 white--text">Log in</v-btn>
                  </router-link>
                </v-col>

                <v-col>
                  <router-link to="/register"> 
                  <v-btn class="white darken-1 blue-grey--text">Register</v-btn>
                  </router-link>
                </v-col>
              </v-row>

          </v-flex>

        </v-layout>

      </v-container>

    </v-content>

  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'home',
  components: {
    
  },

  created() {

  },

}
</script>


<style scoped>
  .naslov {
    font-size: 3em;
  }

  .podnaslov{
    font-size: 1em;
    font-weight: light;
  }

  a{  text-decoration: none;}

</style>

