<template>
    <v-footer
      dark
      padless
    >
      <v-card
        class="flex"
        flat
        tile
      >
        <v-card-text class="py-2 white--text text-center">
          {{ new Date().getFullYear() }} — <strong>D&D</strong>
        </v-card-text>
      </v-card>
    </v-footer>
</template>



<script>

export default {

    components: {
        
    },
    data() {
        return{
        }
    }

}
</script>