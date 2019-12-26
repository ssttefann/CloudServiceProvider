
<template>
    <nav>
    <v-app-bar app class="blue-grey darken-1">

        <v-btn @click="toggleDrawer" :disabled=isDisabled target="_blank" text large>
            <v-icon color="white">mdi-view-headline</v-icon>
        </v-btn>

        <v-btn  @click="homeButon" class="d-flex align-center" text>
            <v-img
            alt="Vuetify Logo"
            class="shrink mr-2"
            contain
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
            transition="scale-transition"
            width="40"
            />

            <label class="naslov">BDSM Services</label>
        </v-btn>

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
            {{this.$store.state.loggedUser.firstName + " " + this.$store.state.loggedUser.lastName}}
          </v-list-item-title>

        </v-list-item>
  
        <v-divider class="blue-grey darken-1" ></v-divider>
        <br><br>

        <!-- Ako je ulogovan obican user -->
        <div v-if="this.$store.state.loggedUser.role == 'User'">
          <v-list dense>
            <v-list-item v-for="link in links_user" :key="link.text" router :to="link.route">
              <v-list-item-icon>
                <v-icon>{{ link.icon }}</v-icon>
              </v-list-item-icon>
    
              <v-list-item-content>
                <v-list-item-title>{{ link.text }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </div>


        <!-- Ako je ulogovan admin -->
        <div v-else-if="this.$store.state.loggedUser.role == 'Admin'">
          <v-list dense>
            <v-list-item v-for="link in links_admin" :key="link.text" router :to="link.route">
              <v-list-item-icon>
                <v-icon>{{ link.icon }}</v-icon>
              </v-list-item-icon>
    
              <v-list-item-content>
                <v-list-item-title>{{ link.text }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </div>

        <template v-slot:append>
          <div class="logout">
            <v-btn @click="logout" block class="blue-grey darken-1 white--text">Logout</v-btn>
          </div>
        </template>

      </v-navigation-drawer>

    </nav>

</template>

<script>

// import { bus } from '../main';

export default {

    components: {
        
    },
    data() {
        return{
            drawerDisabled: true,
            drawerVisible: false,
            user : {
              firstName : "meeyat", lastName : "meel"
            },

            // linkovi za prelaz na druge delove str
            links_admin : [
                {icon: 'mdi-view-dashboard', text: 'Dashboard', route: '/admin'},
                {icon: 'mdi-account', text: 'Account', route: '/account'},
                {icon: 'mdi-steam', text: 'Team', route: '/organization'}
            ],

            links_user : [
                {icon: 'mdi-view-dashboard', text: 'Dashboard', route: '/dashboard'},
                {icon: 'mdi-account', text: 'Account', route: '/account'}
            ]


        }
    },

    methods : {

        /** Otvara drawer ako postoji ulogovani korisnik */
        toggleDrawer : function() {

          this.drawerVisible = !this.drawerVisible;

        },

        /** Log-outuje korisnika, terminira sesiju i vraca ga na pocetnu str */
        logout : function () {

          this.$axios
            .get('/rest/logout/')
            .then(this.$router.push('/'))
            .then(this.drawerVisible = false)
            .then(this.drawerDisabled = true)
            .then(this.$store.commit('logOut'))
            .catch(err => alert(err))
        },

        /** Kada korisnik pritisne na Home Dugme */
        homeButon : function() {

          if (this.$store.state.loggedUser.role === "User") {
            this.$router.push('/dashboard');
          }
          else if(this.$store.state.loggedUser.role === "Admin" || this.$store.state.loggedUser.role === "SuperAdmin"){
            this.$router.push('/admin');
          }else{
            this.$router.push('/');
          }

        }
    },

    computed : {

      /** Ako ne postoji ulogavan korisnik disable-uje dugme  */
        isDisabled() {
          return !this.$store.getters.isLogged;
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