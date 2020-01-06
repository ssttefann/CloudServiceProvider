<template>
 <div>
    <v-content>

      <v-container fluid fill-height>

        <v-layout align-center justify-center>

          <v-flex s12 sm8  md4>

            <v-card class="elevation-12">

              <v-toolbar color="blue-grey" dark flat>
                <v-toolbar-title>Change account information</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
              
              <v-card-text>
                <v-form>

                  <v-text-field
                    label="First Name"
                    name="name"
                    v-model="firstName"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    label="Last Name"
                    name="lastname"
                    v-model="lastName"
                    prepend-icon="mdi-account"
                    type="text"
                  ></v-text-field>

                  <v-text-field
                    id="password"
                    label="Enter the new password"
                    name="password"
                    v-model="password1"
                    prepend-icon="mdi-lock"
                    type="password"
                  ></v-text-field>

                  <v-text-field
                    id="password2"
                    label="Repeat the new password"
                    name="password"
                    v-model="password2"
                    prepend-icon="mdi-lock"
                    type="password"
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


export default {
    components: {
  },

  data() {
    return {
      firstName : this.$store.state.users.loggedUser.firstName,
      lastName : this.$store.state.users.loggedUser.lastName,
      password1 : "",
      password2 : "",
      rules: {
        required: value => !!value || 'Required.'
      }
    }
  },

  methods : {

    submit() {
      let changed = false;

      if(this.firstName != this.$store.state.users.loggedUser.firstName && this.firstName.trim()){
        this.$store.state.users.loggedUser.firstName = this.firstName;
        changed = true;
      }

      if(this.lastName != this.$store.state.users.loggedUser.lastName && this.lastName.trim()){
        this.$store.state.users.loggedUser.lastName = this.lastName;
        changed = true;
      }
    
      if(this.password1.trim() + this.password2.trim() != ""){
        if(this.password1 != this.password2){
          alert("Passwords must match!")
          changed = false;
        }
        else{
          this.$store.state.users.loggedUser.password = this.password1;
          changed = true
        }
      }

      // ako je doslo do promene salji zahtev na srv
      if(changed){
        alert("Uspesno promenjene informacije")
      }
    }
  }
}
</script>

