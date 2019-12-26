<template>
  <div class="about">
    <h1 class="subheading grey--text" >This is the Dashboard</h1>

    <v-container class="my-5">
      
      <v-layout row wrap> 

        <v-flex xs12 sm6 md6>
          <VMcard/>
        </v-flex>

        <v-spacer></v-spacer>

        <v-flex xs12 sm6 md6>
          <VMcard/>
        </v-flex>

      </v-layout>

    </v-container>

  </div>
</template>

<script>

import VMcard from './cards/VMcard'

export default {
    components: {
    VMcard
  }
}
</script>

