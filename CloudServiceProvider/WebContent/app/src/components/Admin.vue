<template>
  <div class="about">
    <h1 class=" my-5 subheading grey--text" >Admin Page</h1>

    <v-container class="my-5">
      
      <v-layout row wrap> 

        <!-- Virtuelne Masine -->
        <v-flex xs12 sm6 md6>
          <VMCard/>
        </v-flex>

        <!-- Virtuelne Masine -->
        <v-flex xs12 sm6 md6>
          <VMCategoryCard/>
        </v-flex>

        <v-flex xs12 sm6 md6>
          <DiskCard/>
        </v-flex>

        <v-flex xs12 sm6 md6>
          <UserCard/>
        </v-flex>

        <v-flex xs12 sm6 md6>
          <OrganizationCard/>
        </v-flex>

      </v-layout>

    </v-container>

  </div>
</template>

<script>

import VMCard from './cards/VMCard'
import DiskCard from './cards/DiskCard'
import VMCategoryCard from './cards/VMCategoryCard'
import OrganizationCard from './cards/OrganizationCard'
import UserCard from './cards/UserCard'

export default {
    components: {
    VMCard, 
    VMCategoryCard,
    DiskCard,
    OrganizationCard,
    UserCard
  },



  //trebalo bi ovde da se ucitaju sve kolekcije za prikaz
  created : {

  }
}
</script>

