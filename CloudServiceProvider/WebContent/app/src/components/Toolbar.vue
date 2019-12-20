
<template>
    <nav>
    <v-app-bar app class="blue-grey darken-1">

        <v-btn @click="toggleDrawer" :disabled=isDisabled target="_blank" text large>
            <v-icon color="white">mdi-view-headline</v-icon>
        </v-btn>

        <router-link to="/"> 
        <div  class="d-flex align-center">
            <v-img
            alt="Vuetify Logo"
            class="shrink mr-2"
            contain
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
            transition="scale-transition"
            width="40"
            />

            <label class="naslov">BDSM Services</label>
        </div>
        </router-link > 

        <v-spacer></v-spacer>

         <!-- DUGMICI NA TOOLBARU -->
        <v-btn href="" target="_blank" text>
            <v-icon color="white" size="20">mdi-help-circle</v-icon>
        </v-btn>

        <v-btn href="" target="_blank" text>
            <v-icon color="white" size="20">mdi-bell</v-icon>
        </v-btn>

    </v-app-bar> 

    
    <!--  DRAWER (OVO SA LEVE STR)-->
    
    <v-navigation-drawer v-model="drawerVisible" app>
        <br>
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="https://avatars0.githubusercontent.com/u/28116193?s=460&v=4"></v-img>
          </v-list-item-avatar>
  
          <v-list-item-title v-model="user">
            {{user.fname + " " + user.lastname}}
          </v-list-item-title>

        </v-list-item>
  
        <v-divider class="blue-grey darken-1" ></v-divider>
        <br><br>

        <v-list dense>
          <v-list-item v-for="link in links" :key="link.text" router :to="link.route">
            <v-list-item-icon>
              <v-icon>{{ link.icon }}</v-icon>
            </v-list-item-icon>
  
            <v-list-item-content>
              <v-list-item-title>{{ link.text }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <template v-slot:append>
          <div class="logout">
            <v-btn @click="logout" block class="blue-grey darken-1 white--text">Logout</v-btn>
          </div>
        </template>

      </v-navigation-drawer>

    </nav>

</template>

<script>

import { bus } from '../main';

export default {

    components: {
        
    },
    data() {
        return{
            drawerDisabled: true,
            drawerVisible: false,
            user : {
              fname : "meeyat", lastname : "meel"
            },

            // linkovi za prelaz na druge delove str
            links : [
                {icon: 'mdi-view-dashboard', text: 'Dashboard', route: '/'},
                {icon: 'mdi-folder', text: 'About', route: '/register'},
                {icon: 'mdi-account-minus', text: 'Team', route: '/login'}
            ],
        }
    },

    methods : {

        /** Otvara drawer ako postoji ulogovani korisnik */
        toggleDrawer : function() {

          if(!this.drawerDisabled)
            this.drawerVisible = !this.drawerVisible;

        },

        /** Log-outuje korisnika, terminira sesiju i vraca ga na pocetnu str */
        logout : function () {

          this.$axios
            .get('/rest/logout/')
            .then(this.$router.push('/'))
            .then(this.drawerVisible = false)
            .then(this.drawerDisabled = true)
            .catch(err => alert(err))
        },

        /** Ucitava podatke o korisniku kad se prijavi ili registruje */
        loadUser : function () {
          this.$axios
            .get('rest/loggedUser/')
            .then(res => {
              this.user = res.data;
            })
            .catch(err => alert(err));
        }
    },

    computed : {
      /** racuna da li treba da aktivira dugme za aktiviranje drawera  */
        isDisabled() {
          return this.drawerDisabled;
        }
    },

    created(){
      /** hvata event logovanja korisnika i aktivira drawer */
      bus.$on('userLoggedIn', (data) => {
        this.drawerDisabled = !data;
        this.loadUser();
      })
    }
}
</script>

<style  scoped>

    .naslov {
        margin-left : 20px;
        color: azure;
        font-size: 35px;
    }

    .d {
        background-color: red;
    }

    .transbtn{
        color: red;
    }

    .logout{
        margin: 15px;
        margin-bottom: 25px;
    }

    a {  text-decoration: none;}

</style>