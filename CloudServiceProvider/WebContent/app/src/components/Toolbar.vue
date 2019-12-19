
<template>
    <nav>
    <v-app-bar app class="blue-grey darken-1">

        <v-btn @click="toggleDrawer" target="_blank" text large>
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
  
          <v-list-item-title>Meeyat</v-list-item-title>

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
import axios from 'axios';

export default {

    components: {
        
    },
    data() {
        return{
            drawerEnabled: false,
            drawerVisible: false,

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

          axios
            .get("/rest/LoggedUser/")
            .then(res => {
              let uloga = res.data;

              // samo dozvoli ako postoji ulogovani korisnik
              if (uloga != "ERR")
                this.drawerVisible = !this.drawerVisible;
            })
            .catch(err => alert(err));

            
        },

        /** Log-outuje korisnika, terminira sesiju i vraca ga na pocetnu str */
        logout : function () {

          axios
            .get('/rest/logout/')
            .then(this.$router.push('/'))
            .catch(err => alert(err))
        }
        
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