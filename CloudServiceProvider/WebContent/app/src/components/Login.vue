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
                    label="Email"
                    name="Email"
                    v-model="email"
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
                <v-btn @click="submit" color="blue-grey white--text">Login</v-btn>
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
import axios from 'axios';
import { stringify } from 'querystring';

export default {
  components: {
    
  },
  data() {
    return {
      email : null,
      password : null
    }
  },

  created() {
    
    //ako je vec ulogovan redirektuj ga
    axios
      .get("/rest/LoggedUser/")
      .then(res => {
        let uloga = res.data;
        if (uloga == "USER")
          this.$router.push('/dashboard')
        else if(uloga == "ADMIN")
          this.$router.push('/admin')
      })
      .catch(err => alert(err));

  },
  methods : {

     /**
     * Proverava da li je svako polje forme popunjeno
     * @returns {String} "OK" ako su uslovi zadovoljeni, ako nisu onda imena labela koje ne zadovoljavaju uslov
     */
    validate() {

      let invalid = [];

      if (!this.email)
        invalid.push("Email");
      
      if(!this.password)
        invalid.push("Password");
      
      let retval = invalid.length > 0 ?  invalid.join(",") :  "OK";

      return retval;
    },
    

    /**
     * Poziva se kada korisnik stisne na dugme za login
     */
    submit() {
      let resp = this.validate();

      let uloga;

      // ako je RESP ok mozes poslati serveru
      if(resp === "OK"){
        let user = {
          password : this.password,
          email : this.email
        };

        axios.post('/rest/login', stringify(user))
          .then(res => {
            uloga = res.data;
            if (uloga == "ADMIN")
              this.$router.push('/admin')
            else if(uloga == "USER")
              this.$router.push('/dashboard')
            else
              alert("Pogresna kombinacija user/pass");
          })
          .catch(res => alert(res));
        
        
      //ako nije ispisi poruku o gresci
      }else{
        alert("Labele " +resp+ " moraju biti popunjene pre prijavljivanja")
      }
    }
  }
}
</script>


<style scoped>
  a {  text-decoration: none;}
</style>