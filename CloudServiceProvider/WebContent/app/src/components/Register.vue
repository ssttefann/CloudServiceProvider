<template>
 <div>
    <v-content>

      <v-container fluid fill-height>

        <v-layout align-center justify-center>

          <v-flex xs12 sm8  md4 >

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
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    label="Last Name"
                    name="lastname"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    label="Email"
                    name="email"
                    prepend-icon="mdi-email"
                    type="email"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Password"
                    name="password"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>
                </v-form>

              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-grey">Register</v-btn>
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
    
  }
}
</script>
