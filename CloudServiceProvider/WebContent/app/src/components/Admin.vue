<template>
  <div class="about">
    <h1 class=" my-5 subheading grey--text" >Admin Page</h1>

    <v-container class="my-5">
      
      <v-layout row wrap> 

        <v-flex xs12 sm6 md6>
          <VMCard/>
        </v-flex>

        <v-spacer></v-spacer>

        <v-flex xs12 sm6 md6>
          <DiskCard/>
        </v-flex>

        <v-flex xs12 sm6 md6>
          <DiskCard/>
        </v-flex>

        <v-flex xs12 sm6 md6>
          <VMCategoryCard/>
        </v-flex>

      </v-layout>

    </v-container>

  </div>
</template>

<script>

import VMCard from './cards/VMCard'
import DiskCard from './cards/DiskCard'
import VMCategoryCard from './cards/VMCategoryCard'

export default {
    components: {
    VMCard, 
    VMCategoryCard,
    DiskCard
  }
}
</script>

