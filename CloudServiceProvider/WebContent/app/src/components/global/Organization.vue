<template>
  <div>
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex s12 sm8 md4>
            <v-card class="elevation-12">
              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Inormacije o organizaciji</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>

              <v-card-text>
                <v-form>
                  <v-text-field
                    label="Ime organizacije"
                    name="orgName"
                    v-model="firstName"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    label="Desciption"
                    name="description"
                    v-model="lastName"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    label="Logo"
                    name="logo"
                    v-model="logo"
                    prepend-icon="mdi-image"
                    type="text"
                  ></v-text-field>
                </v-form>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn @click="submit" color="blue-grey white--text">Save</v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
export default {
    data(){
        return {
            orgName: "",
            description: "",
            logo: "",
        }
    },

    methods: {
        ...mapActions({

        }),

        submit(){
            
        },

        validate(){

        },
    }
};
</script>

<style>
</style>