<template>
 <div>
    <v-content>

      <v-container fluid fill-height>

        <v-layout align-center justify-center>

          <v-flex xs12 sm8  md4 >

            <v-card class="elevation-12">

              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Login form</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              
              <v-card-text>
                <v-form>
                  <v-text-field
                    label="Username"
                    name="username"
                    v-model="username"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Password"
                    v-model="password"
                    name="password"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>
                </v-form>
              </v-card-text>

              <v-card-actions>

                <router-link to="/register"> 
                <v-btn color="blue-grey white--text" >Don't have an account?</v-btn>
                </router-link>

                <v-spacer></v-spacer>
                <v-btn @click="validateInput" color="blue-grey white--text">Login</v-btn>
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
      username : null,
      password : null
    }
  },
  methods : {

      validateInput : function() {
            
            if(!this.username) {
              alert("Username can't be empty");
            }
            else if(!this.password) {
              alert("Password can't be empty")
            }

            // ako je proslo znaci da je popunio sve,
            // posalji zahtev na server da li ima taj user
            // i redirectuj ako se ulogovao
      }
  }
}
</script>


<style scoped>
  a {  text-decoration: none;}
</style>