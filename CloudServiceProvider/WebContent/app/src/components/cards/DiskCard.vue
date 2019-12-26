
<template>
    <v-card outline block class="ma-3 pa-6">
        <v-card-title>
            <v-text-field v-model="search" 
            append-icon="mdi-car"
            label="Search"
            single-line
            hide-details
            ></v-text-field>
        </v-card-title>
        <v-data-table 
            :headers="headers"
            :items="this.$store.state.disks"
        ></v-data-table>
    </v-card>
</template>



<script>
export default {

    data() {
        return {

            headers : [
                { text : 'Name', align : 'left', value: 'name'},
                { text : 'Capacity (gb)', value: 'capacity'},
                { text : 'Type', value: 'type'},
            ]
        }
    }
}
</script>