<template>
 <div>
    <v-content>

      <v-container fluid fill-height>

        <v-layout align-center justify-center>

          <v-flex s12 sm8  md4>

            <v-card class="elevation-12">

              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Registration form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              
              <v-card-text>
                <v-form>

                  <v-text-field
                    label="First Name"
                    name="name"
                    v-model="name"
                    prepend-icon="mdi-account"
                    type="text"
                    :rules="[() => !!name || 'This field is required']"
                    :error-messages="errorMessages"
                    required
                  ></v-text-field>

                  <v-text-field
                    label="Last Name"
                    name="lastname"
                    v-model="lastname"
                    prepend-icon="mdi-account"
                    type="text"
                    :rules="[() => !!name || 'This field is required']"
                    :error-messages="errorMessages"
                    required
                  ></v-text-field>

                  <v-text-field
                    label="Email"
                    name="email"
                    v-model="email"
                    prepend-icon="mdi-email"
                    type="email"
                    :rules="[() => !!name || 'This field is required']"
                    :error-messages="errorMessages"
                    required
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Password"
                    name="password"
                    v-model="password"
                    prepend-icon="mdi-lock"
                    type="password"
                    :rules="[() => !!name || 'This field is required']"
                    :error-messages="errorMessages"
                    required
                  ></v-text-field>
                </v-form>

              </v-card-text>

              <v-card-actions>

                <router-link to="/login"> 
                <v-btn color="blue-grey white--text" >Already have an account?</v-btn>
                </router-link> 

                <v-spacer></v-spacer>
                <v-btn color="blue-grey white--text">Register</v-btn>
              </v-card-actions>

            </v-card>

          </v-flex>

        </v-layout>

      </v-container>

    </v-content>
</div>
</template>

<script>
// @ is an alias to /src

export default {
  components: {
    
  },
  
  data() {
    return {
      name : null,
      lastname : null,
      email : null,
      password : null
    }
  },

  methods : {

    submit() {

    }
  }
}
</script>

<style scoped>
  a {  text-decoration: none;}
</style>