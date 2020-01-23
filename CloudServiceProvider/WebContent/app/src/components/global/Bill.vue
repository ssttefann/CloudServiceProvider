<template>
  <v-container>

  </v-container>
</template>

<script>
export default {
    name: "Bill",
    data(){
        return {
            startDate: new Date().toISOString().substr(0,10),
            endDate: new Date().toISOString().substr(0, 10),
        }
    },
    methods: {
    }
}
</script>

<style>

</style>