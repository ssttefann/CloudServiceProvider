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
                <v-btn @click="submit" color="blue-grey white--text">Register</v-btn>
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
import { stringify } from 'querystring';
import { bus } from '../main';

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


    /**
     * Proverava da li je svako polje forme popunjeno
     * @returns {String} "OK" ako su uslovi zadovoljeni, ako nisu onda imena labela koje ne zadovoljavaju uslov
     */
    validate() {

      let invalid = [];

      if (!this.name)
        invalid.push("Name");
      
      if(!this.lastname)
        invalid.push("Last name");

      if(!this.email)
        invalid.push("Email");
      
      if(!this.password)
        invalid.push("Password");
      
      let retval = invalid.length > 0 ?  invalid.join(",") :  "OK";

      return retval;
    },
    
    /**
     * Poziva se kada korisnik stisne na dugme za registraciju
     */
    submit() {
      let resp = this.validate();

      let user;

      // ako je RESP ok mozes poslati serveru
      if(resp === "OK"){
        
        user = {
          name : this.name, lastname : this.lastname,
          email : this.email, password : this.password
        }

        this.$axios.post('/rest/register', stringify(user))
          .then(res => {
            
            let odgovor = res.data;
            if (odgovor == "OK"){
              bus.$emit('userLoggedIn', true);
              this.$router.push('/dashboard');
            }
            else if(odgovor == "EMAIL_ERR"){
              alert("Vec postoji korisnik sa tim emailom, pokusajte ponovo");
            }
            else
              alert("Doslo je do greske prilikom registracije");
          })
          .catch(res => alert(res));

      //ako nije ispisi poruku o gresci
      }else{
        alert("Labele " +resp+ " moraju biti popunjene pre registracije")
      }
    }
  }
}
</script>

<style scoped>
  a {  text-decoration: none;}
</style>